/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.internal;

import org.roboticsapi.activity.Activity;
import org.roboticsapi.activity.ActivityNotCompletedException;
import org.roboticsapi.activity.ActuatorInterfaceImpl;
import org.roboticsapi.activity.RtActivity;
import org.roboticsapi.activity.SingleDeviceRtActivity;
import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.InvalidParametersException;
import org.roboticsapi.core.RuntimeCommand;
import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.schunk.wsg.WSG50;
import org.roboticsapi.schunk.wsg.action.Homing;
import org.roboticsapi.schunk.wsg.action.Prepositioning;
import org.roboticsapi.schunk.wsg.action.Releasing;
import org.roboticsapi.schunk.wsg.action.Homing.HomingDirection;
import org.roboticsapi.schunk.wsg.action.Prepositioning.Type;
import org.roboticsapi.schunk.wsg.activity.HoldingProperty;
import org.roboticsapi.tool.gripper.GripperParameters;
import org.roboticsapi.tool.gripper.activity.GraspingInterface;
import org.roboticsapi.tool.gripper.activity.HoldingActivity;
import org.roboticsapi.tool.gripper.parameters.AccelerationParameter;
import org.roboticsapi.tool.gripper.parameters.ForceParameter;
import org.roboticsapi.tool.gripper.parameters.VelocityParameter;

public class WsgGraspingInterfaceImpl extends ActuatorInterfaceImpl<WSG50> implements GraspingInterface {

	public WsgGraspingInterfaceImpl(WSG50 device) {
		super(device);
	}

	@Override
	public RtActivity openForReferencing() throws RoboticsException {
		return homing(HomingDirection.OPEN);
	}

	@Override
	public RtActivity closeForReferencing() throws RoboticsException {
		return homing(HomingDirection.CLOSE);
	}

	private RtActivity homing(HomingDirection direction, GripperParameters... gripperParameters)
			throws RoboticsException {
		validateParameters(gripperParameters);

		RuntimeCommand cmd = getDevice().getDriver().getRuntime().createRuntimeCommand(getDevice(),
				new Homing(direction));

		return new SingleDeviceRtActivity<WSG50>(getDevice(), cmd, null) {
			@Override
			protected boolean prepare(Activity prevActivity) throws RoboticsException, ActivityNotCompletedException {
				return false;
			}
		};
	}

	private void validateParameters(DeviceParameters[] parameters) throws InvalidParametersException {
		for (DeviceParameters param : parameters) {
			validateParameters(param);
		}
	}

	/**
	 * 
	 * @param type               , relative or absolute positioning
	 * @param fingerOpeningWidth in [m]
	 * @param stopOnBlock        , if blocked, silently stop or throw an exception
	 * @param gripperParameters
	 * @return
	 * @throws RoboticsException
	 */
	private RtActivity preposition(Type type, double fingerOpeningWidth, boolean stopOnBlock,
			GripperParameters... gripperParameters) throws RoboticsException {
		validateParameters(gripperParameters);
		DeviceParameterBag params = getDefaultParameters().withParameters(gripperParameters);

		double velocity = params.get(VelocityParameter.class).getVelocity();
		final double force = params.get(ForceParameter.class).getForce();
		final double acceleration = params.get(AccelerationParameter.class).getAcceleration();

		return new WsgActivity(getDevice(), new Prepositioning(type,
				getDevice().getBaseJawOpeningWidthFrom(fingerOpeningWidth), velocity, stopOnBlock), force,
				acceleration);
	}

	@Override
	public HoldingActivity grasp(double width, GripperParameters... gripperParameters) throws RoboticsException {
		validateParameters(gripperParameters);
		DeviceParameterBag params = getDefaultParameters().withParameters(gripperParameters);

		double velocity = params.get(VelocityParameter.class).getVelocity();
		final double force = params.get(ForceParameter.class).getForce();
		final double acceleration = params.get(AccelerationParameter.class).getAcceleration();

		return new HoldingActivityImpl(getDevice(), width, velocity, force, acceleration);
	}

