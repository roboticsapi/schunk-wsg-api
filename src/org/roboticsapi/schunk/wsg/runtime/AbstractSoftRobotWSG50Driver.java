/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.runtime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.roboticsapi.core.actuator.ActuatorDriverRealtimeException;
import org.roboticsapi.core.actuator.ActuatorNotOperationalException;
import org.roboticsapi.core.actuator.ConcurrentAccessException;
import org.roboticsapi.core.sensor.DeviceBasedDoubleSensor;
import org.roboticsapi.core.sensor.DoubleSensor;
import org.roboticsapi.runtime.AbstractSoftRobotActuatorDriver;
import org.roboticsapi.runtime.driver.DeviceBasedLoadable;
import org.roboticsapi.schunk.wsg.PartLostException;
import org.roboticsapi.schunk.wsg.WSG50;
import org.roboticsapi.schunk.wsg.WSG50Exception;
import org.roboticsapi.schunk.wsg.WSG50GripperDriver;
import org.roboticsapi.schunk.wsg.sensor.GraspingStateSensor;

public abstract class AbstractSoftRobotWSG50Driver extends AbstractSoftRobotActuatorDriver
		implements WSG50GripperDriver, DeviceBasedLoadable<WSG50> {

	public static final class OpeningWidthSensor extends DeviceBasedDoubleSensor<AbstractSoftRobotWSG50Driver> {
		public OpeningWidthSensor(AbstractSoftRobotWSG50Driver gripper) {
			super(gripper);
		}
	}

	@Override
	public DoubleSensor getBaseJawOpeningWidthSensor() {
		return new OpeningWidthSensor(this);
	}

	/**
	 * {@link DoubleSensor} implementation for measuring the velocity of a
	 * {@link WSG50} using the {@link AbstractSoftRobotWSG50Driver}.
	 */
	public static final class VelocitySensor extends DeviceBasedDoubleSensor<AbstractSoftRobotWSG50Driver> {
		public VelocitySensor(AbstractSoftRobotWSG50Driver gripper) {
			super(gripper);
		}
	}

	@Override
	public DoubleSensor getVelocitySensor() {
		return new VelocitySensor(this);
	}

	/**
	 * {@link DoubleSensor} implementation for measuring the gripping force of a
	 * {@link WSG50} using the {@link AbstractSoftRobotWSG50Driver}.
	 */
	public static final class ForceSensor extends DeviceBasedDoubleSensor<AbstractSoftRobotWSG50Driver> {
		public ForceSensor(AbstractSoftRobotWSG50Driver gripper) {
			super(gripper);
		}
	}

	@Override
	public DoubleSensor getForceSensor() {
		return new ForceSensor(this);
	}

	/**
	 * {@link DoubleSensor} implementation for measuring the acceleration of a
	 * {@link WSG50} using the {@link AbstractSoftRobotWSG50Driver}.
	 */
	public static final class AccelerationSensor extends DeviceBasedDoubleSensor<AbstractSoftRobotWSG50Driver> {
		public AccelerationSensor(AbstractSoftRobotWSG50Driver gripper) {
			super(gripper);
		}
	}

	@Override
	public DoubleSensor getAccelerationSensor() {
		return new AccelerationSensor(this);
	}

	/**
	 * {@link DoubleSensor} implementation for measuring the grasping state of a
	 * {@link WSG50} using the {@link AbstractSoftRobotWSG50Driver}.
	 */
	public static final class GraspingStateDoubleSensor extends DeviceBasedDoubleSensor<AbstractSoftRobotWSG50Driver> {
		public GraspingStateDoubleSensor(AbstractSoftRobotWSG50Driver gripper) {
			super(gripper);
		}
	}

	@Override
	public GraspingStateSensor getGraspingStateSensor() {
		return new GraspingStateSensor(new GraspingStateDoubleSensor(this));
	}

	@Override
	public List<ActuatorDriverRealtimeException> defineActuatorDriverExceptions() {
		List<ActuatorDriverRealtimeException> actuatorExceptions = super.defineActuatorDriverExceptions();

		actuatorExceptions.add(new WSG50Exception.WsgNotAvailableException(this));
		actuatorExceptions.add(new WSG50Exception.WsgNoSensorException(this));
		actuatorExceptions.add(new WSG50Exception.WsgNotInitializedException(this));
		actuatorExceptions.add(new WSG50Exception.WsgAlreadyRunningException(this));
		actuatorExceptions.add(new WSG50Exception.WsgFeatureNotSupportedException(this));
		actuatorExceptions.add(new WSG50Exception.WsgInconsistentDataException(this));
		actuatorExceptions.add(new WSG50Exception.WsgTimeoutException(this));
		actuatorExceptions.add(new WSG50Exception.WsgReadErrorException(this));
		actuatorExceptions.add(new WSG50Exception.WsgWriteErrorException(this));
		actuatorExceptions.add(new WSG50Exception.WsgInsufficientResourcesException(this));
		actuatorExceptions.add(new WSG50Exception.WsgChecksumErrorException(this));
		actuatorExceptions.add(new WSG50Exception.WsgNoParamExpectedException(this));
		actuatorExceptions.add(new WSG50Exception.WsgNotEnoughParamsException(this));
		actuatorExceptions.add(new WSG50Exception.WsgCmdUnknownException(this));
		actuatorExceptions.add(new WSG50Exception.WsgCmdFormatErrorException(this));
		actuatorExceptions.add(new WSG50Exception.WsgAccessDeniedException(this));
		actuatorExceptions.add(new WSG50Exception.WsgAlreadyOpenException(this));
		actuatorExceptions.add(new WSG50Exception.WsgCmdFailedException(this));
		actuatorExceptions.add(new WSG50Exception.WsgCmdAbortedException(this));
		actuatorExceptions.add(new WSG50Exception.WsgInvalidHandleException(this));
		actuatorExceptions.add(new WSG50Exception.WsgNotFoundException(this));
		actuatorExceptions.add(new WSG50Exception.WsgNotOpenException(this));
		actuatorExceptions.add(new WSG50Exception.WsgIoErrorException(this));
		actuatorExceptions.add(new WSG50Exception.WsgInvalidParameterException(this));
		actuatorExceptions.add(new WSG50Exception.WsgIndexOutOfBoundsException(this));
		actuatorExceptions.add(new WSG50Exception.WsgCmdPendingException(this));
		actuatorExceptions.add(new WSG50Exception.WsgOverrunException(this));
		actuatorExceptions.add(new WSG50Exception.WsgRangeErrorException(this));
		actuatorExceptions.add(new WSG50Exception.WsgAxisBlockedException(this));
		actuatorExceptions.add(new WSG50Exception.WsgFileExistsException(this));
		actuatorExceptions.add(new ActuatorNotOperationalException(this));// .WsgNotOperationalException(this));
		actuatorExceptions.add(new ConcurrentAccessException(this));// .WsgConcurrentAccessException(this));

		actuatorExceptions.add(new PartLostException(this));

		return actuatorExceptions;
	}

	@Override
	protected boolean checkDeviceInterfaces(List<String> interfaces) {
		return true; // no interfaces until now
	}

	@Override
	public boolean build(WSG50 t) {
		Map<String, String> parameters = new HashMap<String, String>();

		collectWSG50Parameters(t, parameters);
		collectDriverSpecificParameters(parameters);

		return loadDeviceDriver(parameters);
	}

	private void collectWSG50Parameters(WSG50 t, Map<String, String> parameters) {
		// currently nothing to do
	}

	protected abstract void collectDriverSpecificParameters(Map<String, String> parameters);

	@Override
	public void delete() {
		deleteDeviceDriver();
	}

}
