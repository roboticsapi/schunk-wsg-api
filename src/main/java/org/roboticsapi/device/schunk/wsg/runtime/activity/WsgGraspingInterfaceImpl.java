/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.runtime.activity;

import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.InvalidParametersException;
import org.roboticsapi.core.activity.Activity;
import org.roboticsapi.core.activity.ActivityResult;
import org.roboticsapi.core.activity.runtime.ActuatorInterfaceImpl;
import org.roboticsapi.core.activity.runtime.FromCommandActivity;
import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.device.schunk.wsg.Wsg50;
import org.roboticsapi.device.schunk.wsg.runtime.WsgGenericDriver;
import org.roboticsapi.device.schunk.wsg.runtime.action.Grasping;
import org.roboticsapi.device.schunk.wsg.runtime.action.Homing;
import org.roboticsapi.device.schunk.wsg.runtime.action.Homing.HomingDirection;
import org.roboticsapi.device.schunk.wsg.runtime.action.Prepositioning;
import org.roboticsapi.device.schunk.wsg.runtime.action.Prepositioning.Type;
import org.roboticsapi.device.schunk.wsg.runtime.action.Releasing;
import org.roboticsapi.framework.gripper.GripperParameters;
import org.roboticsapi.framework.gripper.activity.GraspingInterface;
import org.roboticsapi.framework.gripper.parameters.AccelerationParameter;
import org.roboticsapi.framework.gripper.parameters.ForceParameter;
import org.roboticsapi.framework.gripper.parameters.VelocityParameter;

public class WsgGraspingInterfaceImpl extends ActuatorInterfaceImpl implements GraspingInterface {

	private WsgGenericDriver driver;

	public WsgGraspingInterfaceImpl(WsgGenericDriver driver) {
		super(driver);
		this.driver = driver;
	}

	@Override
	public Activity openForReferencing() throws RoboticsException {
		return homing(HomingDirection.OPEN);
	}

	@Override
	public Activity closeForReferencing() throws RoboticsException {
		return homing(HomingDirection.CLOSE);
	}

	private Activity homing(HomingDirection direction, GripperParameters... gripperParameters)
			throws RoboticsException {
		validateParameters(gripperParameters);

		return new FromCommandActivity("Homing", () -> getDevice().getDriver().getRuntime()
				.createRuntimeCommand(getDevice().getDriver(), new Homing(direction)), getDevice());
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
	private Activity preposition(Type type, double fingerOpeningWidth, boolean stopOnBlock,
			GripperParameters... gripperParameters) throws RoboticsException {
		validateParameters(gripperParameters);
		DeviceParameterBag params = getDefaultParameters().withParameters(gripperParameters);

		double velocity = params.get(VelocityParameter.class).getVelocity();
		final double force = params.get(ForceParameter.class).getForce();
		final double acceleration = params.get(AccelerationParameter.class).getAcceleration();

		return new WsgActivity(driver, new Prepositioning(type,
				((Wsg50) getDevice()).getBaseJawOpeningWidthFrom(fingerOpeningWidth), velocity, stopOnBlock),
				force, acceleration);
	}

	@Override
	public Activity grasp(double fingerOpeningWidth, GripperParameters... gripperParameters) throws RoboticsException {
		validateParameters(gripperParameters);
		DeviceParameterBag params = getDefaultParameters().withParameters(gripperParameters);

		double velocity = params.get(VelocityParameter.class).getVelocity();
		final double force = params.get(ForceParameter.class).getForce();
		final double acceleration = params.get(AccelerationParameter.class).getAcceleration();

//		return new HoldingActivityImpl(driver, width, velocity, force, acceleration);
		return new WsgActivity(driver,
				new Grasping(((Wsg50) getDevice()).getBaseJawOpeningWidthFrom(fingerOpeningWidth), velocity),
				force, acceleration);
	}

	@Override
	public Activity release(double openWidth, GripperParameters... gripperParameters) throws RoboticsException {
		validateParameters(gripperParameters);
		DeviceParameterBag params = getDefaultParameters().withParameters(gripperParameters);

		double velocity = params.get(VelocityParameter.class).getVelocity();
		final double force = params.get(ForceParameter.class).getForce();
		final double acceleration = params.get(AccelerationParameter.class).getAcceleration();

		return new WsgActivity(driver,
				new Releasing(((Wsg50) getDevice()).getBaseJawOpeningWidthFrom(openWidth), velocity), force,
				acceleration) {
			@Override
			protected boolean checkTakeOver(ActivityResult prevActivity) {
				if (prevActivity != null && prevActivity.getMetadata(getDevice(), HoldingProperty.class) != null) {
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
	// return new SingleDeviceRtActivity<SchunkWsg50>(getDevice(), cmd,
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
	// return new SingleDeviceRtActivity<SchunkWsg50>(getDevice(), cmd,
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
	// return new SingleDeviceRtActivity<SchunkWsg50>(getDevice(), cmd,
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
	// return new SingleDeviceRtActivity<SchunkWsg50>(getDevice(), cmd,
	// null) {
	// @Override
	// protected boolean prepare(Activity prevActivity)
	// throws RoboticsException, ActivityNotCompletedException {
	// return false;
	// }
	// };
	// }

	@Override
	public Activity openStepwise(double stepDistance, GripperParameters... gripperParameters) throws RoboticsException {
		return preposition(Type.RELATIVE, stepDistance, false, gripperParameters);
	}

	@Override
	public Activity closeStepwise(double stepDistance, GripperParameters... gripperParameters)
			throws RoboticsException {
		return preposition(Type.RELATIVE, -stepDistance, true, gripperParameters);
	}

	@Override
	public Activity preposition(double distance, GripperParameters... gripperParameters) throws RoboticsException {
		return preposition(Type.ABSOLUTE, distance, false, gripperParameters);
	}

	@Override
	public Activity open(GripperParameters... gripperParameters) throws RoboticsException {
		return preposition(Type.ABSOLUTE, ((Wsg50) getDevice()).getMaximalOpeningWidth(), false,
				gripperParameters);
	}

	@Override
	public Activity close(GripperParameters... gripperParameters) throws RoboticsException {
		return preposition(Type.ABSOLUTE, ((Wsg50) getDevice()).getMinimalOpeningWidth(), true,
				gripperParameters);
	}

}