	@Override
	public RtActivity release(double openWidth, GripperParameters... gripperParameters) throws RoboticsException {
		validateParameters(gripperParameters);
		DeviceParameterBag params = getDefaultParameters().withParameters(gripperParameters);

		double velocity = params.get(VelocityParameter.class).getVelocity();
		final double force = params.get(ForceParameter.class).getForce();
		final double acceleration = params.get(AccelerationParameter.class).getAcceleration();

		return new WsgActivity(getDevice(), new Releasing(getDevice().getBaseJawOpeningWidthFrom(openWidth), velocity),
				force, acceleration) {
			@Override
			protected boolean checkTakeOver(Activity prevActivity) throws RoboticsException {
				if (prevActivity != null && prevActivity.getProperty(getDevice(), HoldingProperty.class) != null) {
					return true;
				}
				return super.checkTakeOver(prevActivity);
			}
		};
	}

	// private RtActivity setAcceleration(double acceleration)
	// throws RoboticsException {
	// RuntimeCommand cmd = getDevice().getRuntime().createRuntimeCommand(
	// getDevice(), new SetAcceleration((float) acceleration));
	//
	// return new SingleDeviceRtActivity<WSG50>(getDevice(), cmd,
	// null) {
	// @Override
	// protected boolean prepare(Activity prevActivity)
	// throws RoboticsException, ActivityNotCompletedException {
	// return false;
	// }
	// };
	// }

	// private RtActivity setForceLimit(double force) throws RoboticsException {
	// RuntimeCommand cmd = getDevice().getRuntime().createRuntimeCommand(
	// getDevice(), new SetForceLimit((float) force));
	//
	// return new SingleDeviceRtActivity<WSG50>(getDevice(), cmd,
	// null) {
	// @Override
	// protected boolean prepare(Activity prevActivity)
	// throws RoboticsException, ActivityNotCompletedException {
	// return false;
	// }
	// };
	// }

	// private RtActivity setSoftLimits(double inner, double outer)
	// throws RoboticsException {
	// RuntimeCommand cmd = getDevice().getRuntime().createRuntimeCommand(
	// getDevice(), new SetSoftLimits((float) inner, (float) outer));
	//
	// return new SingleDeviceRtActivity<WSG50>(getDevice(), cmd,
	// null) {
	// @Override
	// protected boolean prepare(Activity prevActivity)
	// throws RoboticsException, ActivityNotCompletedException {
	// return false;
	// }
	// };
	// }

	// private RtActivity clearSoftLimits() throws RoboticsException {
	// RuntimeCommand cmd = getDevice().getRuntime().createRuntimeCommand(
	// getDevice(), new ClearSoftLimits());
	//
	// return new SingleDeviceRtActivity<WSG50>(getDevice(), cmd,
	// null) {
	// @Override
	// protected boolean prepare(Activity prevActivity)
	// throws RoboticsException, ActivityNotCompletedException {
	// return false;
	// }
	// };
	// }

	@Override
	public RtActivity openStepwise(double stepDistance, GripperParameters... gripperParameters)
			throws RoboticsException {
		return preposition(Type.RELATIVE, stepDistance, false, gripperParameters);
	}

	@Override
	public RtActivity closeStepwise(double stepDistance, GripperParameters... gripperParameters)
			throws RoboticsException {
		return preposition(Type.RELATIVE, -stepDistance, true, gripperParameters);
	}

	@Override
	public RtActivity preposition(double distance, GripperParameters... gripperParameters) throws RoboticsException {
		return preposition(Type.ABSOLUTE, distance, false, gripperParameters);
	}

	@Override
	public RtActivity open(GripperParameters... gripperParameters) throws RoboticsException {
		return preposition(Type.ABSOLUTE, getDevice().getMaximalOpeningWidth(), false, gripperParameters);
	}

	@Override
	public RtActivity close(GripperParameters... gripperParameters) throws RoboticsException {
		return preposition(Type.ABSOLUTE, getDevice().getMinimalOpeningWidth(), true, gripperParameters);
	}

}
