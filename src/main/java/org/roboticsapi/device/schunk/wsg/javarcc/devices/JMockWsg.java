/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.javarcc.devices;

import org.roboticsapi.facet.javarcc.devices.AbstractJDevice;

public class JMockWsg extends AbstractJDevice {

	public enum GraspingState {
		IDLE, GRASPING, NO_PART_FOUND, PART_LOST, HOLDING, RELEASING, POSITIONING, ERROR
	};

	public enum StatusCode {
		E_SUCCESS, // !< No error
		E_NOT_AVAILABLE, // !< Device, service or data is not available
		E_NO_SENSOR, // !< No sensor connected
		E_NOT_INITIALIZED, // !< The device is not initialized
		E_ALREADY_RUNNING, // !< Service is already running
		E_FEATURE_NOT_SUPPORTED, // !< The asked feature is not supported
		E_INCONSISTENT_DATA, // !< One or more dependent parameters mismatch
		E_TIMEOUT, // !< Timeout error
		E_READ_ERROR, // !< Error while reading from a device
		E_WRITE_ERROR, // !< Error while writing to a device
		E_INSUFFICIENT_RESOURCES, // !< No memory available
		E_CHECKSUM_ERROR, // !< Checksum error
		E_NO_PARAM_EXPECTED, // !< No parameters expected
		E_NOT_ENOUGH_PARAMS, // !< Not enough parameters
		E_CMD_UNKNOWN, // !< Unknown command
		E_CMD_FORMAT_ERROR, // !< Command format error
		E_ACCESS_DENIED, // !< Access denied
		E_ALREADY_OPEN, // !< The interface is already open
		E_CMD_FAILED, // !< Command failed
		E_CMD_ABORTED, // !< Command aborted
		E_INVALID_HANDLE, // !< invalid handle
		E_NOT_FOUND, // !< device not found
		E_NOT_OPEN, // !< device not open
		E_IO_ERROR, // !< I/O error
		E_INVALID_PARAMETER, // !< invalid parameter
		E_INDEX_OUT_OF_BOUNDS, // !< index out of bounds
		E_CMD_PENDING, // !< Command execution needs more time
		E_OVERRUN, // !< Data overrun
		E_RANGE_ERROR, // !< Range error
		E_AXIS_BLOCKED, // !< Axis is blocked
		E_FILE_EXISTS // !< File already exists
	};

	public boolean homing(boolean openDirection) {
		if (eStop) {
			status_code = StatusCode.E_ACCESS_DENIED;
		} else {
			desiredOpeningWidth = Math.min(softlimits_outer,
					Math.max(softlimits_inner, openDirection ? MAX_OPENING_WIDTH : 0));
			initialOpeningWidth = currentOpeningWidth;

			currentForce = 0;
			currentGraspingState = GraspingState.POSITIONING;
			homed = true;
			status_code = StatusCode.E_SUCCESS;
			startTime = System.currentTimeMillis();
			busy = true;
		}

		return true;
	}

	public boolean preposition(boolean relative, double width, double speed, boolean stopOnBlock) {
		double newOpeningWidth = relative ? currentOpeningWidth + width : width;

		if (eStop) {
			status_code = StatusCode.E_ACCESS_DENIED;
		} else if (!homed) {
			status_code = StatusCode.E_NOT_INITIALIZED;
		} else if (newOpeningWidth > softlimits_outer || newOpeningWidth < softlimits_inner) {
			status_code = StatusCode.E_RANGE_ERROR;
		} else {
			desiredOpeningWidth = newOpeningWidth;
			initialOpeningWidth = currentOpeningWidth;

			pendingDuration = 0.3 + Math.abs(initialOpeningWidth - desiredOpeningWidth) / MAX_VELOCITY;

			currentForce = 0;
			currentGraspingState = GraspingState.POSITIONING;
			noPart = false;
			status_code = StatusCode.E_SUCCESS;
			startTime = System.currentTimeMillis();
			busy = true;
		}

		return true;
	}

	public boolean grasp(double width, double speed) {
		preposition(false, width, speed, true);
		if (status_code == StatusCode.E_SUCCESS) {
			currentForce = force / 2;
			currentGraspingState = GraspingState.GRASPING;
		}
		return true;
	}

	public boolean release(double openWidth, double speed) {
		preposition(false, openWidth, speed, false);
		if (status_code == StatusCode.E_SUCCESS) {
			currentGraspingState = GraspingState.RELEASING;
		}
		return true;
	}

	public boolean setAcceleration(double acceleration) {
		status_code = StatusCode.E_SUCCESS;
		busy = false;
		return true;
	}

	public boolean setForceLimit(double force) {
		status_code = StatusCode.E_SUCCESS;
		busy = false;
		this.force = force;
		return true;
	}

	public boolean setSoftLimits(double inner, double outer) {
		status_code = StatusCode.E_SUCCESS;
		busy = false;
		softlimits_inner = inner;
		softlimits_outer = outer;
		return true;
	}

	public boolean clearSoftLimits() {
		status_code = StatusCode.E_SUCCESS;
		busy = false;
		softlimits_inner = 0;
		softlimits_outer = MAX_OPENING_WIDTH;
		return true;

	}

	public void stopDevice() {
		status_code = StatusCode.E_SUCCESS;
		busy = false;
	}

	public boolean isBusy() {
		double timeLeft = (System.currentTimeMillis() - startTime) / 1000.0;
		if (busy && status_code == StatusCode.E_SUCCESS) {
			if (timeLeft >= pendingDuration) {
				currentOpeningWidth = desiredOpeningWidth;

				if (eStop) {
					status_code = StatusCode.E_ACCESS_DENIED;
					currentGraspingState = GraspingState.IDLE;
				} else if (currentGraspingState == GraspingState.GRASPING) {
					if (noPart) {
						status_code = StatusCode.E_CMD_FAILED;
					}
					currentGraspingState = (noPart ? GraspingState.NO_PART_FOUND : GraspingState.HOLDING);
				} else {
					currentGraspingState = GraspingState.IDLE;
				}
				busy = false;
			} else {
				double dist = desiredOpeningWidth - initialOpeningWidth;
				double fact = timeLeft / pendingDuration;
				currentOpeningWidth = initialOpeningWidth + (dist * fact);
			}
		}
		return busy;
	}

	public StatusCode getStatusCode() {
		isBusy();
		return status_code;
	}

	public double getOpeningWidth() {
		isBusy();
		return currentOpeningWidth;
	}

	public double getVelocity() {
		return 0;
	}

	public double getForce() {
		return currentForce;
	}

	public GraspingState getGraspingState() {
		isBusy();
		return currentGraspingState;
	}

	public void setPartLost() {
		noPart = true;
		if (currentGraspingState == GraspingState.HOLDING)
			currentGraspingState = GraspingState.PART_LOST;
	}

	private static final double MAX_VELOCITY = 0.42 / 2;
	private static final double MAX_OPENING_WIDTH = 0.11;

	private double pendingDuration = 1;

	private boolean eStop = false;
	private boolean homed = false;

	private double force = 0.5;
	private double softlimits_inner = 0;
	private double softlimits_outer = MAX_OPENING_WIDTH;

	private double desiredOpeningWidth = 0;
	private double initialOpeningWidth = 0;

	private double currentOpeningWidth = 0;
	private double currentForce = 0;
	private GraspingState currentGraspingState = GraspingState.IDLE;
	private StatusCode status_code = StatusCode.E_SUCCESS;

	private boolean noPart = false;
	private boolean busy = false;
	private long startTime = 0;
}
