/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.runtime.mapper.homing;

import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.mapping.MappingException;
import org.roboticsapi.runtime.mapping.parts.DeviceMappingPorts;
import org.roboticsapi.runtime.mapping.result.ActuatorDriverMapperResult;
import org.roboticsapi.runtime.rpi.RPIException;
import org.roboticsapi.schunk.wsg.action.Homing.HomingDirection;
import org.roboticsapi.schunk.wsg.runtime.AbstractSoftRobotWSG50Driver;
import org.roboticsapi.schunk.wsg.runtime.AbstractSoftRobotWSG50Mapper;
import org.roboticsapi.schunk.wsg.runtime.PrimitiveWrapper;
import org.roboticsapi.schunk.wsg.runtime.primitives.Homing;

public class SoftRobotSchunkWsgHomingMapper extends AbstractSoftRobotWSG50Mapper<HomingActionResult> {

	@Override
	public PrimitiveWrapper getMappedPrimitive(HomingActionResult actionResult, String deviceName) {
		Homing ar = new Homing((actionResult.getDirection() == HomingDirection.OPEN), deviceName);
		return new PrimitiveWrapper(ar, ar.getOutFinished(), ar.getOutStatusCode());
	}

	@Override
	public ActuatorDriverMapperResult map(SoftRobotRuntime runtime, AbstractSoftRobotWSG50Driver device,
			HomingActionResult actionResult, DeviceParameterBag parameters, DeviceMappingPorts ports)
			throws MappingException, RPIException {
		return super.map(runtime, device, actionResult, parameters, ports);
	}

}
