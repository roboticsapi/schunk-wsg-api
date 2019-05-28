/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.runtime.mapper.prepositioning;

import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.mapping.MappingException;
import org.roboticsapi.runtime.mapping.parts.DeviceMappingPorts;
import org.roboticsapi.runtime.mapping.result.ActuatorDriverMapperResult;
import org.roboticsapi.runtime.rpi.RPIException;
import org.roboticsapi.schunk.wsg.action.Prepositioning.Type;
import org.roboticsapi.schunk.wsg.runtime.AbstractSoftRobotWSG50Driver;
import org.roboticsapi.schunk.wsg.runtime.AbstractSoftRobotWSG50Mapper;
import org.roboticsapi.schunk.wsg.runtime.PrimitiveWrapper;
import org.roboticsapi.schunk.wsg.runtime.primitives.Prepositioning;

public class SoftRobotSchunkWsgPrepositioningMapper extends AbstractSoftRobotWSG50Mapper<PrepositioningActionResult> {

	@Override
	public PrimitiveWrapper getMappedPrimitive(PrepositioningActionResult actionResult, String deviceName) {
		Prepositioning ar = new Prepositioning(actionResult.getBaseJawWidth(), actionResult.getSpeed(),
				(actionResult.getType() == Type.RELATIVE), actionResult.getStopOnBlock(), deviceName);
		return new PrimitiveWrapper(ar, ar.getOutFinished(), ar.getOutStatusCode());
	}

	@Override
	public ActuatorDriverMapperResult map(SoftRobotRuntime runtime, AbstractSoftRobotWSG50Driver deviceDriver,
			PrepositioningActionResult actionResult, DeviceParameterBag parameters, DeviceMappingPorts ports)
			throws MappingException, RPIException {
		return super.map(runtime, deviceDriver, actionResult, parameters, ports);
	}

}
