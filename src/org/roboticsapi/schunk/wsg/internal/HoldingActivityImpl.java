/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.internal;

import org.roboticsapi.activity.Activity;
import org.roboticsapi.activity.ActivityNotCompletedException;
import org.roboticsapi.activity.SingleDeviceRtActivity;
import org.roboticsapi.core.Command;
import org.roboticsapi.core.EventHandler;
import org.roboticsapi.core.TransactionCommand;
import org.roboticsapi.core.eventhandler.CommandStarter;
import org.roboticsapi.core.eventhandler.ExceptionThrower;
import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.schunk.wsg.PartLostException;
import org.roboticsapi.schunk.wsg.WSG50;
import org.roboticsapi.schunk.wsg.action.Grasping;
import org.roboticsapi.schunk.wsg.activity.HoldingProperty;
import org.roboticsapi.schunk.wsg.sensor.GraspingStateSensor.GraspingState;
import org.roboticsapi.tool.gripper.activity.HoldingActivity;
import org.roboticsapi.world.TransformationException;

class HoldingActivityImpl extends SingleDeviceRtActivity<WSG50> implements HoldingActivity {

	private final double baseJawWidth;
	private final double speed;
	private final double force;
	private final double acceleration;

	/**
	 * 
	 * @param device
	 * @param width        of the part to hold in [m]
	 * @param speed
	 * @param force
	 * @param acceleration
	 * @throws TransformationException
	 */
	public HoldingActivityImpl(WSG50 device, double width, double speed, double force, double acceleration)
			throws TransformationException {
		super("WsgHoldingActivity", device);
		this.baseJawWidth = device.getBaseJawOpeningWidthFrom(width);
		this.speed = speed;
		this.force = force;
		this.acceleration = acceleration;
	}

	@Override
	protected boolean prepare(Activity prevActivity) throws RoboticsException, ActivityNotCompletedException {

		WsgActivity wsgActivity = new WsgActivity(getDevice(), new Grasping(baseJawWidth, speed), force, acceleration);
		wsgActivity.prepare(prevActivity);
		Command wsgCmd = wsgActivity.getCommand();

		Command command = getDevice().getDriver().getRuntime().createWaitCommand("Holding");
		command.addStateFirstEnteredHandler(
				getDevice().getGraspingStateSensor().hasGraspingState(GraspingState.PART_LOST),
				new ExceptionThrower(getDevice().getDriver().defineActuatorDriverException(PartLostException.class)));

		TransactionCommand trans = getDevice().getDriver().getRuntime().createTransactionCommand();
		trans.addStartCommand(wsgCmd);

		trans.addCommand(command);
		trans.addEventHandler(EventHandler.OnStateEntered(wsgCmd.getCompletedState(), new CommandStarter(command)));

		trans.addTakeoverAllowedCondition(command.getActiveState());
		addProperty(getDevice(), new HoldingProperty());

		setCommand(trans, prevActivity, command.getActiveState());

		return false;
	}
}