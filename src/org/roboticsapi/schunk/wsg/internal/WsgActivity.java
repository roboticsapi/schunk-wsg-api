/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.internal;

import java.util.ArrayList;
import java.util.List;

import org.roboticsapi.activity.Activity;
import org.roboticsapi.activity.ActivityNotCompletedException;
import org.roboticsapi.activity.SingleDeviceRtActivity;
import org.roboticsapi.core.Action;
import org.roboticsapi.core.Command;
import org.roboticsapi.core.EventHandler;
import org.roboticsapi.core.RuntimeCommand;
import org.roboticsapi.core.TransactionCommand;
import org.roboticsapi.core.eventhandler.CommandStarter;
import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.schunk.wsg.WSG50;
import org.roboticsapi.schunk.wsg.action.SetAcceleration;
import org.roboticsapi.schunk.wsg.action.SetForceLimit;
import org.roboticsapi.schunk.wsg.activity.AccelerationProperty;
import org.roboticsapi.schunk.wsg.activity.ForceProperty;
import org.roboticsapi.schunk.wsg.activity.HoldingProperty;

public class WsgActivity extends SingleDeviceRtActivity<WSG50> {

	private final Action action;
	private final double force;
	private final double acceleration;

	public WsgActivity(WSG50 device, Action action, double force, double acceleration) {
		super("WsgActivity<" + action.getClass().getSimpleName() + ">", device);
		this.action = action;
		this.force = force;
		this.acceleration = acceleration;
	}

	@Override
	protected boolean prepare(Activity prevActivity) throws RoboticsException, ActivityNotCompletedException {
		boolean canTakeOver = checkTakeOver(prevActivity);

		List<Command> commands = getInitCommands(prevActivity, force, acceleration);

		RuntimeCommand mainCommand = getDevice().getDriver().getRuntime().createRuntimeCommand(getDevice(), action);
		commands.add(mainCommand);

		TransactionCommand trans = getDevice().getDriver().getRuntime().createTransactionCommand();
		trans.addStartCommand(commands.get(0));
		for (int i = 1; i < commands.size(); i++) {
			trans.addCommand(commands.get(i));
			trans.addEventHandler(EventHandler.OnStateEntered(commands.get(i - 1).getCompletedState(),
					new CommandStarter(commands.get(i))));
		}

		addProperty(getDevice(), new ForceProperty(force));
		addProperty(getDevice(), new AccelerationProperty(acceleration));
		// TODO: Soft limits

		setCommand(trans, prevActivity);
		return canTakeOver;
	}

	protected boolean checkTakeOver(Activity prevActivity) throws RoboticsException {
		if (prevActivity != null && prevActivity.getProperty(getDevice(), HoldingProperty.class) != null) {
			throw new RoboticsException("Can not take over previous holding activity.");
		}
		return false;
	}

	private List<Command> getInitCommands(Activity prevActivity, double force, double acceleration)
			throws RoboticsException {
		List<Command> commands = new ArrayList<Command>();
		RuntimeCommand forceCommand = getForceCommandIfNeeded(prevActivity, force);
		if (forceCommand != null) {
			commands.add(forceCommand);
		}
		RuntimeCommand accelerationCommand = getAccelerationCommandIfNeeded(prevActivity, acceleration);
		if (accelerationCommand != null) {
			commands.add(accelerationCommand);
		}
		// TODO: SoftLimits

		return commands;
	}

	/**
	 * Checks whether the force value has to be set depending on previous actions,
	 * and if so a command is generated and returned.
	 * 
	 * @param prevActivity
	 * @param newForce
	 * @return
	 * @throws RoboticsException
	 */
	private final RuntimeCommand getForceCommandIfNeeded(Activity prevActivity, double newForce)
			throws RoboticsException {
		if (prevActivity != null) {
			ForceProperty prop = prevActivity.getProperty(getDevice(), ForceProperty.class);
			if (prop != null && prop.getForce() == newForce) {
				return null;
			}
		}
		return getDevice().getDriver().getRuntime().createRuntimeCommand(getDevice(), new SetForceLimit(newForce));
	}

	/**
	 * Checks whether the acceleration value has to be set depending on previous
	 * actions, and if so a command is generated and returned.
	 * 
	 * @param prevActivity
	 * @param newAcceleration
	 * @return
	 * @throws RoboticsException
	 */
	private final RuntimeCommand getAccelerationCommandIfNeeded(Activity prevActivity, double newAcceleration)
			throws RoboticsException {
		if (prevActivity != null) {
			AccelerationProperty prop = prevActivity.getProperty(getDevice(), AccelerationProperty.class);
			if (prop != null && prop.getAcceleration() == newAcceleration) {
				return null;
			}
		}
		return getDevice().getDriver().getRuntime().createRuntimeCommand(getDevice(),
				new SetAcceleration(newAcceleration));
	}
}
