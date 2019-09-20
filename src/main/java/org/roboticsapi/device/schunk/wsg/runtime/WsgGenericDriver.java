/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.runtime;

import java.util.List;
import java.util.Map;

import org.roboticsapi.core.actuator.ActuatorDriverRealtimeException;
import org.roboticsapi.core.realtimevalue.realtimedouble.DriverBasedRealtimeDouble;
import org.roboticsapi.core.realtimevalue.realtimedouble.RealtimeDouble;
import org.roboticsapi.core.realtimevalue.realtimeinteger.DriverBasedRealtimeInteger;
import org.roboticsapi.device.schunk.wsg.RealtimeGraspingState;
import org.roboticsapi.device.schunk.wsg.Wsg50;
import org.roboticsapi.device.schunk.wsg.activity.GraspingStateSensorInterface;
import org.roboticsapi.device.schunk.wsg.runtime.activity.GraspingStateSensorInterfaceImpl;
import org.roboticsapi.device.schunk.wsg.runtime.activity.GripperAccelerationSensorInterfaceImpl;
import org.roboticsapi.device.schunk.wsg.runtime.activity.GripperForceSensorInterfaceImpl;
import org.roboticsapi.device.schunk.wsg.runtime.activity.GripperOpeningWidthSensorInterfaceImpl;
import org.roboticsapi.device.schunk.wsg.runtime.activity.GripperVelocitySensorInterfaceImpl;
import org.roboticsapi.device.schunk.wsg.runtime.activity.WsgGraspingInterfaceImpl;
import org.roboticsapi.device.schunk.wsg.runtime.exception.PartLostException;
import org.roboticsapi.device.schunk.wsg.runtime.exception.Wsg50Exception;
import org.roboticsapi.facet.runtime.rpi.RpiParameters;
import org.roboticsapi.framework.gripper.GripperDriver;
import org.roboticsapi.framework.gripper.activity.GraspingInterface;
import org.roboticsapi.framework.gripper.activity.GripperAccelerationSensorInterface;
import org.roboticsapi.framework.gripper.activity.GripperForceSensorInterface;
import org.roboticsapi.framework.gripper.activity.GripperOpeningWidthSensorInterface;
import org.roboticsapi.framework.gripper.activity.GripperVelocitySensorInterface;
import org.roboticsapi.framework.gripper.runtime.AbstractParallelGripperDriver;

public class WsgGenericDriver extends AbstractParallelGripperDriver<Wsg50> implements GripperDriver {

	@Override
	protected boolean checkDeviceType(String deviceType) {
		return true;
	}

	public static final class RealtimeOpeningWidth extends DriverBasedRealtimeDouble<WsgGenericDriver> {
		public RealtimeOpeningWidth(WsgGenericDriver gripper) {
			super(gripper);
		}
	}

	@Override
	public RealtimeDouble getBaseJawOpeningWidth() {
		return new RealtimeOpeningWidth(this);
	}

	/**
	 * {@link RealtimeDouble} implementation for measuring the velocity of a
	 * {@link Wsg50} using the {@link WsgGenericDriver}.
	 */
	public static final class RealtimeVelocity extends DriverBasedRealtimeDouble<WsgGenericDriver> {
		public RealtimeVelocity(WsgGenericDriver gripper) {
			super(gripper);
		}
	}

	public final RealtimeDouble getVelocity() {
		return new RealtimeVelocity(this);
	}

	/**
	 * {@link RealtimeDouble} implementation for measuring the gripping force of a
	 * {@link Wsg50} using the {@link WsgGenericDriver}.
	 */
	public static final class RealtimeForce extends DriverBasedRealtimeDouble<WsgGenericDriver> {
		public RealtimeForce(WsgGenericDriver gripper) {
			super(gripper);
		}
	}

	public final RealtimeDouble getForce() {
		return new RealtimeForce(this);
	}

	/**
	 * {@link RealtimeDouble} implementation for measuring the acceleration of a
	 * {@link Wsg50} using the {@link WsgGenericDriver}.
	 */
	public static final class RealtimeAcceleration extends DriverBasedRealtimeDouble<WsgGenericDriver> {
		public RealtimeAcceleration(WsgGenericDriver gripper) {
			super(gripper);
		}
	}

	public final RealtimeDouble getAcceleration() {
		return new RealtimeAcceleration(this);
	}

	/**
	 * {@link RealtimeDouble} implementation for measuring the grasping state of a
	 * {@link Wsg50} using the {@link WsgGenericDriver}.
	 */
	public static final class RealtimeGraspingStateInteger extends DriverBasedRealtimeInteger<WsgGenericDriver> {
		public RealtimeGraspingStateInteger(WsgGenericDriver gripper) {
			super(gripper);
		}
	}

	public final RealtimeGraspingState getGraspingState() {
		return new RealtimeGraspingState(new RealtimeGraspingStateInteger(this));
	}

	@Override
	public List<ActuatorDriverRealtimeException> defineActuatorDriverExceptions() {
		List<ActuatorDriverRealtimeException> actuatorExceptions = super.defineActuatorDriverExceptions();

		actuatorExceptions.add(new Wsg50Exception.WsgNotAvailableException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgNoSensorException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgNotInitializedException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgAlreadyRunningException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgFeatureNotSupportedException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgInconsistentDataException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgTimeoutException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgReadErrorException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgWriteErrorException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgInsufficientResourcesException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgChecksumErrorException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgNoParamExpectedException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgNotEnoughParamsException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgCmdUnknownException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgCmdFormatErrorException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgAccessDeniedException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgAlreadyOpenException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgCmdFailedException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgCmdAbortedException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgInvalidHandleException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgNotFoundException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgNotOpenException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgIoErrorException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgInvalidParameterException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgIndexOutOfBoundsException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgCmdPendingException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgOverrunException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgRangeErrorException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgAxisBlockedException(this));
		actuatorExceptions.add(new Wsg50Exception.WsgFileExistsException(this));
		actuatorExceptions.add(new PartLostException(this));

		return actuatorExceptions;
	}

	@Override
	protected final boolean checkRpiDeviceInterfaces(Map<String, RpiParameters> interfaces) {
		return true; // no interfaces until now
	}

	@Override
	protected void afterInitialization() {
		super.afterInitialization();

		getDevice().addInterfaceFactory(GraspingInterface.class, () -> new WsgGraspingInterfaceImpl(this));
		getDevice().addInterfaceFactory(GripperOpeningWidthSensorInterface.class,
				() -> new GripperOpeningWidthSensorInterfaceImpl(this));
		getDevice().addInterfaceFactory(GripperForceSensorInterface.class,
				() -> new GripperForceSensorInterfaceImpl(this));
		getDevice().addInterfaceFactory(GripperVelocitySensorInterface.class,
				() -> new GripperVelocitySensorInterfaceImpl(this));
		getDevice().addInterfaceFactory(GripperAccelerationSensorInterface.class,
				() -> new GripperAccelerationSensorInterfaceImpl(this));
		getDevice().addInterfaceFactory(GraspingStateSensorInterface.class,
				() -> new GraspingStateSensorInterfaceImpl(this));

	}

	@Override
	protected void beforeUninitialization() {
		super.beforeUninitialization();
	}

}
