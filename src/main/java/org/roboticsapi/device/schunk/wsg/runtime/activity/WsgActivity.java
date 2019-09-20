/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.runtime.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.roboticsapi.core.Action;
import org.roboticsapi.core.Command;
import org.roboticsapi.core.CommandResult;
import org.roboticsapi.core.RuntimeCommand;
import org.roboticsapi.core.activity.ActivityResult;
import org.roboticsapi.core.activity.runtime.SingleDeviceActivity;
import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.device.schunk.wsg.runtime.WsgGenericDriver;
import org.roboticsapi.device.schunk.wsg.runtime.action.SetAcceleration;
import org.roboticsapi.device.schunk.wsg.runtime.action.SetForceLimit;

public class WsgActivity extends SingleDeviceActivity {

	private final Action action;
	private final double force;
	private final double acceleration;

	public WsgActivity(WsgGenericDriver driver, Action action, double force, double acceleration) {
		super("WsgActivity<" + action.getClass().getSimpleName() + ">", driver);
		this.action = action;
		this.force = force;
		this.acceleration = acceleration;
	}

	protected boolean checkTakeOver(ActivityResult prevActivity) {
		if (prevActivity != null && prevActivity.getMetadata(getDevice(), HoldingProperty.class) != null) {
			return false;
		}
		return true;
	}

	private List<Command> getInitCommands(ActivityResult prevActivity, double force, double acceleration)
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
	private final RuntimeCommand getForceCommandIfNeeded(ActivityResult prevActivity, double newForce)
			throws RoboticsException {
		if (prevActivity != null) {
			ForceProperty prop = prevActivity.getMetadata(getDevice(), ForceProperty.class);
			if (prop != null && prop.getForce() == newForce) {
				return null;
			}
		}
		return createRuntimeCommand(new SetForceLimit(newForce));
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
	private final RuntimeCommand getAccelerationCommandIfNeeded(ActivityResult prevActivity, double newAcceleration)
			throws RoboticsException {
		if (prevActivity != null) {
			AccelerationProperty prop = prevActivity.getMetadata(getDevice(), AccelerationProperty.class);
			if (prop != null && prop.getAcceleration() == newAcceleration) {
				return null;
			}
		}
		return createRuntimeCommand(new SetAcceleration(newAcceleration));
	}

	@Override
	protected Set<ActivityResult> getResultsForCommand(Command command) throws RoboticsException {
		Set<ActivityResult> ret = activityResultSet();
		for (CommandResult complete : command.getCompletionResults()) {
			if (complete.isCancelled())
				ret.add(cancelResult(complete));
			else
				ret.add(completionResult(complete, new ForceProperty(force), new AccelerationProperty(acceleration)));
		}
		return ret;
	}

	@Override
	protected Command getCommandForResult(ActivityResult result) throws RoboticsException {
		boolean canTakeOver = checkTakeOver(result);
		if (!canTakeOver)
			return null;
//		List<Command> commands = getInitCommands(result, force, acceleration);

		RuntimeCommand mainCommand = createRuntimeCommand(action);
		// TOOD: add init commands
//		commands.add(mainCommand);

		return mainCommand;
	}
}
