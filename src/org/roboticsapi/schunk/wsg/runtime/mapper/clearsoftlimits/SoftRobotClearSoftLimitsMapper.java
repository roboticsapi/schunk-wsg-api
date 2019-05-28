/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.runtime.mapper.clearsoftlimits;

import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.mapping.MappingException;
import org.roboticsapi.runtime.mapping.net.NetFragment;
import org.roboticsapi.runtime.mapping.parts.ActionMapper;
import org.roboticsapi.runtime.mapping.parts.ActionMappingContext;
import org.roboticsapi.runtime.mapping.result.ActionMapperResult;
import org.roboticsapi.runtime.mapping.result.impl.GoalActionMapperResult;
import org.roboticsapi.runtime.rpi.RPIException;
import org.roboticsapi.schunk.wsg.action.ClearSoftLimits;

public class SoftRobotClearSoftLimitsMapper implements ActionMapper<SoftRobotRuntime, ClearSoftLimits> {

	@Override
	public ActionMapperResult map(SoftRobotRuntime runtime, ClearSoftLimits action, DeviceParameterBag parameters,
			ActionMappingContext ports) throws MappingException, RPIException {

		return new GoalActionMapperResult(action, new NetFragment("ClearSoftLimits"),
				new ClearSoftLimitsActionResult());
	}

}
