/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.runtime.mapper.monitor;

import org.roboticsapi.core.Command;
import org.roboticsapi.core.PersistContext;
import org.roboticsapi.core.SensorListener;
import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.mapping.MappingException;
import org.roboticsapi.runtime.mapping.net.DataflowOutPort;
import org.roboticsapi.runtime.mapping.net.DoubleDataflow;
import org.roboticsapi.runtime.mapping.parts.SensorMapper;
import org.roboticsapi.runtime.mapping.parts.SensorMappingContext;
import org.roboticsapi.runtime.mapping.parts.SensorMappingPorts;
import org.roboticsapi.runtime.mapping.result.SensorMapperResult;
import org.roboticsapi.runtime.mapping.result.impl.BaseSensorMapperResult;
import org.roboticsapi.schunk.wsg.sensor.GraspingStateSensor;
import org.roboticsapi.schunk.wsg.sensor.GraspingStateSensor.GraspingState;

public class SoftRobotGraspingStateSensorMapper
		implements SensorMapper<SoftRobotRuntime, GraspingState, GraspingStateSensor> {

	@Override
	public SensorMapperResult<GraspingState> map(SoftRobotRuntime runtime, GraspingStateSensor sensor,
			SensorMappingPorts ports, SensorMappingContext context) throws MappingException {

		SensorMapperResult<Double> mappedInnerSensor = runtime.getMapperRegistry().mapSensor(runtime,
				sensor.getDoubleSensor(), null, context);

		DataflowOutPort sensorPort = mappedInnerSensor.getNetFragment().reinterpret(mappedInnerSensor.getSensorPort(),
				new DoubleDataflow());

		try {
			return new GraspingStateSensorMapperResult(sensor, mappedInnerSensor, sensorPort);
		} catch (RoboticsException e) {
			throw new MappingException(e);
		}
	}

	private class GraspingStateSensorMapperResult extends BaseSensorMapperResult<GraspingState> {

		private final SensorMapperResult<Double> innerSensor;

		public GraspingStateSensorMapperResult(final GraspingStateSensor sensor, SensorMapperResult<Double> innerSensor,
				DataflowOutPort sensorPort) throws RoboticsException {
			super(innerSensor.getNetFragment(), sensorPort);
			this.innerSensor = innerSensor;
		}

		@Override
		public void addListener(Command command, final SensorListener<GraspingState> listener) throws MappingException {
			innerSensor.addListener(command, new SensorListener<Double>() {
				@Override
				public void onValueChanged(Double newValue) {
					listener.onValueChanged(GraspingState.values()[newValue.intValue()]);
				}
			});
		}

		@Override
		public void addUpdateListener(Command command, final SensorUpdateListener<GraspingState> listener)
				throws MappingException {

			innerSensor.addUpdateListener(command, new SensorUpdateListener<Double>() {
				@Override
				public void onValueChanged(Double newValue) {
					listener.onValueChanged(GraspingState.values()[newValue.intValue()]);
				}

				@Override
				public void updatePerformed() {
					listener.updatePerformed();
				}
			});
		}

		@Override
		public void assign(Command command, PersistContext<GraspingState> target) throws MappingException {
			// FIXME add
		}
	}
}