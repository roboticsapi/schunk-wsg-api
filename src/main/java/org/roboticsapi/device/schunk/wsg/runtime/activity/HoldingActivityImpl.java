/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.runtime.activity;

import java.util.Set;

import org.roboticsapi.core.Command;
import org.roboticsapi.core.CommandResult;
import org.roboticsapi.core.activity.ActivityResult;
import org.roboticsapi.core.activity.runtime.SingleDeviceActivity;
import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.core.world.TransformationException;
import org.roboticsapi.device.schunk.wsg.runtime.WsgGenericDriver;
import org.roboticsapi.device.schunk.wsg.runtime.action.Grasping;
import org.roboticsapi.framework.gripper.activity.HoldingActivity;

public class HoldingActivityImpl extends SingleDeviceActivity implements HoldingActivity {

	private final double baseJawWidth;
	private final double speed;
	private final double force;
	private final double acceleration;
	private WsgGenericDriver driver;

	/**
	 *
	 * @param device
	 * @param width        of the part to hold in [m]
	 * @param speed
	 * @param force
	 * @param acceleration
	 * @throws TransformationException
	 */
	public HoldingActivityImpl(WsgGenericDriver driver, double width, double speed, double force,
			double acceleration) throws TransformationException {
		super("WsgHoldingActivity", driver);
		this.driver = driver;
		this.baseJawWidth = driver.getDevice().getBaseJawOpeningWidthFrom(width);
		this.speed = speed;
		this.force = force;
		this.acceleration = acceleration;
	}

	@Override
	protected Set<ActivityResult> getResultsForCommand(Command command) throws RoboticsException {
		Set<ActivityResult> ret = activityResultSet();
		for (CommandResult complete : command.getCompletionResults()) {
			if (complete.isCancelled())
				ret.add(cancelResult(complete));
			else
				ret.add(completionResult(complete, new HoldingProperty()));
		}
		return ret;
	}

	@Override
	protected Command getCommandForResult(ActivityResult result) throws RoboticsException {
		WsgActivity wsgActivity = new WsgActivity(driver, new Grasping(baseJawWidth, speed), force, acceleration);
		Command wsgCmd = wsgActivity.getCommandForResult(result);

//		Command command = getDriver().getRuntime().createWaitCommand("Holding");
//		List<ActuatorDriverRealtimeException> errors = driver.defineActuatorDriverExceptions();
//		PartLostException partLost = null;
//		for (ActuatorDriverRealtimeException error : errors)
//			if (error instanceof PartLostException)
//				partLost = (PartLostException) error;
//		
//		if (partLost==null) throw new RoboticsException("Exception is not defined");
//
//		command.addExceptionCondition(
//				getDevice().use(GraspingStateSensorInterface.class).getGraspingState().isEqual(GraspingState.PART_LOST),
//				partLost);
		// TODO: return sequence of wsgCmd and command
		return wsgCmd;
	}
}