/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.runtime.exception;

import org.roboticsapi.framework.gripper.GripperDriver;
import org.roboticsapi.framework.gripper.GripperException;

/**
 * Abstract definition of an exception which can occur by the wsg gripper.
 */
public abstract class Wsg50Exception extends GripperException {

	private static final long serialVersionUID = 1L;

	public enum WSG50Error {
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
		E_FILE_EXISTS, NOT_OPERATIONAL, CONCURRENT_ACCESS
	}

	private final WSG50Error error;

	private Wsg50Exception(GripperDriver gripperdriver, WSG50Error error) {
		super(gripperdriver);
		this.error = error;
	}

	/**
	 * Returns the error type of this exception.
	 */
	public final WSG50Error getError() {
		return error;
	}

	/**
	 * Returns the unique error number of this exception.
	 */
	public final int getErrorNumber() {
		return error.ordinal();
	}

	@Override
	public final boolean equals(Object obj) {
		return super.equals(obj) && ((Wsg50Exception) obj).getError() == getError();
	}

	@Override
	public final String toString() {
		return getError().toString() + " in " + super.toString();
	}

	/**
	 * Device, service or data is not available
	 */
	public static class WsgNotAvailableException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgNotAvailableException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_NOT_AVAILABLE);
		}
	}

	/**
	 * No sensor connected
	 */
	public static class WsgNoSensorException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgNoSensorException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_NO_SENSOR);
		}
	}

	/**
	 * The device is not initialized
	 */
	public static class WsgNotInitializedException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgNotInitializedException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_NOT_INITIALIZED);
		}
	}

	/**
	 * Service is already running
	 */
	public static class WsgAlreadyRunningException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgAlreadyRunningException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_ALREADY_RUNNING);
		}
	}

	/**
	 * The asked feature is not supported
	 */
	public static class WsgFeatureNotSupportedException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgFeatureNotSupportedException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_FEATURE_NOT_SUPPORTED);
		}
	}

	/**
	 * One or more dependent parameters mismatch
	 */
	public static class WsgInconsistentDataException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgInconsistentDataException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_INCONSISTENT_DATA);
		}
	}

	/**
	 * Timeout error
	 */
	public static class WsgTimeoutException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgTimeoutException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_TIMEOUT);
		}
	}

	/**
	 * Error while reading from a device
	 */
	public static class WsgReadErrorException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgReadErrorException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_READ_ERROR);
		}
	}

	/**
	 * Error while writing to a device
	 */
	public static class WsgWriteErrorException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgWriteErrorException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_WRITE_ERROR);
		}
	}

	/**
	 * No memory available
	 */
	public static class WsgInsufficientResourcesException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgInsufficientResourcesException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_INSUFFICIENT_RESOURCES);
		}
	}

	/**
	 * Checksum error
	 */
	public static class WsgChecksumErrorException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgChecksumErrorException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_CHECKSUM_ERROR);
		}
	}

	/**
	 * No parameters expected
	 */
	public static class WsgNoParamExpectedException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgNoParamExpectedException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_NO_PARAM_EXPECTED);
		}
	}

	/**
	 * Not enough parameters
	 */
	public static class WsgNotEnoughParamsException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgNotEnoughParamsException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_NOT_ENOUGH_PARAMS);
		}
	}

	/**
	 * Unknown command
	 */
	public static class WsgCmdUnknownException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgCmdUnknownException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_CMD_UNKNOWN);
		}
	}

	/**
	 * Command format error
	 */
	public static class WsgCmdFormatErrorException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgCmdFormatErrorException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_CMD_FORMAT_ERROR);
		}
	}

	/**
	 * Access denied
	 */
	public static class WsgAccessDeniedException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgAccessDeniedException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_ACCESS_DENIED);
		}
	}

	/**
	 * The interface is already open
	 */
	public static class WsgAlreadyOpenException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgAlreadyOpenException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_ALREADY_OPEN);
		}
	}

	/**
	 * Command failed
	 */
	public static class WsgCmdFailedException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgCmdFailedException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_CMD_FAILED);
		}
	}

	/**
	 * Command aborted
	 */
	public static class WsgCmdAbortedException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgCmdAbortedException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_CMD_ABORTED);
		}
	}

	/**
	 * invalid handle
	 */
	public static class WsgInvalidHandleException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgInvalidHandleException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_INVALID_HANDLE);
		}
	}

	/**
	 * device not found
	 */
	public static class WsgNotFoundException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgNotFoundException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_NOT_FOUND);
		}
	}

	/**
	 * device not open
	 */
	public static class WsgNotOpenException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgNotOpenException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_NOT_OPEN);
		}
	}

	/**
	 * I/O error
	 */
	public static class WsgIoErrorException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgIoErrorException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_IO_ERROR);
		}
	}

	/**
	 * invalid parameter
	 */
	public static class WsgInvalidParameterException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgInvalidParameterException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_INVALID_PARAMETER);
		}
	}

	/**
	 * index out of bounds
	 */
	public static class WsgIndexOutOfBoundsException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgIndexOutOfBoundsException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_INDEX_OUT_OF_BOUNDS);
		}
	}

	/**
	 * Command execution needs more time
	 */
	public static class WsgCmdPendingException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgCmdPendingException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_CMD_PENDING);
		}
	}

	/**
	 * Data overrun
	 */
	public static class WsgOverrunException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgOverrunException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_OVERRUN);
		}
	}

	/**
	 * Range error
	 */
	public static class WsgRangeErrorException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgRangeErrorException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_RANGE_ERROR);
		}
	}

	/**
	 * Axis is blocked
	 */
	public static class WsgAxisBlockedException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgAxisBlockedException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_AXIS_BLOCKED);
		}
	}

	/**
	 * File exists
	 */
	public static class WsgFileExistsException extends Wsg50Exception {
		private static final long serialVersionUID = 1L;

		public WsgFileExistsException(GripperDriver gripper) {
			super(gripper, WSG50Error.E_FILE_EXISTS);
		}
	}

}