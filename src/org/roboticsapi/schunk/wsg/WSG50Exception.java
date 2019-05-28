/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg;

import org.roboticsapi.tool.gripper.GripperException;

/**
 * Abstract definition of an exception which can occur by the wsg gripper.
 */
public abstract class WSG50Exception extends GripperException {

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

	private WSG50Exception(WSG50GripperDriver gripperdriver, WSG50Error error) {
		super(gripperdriver);
		this.error = error;
	}

	/**
	 * Returns the error type of this exception.
	 * 
	 * @return
	 */
	public final WSG50Error getError() {
		return error;
	}

	/**
	 * Returns the unique error number of this exception.
	 * 
	 * @return
	 */
	public final int getErrorNumber() {
		return error.ordinal();
	}

	@Override
	public final boolean equals(Object obj) {
		return super.equals(obj) && ((WSG50Exception) obj).getError() == getError();
	}

	@Override
	public final String toString() {
		return getError().toString() + " in " + super.toString();
	}

	/**
	 * Device, service or data is not available
	 */
	public static class WsgNotAvailableException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgNotAvailableException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_NOT_AVAILABLE);
		}
	}

	/**
	 * No sensor connected
	 */
	public static class WsgNoSensorException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgNoSensorException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_NO_SENSOR);
		}
	}

	/**
	 * The device is not initialized
	 */
	public static class WsgNotInitializedException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgNotInitializedException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_NOT_INITIALIZED);
		}
	}

	/**
	 * Service is already running
	 */
	public static class WsgAlreadyRunningException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgAlreadyRunningException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_ALREADY_RUNNING);
		}
	}

	/**
	 * The asked feature is not supported
	 */
	public static class WsgFeatureNotSupportedException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgFeatureNotSupportedException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_FEATURE_NOT_SUPPORTED);
		}
	}

	/**
	 * One or more dependent parameters mismatch
	 */
	public static class WsgInconsistentDataException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgInconsistentDataException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_INCONSISTENT_DATA);
		}
	}

	/**
	 * Timeout error
	 */
	public static class WsgTimeoutException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgTimeoutException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_TIMEOUT);
		}
	}

	/**
	 * Error while reading from a device
	 */
	public static class WsgReadErrorException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgReadErrorException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_READ_ERROR);
		}
	}

	/**
	 * Error while writing to a device
	 */
	public static class WsgWriteErrorException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgWriteErrorException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_WRITE_ERROR);
		}
	}

	/**
	 * No memory available
	 */
	public static class WsgInsufficientResourcesException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgInsufficientResourcesException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_INSUFFICIENT_RESOURCES);
		}
	}

	/**
	 * Checksum error
	 */
	public static class WsgChecksumErrorException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgChecksumErrorException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_CHECKSUM_ERROR);
		}
	}

	/**
	 * No parameters expected
	 */
	public static class WsgNoParamExpectedException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgNoParamExpectedException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_NO_PARAM_EXPECTED);
		}
	}

	/**
	 * Not enough parameters
	 */
	public static class WsgNotEnoughParamsException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgNotEnoughParamsException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_NOT_ENOUGH_PARAMS);
		}
	}

	/**
	 * Unknown command
	 */
	public static class WsgCmdUnknownException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgCmdUnknownException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_CMD_UNKNOWN);
		}
	}

	/**
	 * Command format error
	 */
	public static class WsgCmdFormatErrorException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgCmdFormatErrorException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_CMD_FORMAT_ERROR);
		}
	}

	/**
	 * Access denied
	 */
	public static class WsgAccessDeniedException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgAccessDeniedException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_ACCESS_DENIED);
		}
	}

	/**
	 * The interface is already open
	 */
	public static class WsgAlreadyOpenException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgAlreadyOpenException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_ALREADY_OPEN);
		}
	}

	/**
	 * Command failed
	 */
	public static class WsgCmdFailedException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgCmdFailedException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_CMD_FAILED);
		}
	}

	/**
	 * Command aborted
	 */
	public static class WsgCmdAbortedException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgCmdAbortedException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_CMD_ABORTED);
		}
	}

	/**
	 * invalid handle
	 */
	public static class WsgInvalidHandleException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgInvalidHandleException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_INVALID_HANDLE);
		}
	}

	/**
	 * device not found
	 */
	public static class WsgNotFoundException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgNotFoundException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_NOT_FOUND);
		}
	}

	/**
	 * device not open
	 */
	public static class WsgNotOpenException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgNotOpenException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_NOT_OPEN);
		}
	}

	/**
	 * I/O error
	 */
	public static class WsgIoErrorException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgIoErrorException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_IO_ERROR);
		}
	}

	/**
	 * invalid parameter
	 */
	public static class WsgInvalidParameterException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgInvalidParameterException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_INVALID_PARAMETER);
		}
	}

	/**
	 * index out of bounds
	 */
	public static class WsgIndexOutOfBoundsException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgIndexOutOfBoundsException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_INDEX_OUT_OF_BOUNDS);
		}
	}

	/**
	 * Command execution needs more time
	 */
	public static class WsgCmdPendingException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgCmdPendingException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_CMD_PENDING);
		}
	}

	/**
	 * Data overrun
	 */
	public static class WsgOverrunException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgOverrunException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_OVERRUN);
		}
	}

	/**
	 * Range error
	 */
	public static class WsgRangeErrorException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgRangeErrorException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_RANGE_ERROR);
		}
	}

	/**
	 * Axis is blocked
	 */
	public static class WsgAxisBlockedException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgAxisBlockedException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_AXIS_BLOCKED);
		}
	}

	/**
	 * file exists
	 */
	public static class WsgFileExistsException extends WSG50Exception {
		private static final long serialVersionUID = 1L;

		public WsgFileExistsException(WSG50GripperDriver gripper) {
			super(gripper, WSG50Error.E_FILE_EXISTS);
		}
	}

}