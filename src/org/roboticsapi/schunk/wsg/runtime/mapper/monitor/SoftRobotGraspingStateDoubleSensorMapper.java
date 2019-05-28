/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.runtime.mapper.monitor;

import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.mapping.MappingException;
import org.roboticsapi.runtime.mapping.net.DataflowOutPort;
import org.roboticsapi.runtime.mapping.net.DoubleDataflow;
import org.roboticsapi.runtime.mapping.net.NetFragment;
import org.roboticsapi.runtime.mapping.parts.SensorMapper;
import org.roboticsapi.runtime.mapping.parts.SensorMappingContext;
import org.roboticsapi.runtime.mapping.parts.SensorMappingPorts;
import org.roboticsapi.runtime.mapping.result.SensorMapperResult;
import org.roboticsapi.runtime.mapping.result.impl.DoubleSensorMapperResult;
import org.roboticsapi.schunk.wsg.runtime.AbstractSoftRobotWSG50Driver;
import org.roboticsapi.schunk.wsg.runtime.primitives.Monitor;

public class SoftRobotGraspingStateDoubleSensorMapper
		implements SensorMapper<SoftRobotRuntime, Double, AbstractSoftRobotWSG50Driver.GraspingStateDoubleSensor> {

	@Override
	public SensorMapperResult<Double> map(final SoftRobotRuntime runtime,
			final AbstractSoftRobotWSG50Driver.GraspingStateDoubleSensor sensor, SensorMappingPorts ports,
			SensorMappingContext context) throws MappingException {
		final NetFragment ret = new NetFragment("SoftRobotGraspingStateDoubleSensor");
		final Monitor m = new Monitor(sensor.getDriver().getDeviceName());

		ret.add(m);

		DataflowOutPort port = ret.addOutPort(new DoubleDataflow(), true, m.getOutGraspingState());

		return new DoubleSensorMapperResult(ret, port);
	}

}
