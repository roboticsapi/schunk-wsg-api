/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.runtime.extension;

import java.util.HashMap;
import java.util.Map;

import org.roboticsapi.core.RoboticsObject;
import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.driver.DeviceBasedInstantiator;
import org.roboticsapi.runtime.extension.AbstractSoftRobotRoboticsBuilder;
import org.roboticsapi.runtime.mapping.MapperRegistry;
import org.roboticsapi.schunk.wsg.WSG50;
import org.roboticsapi.schunk.wsg.WSG50GripperDriver;
import org.roboticsapi.schunk.wsg.action.ClearSoftLimits;
import org.roboticsapi.schunk.wsg.action.Grasping;
import org.roboticsapi.schunk.wsg.action.Homing;
import org.roboticsapi.schunk.wsg.action.Prepositioning;
import org.roboticsapi.schunk.wsg.action.Releasing;
import org.roboticsapi.schunk.wsg.action.SetAcceleration;
import org.roboticsapi.schunk.wsg.action.SetForceLimit;
import org.roboticsapi.schunk.wsg.action.SetSoftLimits;
import org.roboticsapi.schunk.wsg.runtime.AbstractSoftRobotWSG50Driver;
import org.roboticsapi.schunk.wsg.runtime.CANBasedWSG50Driver;
import org.roboticsapi.schunk.wsg.runtime.SimulatedWSG50Driver;
import org.roboticsapi.schunk.wsg.runtime.mapper.clearsoftlimits.ClearSoftLimitsActionResult;
import org.roboticsapi.schunk.wsg.runtime.mapper.clearsoftlimits.SoftRobotClearSoftLimitsMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.clearsoftlimits.SoftRobotSchunkWsgClearSoftLimitsMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.grasping.GraspingActionResult;
import org.roboticsapi.schunk.wsg.runtime.mapper.grasping.SoftRobotGraspingMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.grasping.SoftRobotSchunkWsgGraspingMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.homing.HomingActionResult;
import org.roboticsapi.schunk.wsg.runtime.mapper.homing.SoftRobotHomingMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.homing.SoftRobotSchunkWsgHomingMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.monitor.SoftRobotAccelerationSensorMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.monitor.SoftRobotForceSensorMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.monitor.SoftRobotGraspingStateDoubleSensorMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.monitor.SoftRobotGraspingStateSensorMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.monitor.SoftRobotOpeningWidthSensorMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.monitor.SoftRobotVelocitySensorMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.prepositioning.PrepositioningActionResult;
import org.roboticsapi.schunk.wsg.runtime.mapper.prepositioning.SoftRobotPrepositioningMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.prepositioning.SoftRobotSchunkWsgPrepositioningMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.releasing.ReleasingActionResult;
import org.roboticsapi.schunk.wsg.runtime.mapper.releasing.SoftRobotReleasingMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.releasing.SoftRobotSchunkWsgReleasingMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.setacceleration.SetAccelerationActionResult;
import org.roboticsapi.schunk.wsg.runtime.mapper.setacceleration.SoftRobotSchunkWsgSetAccelerationMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.setacceleration.SoftRobotSetAccelerationMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.setforcelimit.SetForceLimitActionResult;
import org.roboticsapi.schunk.wsg.runtime.mapper.setforcelimit.SoftRobotSchunkWsgSetForceLimitMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.setforcelimit.SoftRobotSetForceLimitMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.setsoftlimits.SetSoftLimitsActionResult;
import org.roboticsapi.schunk.wsg.runtime.mapper.setsoftlimits.SoftRobotSchunkWsgSetSoftLimitsMapper;
import org.roboticsapi.schunk.wsg.runtime.mapper.setsoftlimits.SoftRobotSetSoftLimitsMapper;
import org.roboticsapi.schunk.wsg.sensor.GraspingStateSensor;

public class SoftRobotWSG50Extension extends AbstractSoftRobotRoboticsBuilder {

	private final Map<AbstractSoftRobotWSG50Driver, DeviceBasedInstantiator<WSG50>> map = new HashMap<AbstractSoftRobotWSG50Driver, DeviceBasedInstantiator<WSG50>>();

	public SoftRobotWSG50Extension() {
		super(SimulatedWSG50Driver.class, CANBasedWSG50Driver.class);
	}

	@Override
	protected String[] getRuntimeExtensions() {
		return new String[] { "schunk_wsg", "schunk_wsg_sim", "schunk_wsg_can" };
	}

	@Override
	protected void onRoboticsObjectAvailable(RoboticsObject object) {
		if (object instanceof WSG50) {
			final WSG50 device = (WSG50) object;
			WSG50GripperDriver d = device.getDriver();

			if (d instanceof SimulatedWSG50Driver) {
				final SimulatedWSG50Driver driver = (SimulatedWSG50Driver) d;
				final DeviceBasedInstantiator<WSG50> loader = new DeviceBasedInstantiator<WSG50>(device, driver);

				map.put(driver, loader);
				driver.addOperationStateListener(loader);
			}
			
			if (d instanceof CANBasedWSG50Driver) {
				final CANBasedWSG50Driver driver = (CANBasedWSG50Driver) d;
				final DeviceBasedInstantiator<WSG50> loader = new DeviceBasedInstantiator<WSG50>(device, driver);

				map.put(driver, loader);
				driver.addOperationStateListener(loader);
			}
		}
	}

	@Override
	protected void onRoboticsObjectUnavailable(RoboticsObject object) {
		if (object instanceof SimulatedWSG50Driver) {
			final SimulatedWSG50Driver driver = (SimulatedWSG50Driver) object;
			final DeviceBasedInstantiator<WSG50> loader = map.remove(driver);

			driver.removeOperationStateListener(loader);
		}
		
		if (object instanceof CANBasedWSG50Driver) {
			final CANBasedWSG50Driver driver = (CANBasedWSG50Driver) object;
			final DeviceBasedInstantiator<WSG50> loader = map.remove(driver);

			driver.removeOperationStateListener(loader);
		}
	}

	@Override
	protected void onRuntimeAvailable(SoftRobotRuntime runtime) {
		MapperRegistry registry = runtime.getMapperRegistry();

		registry.registerActuatorDriverMapper(SoftRobotRuntime.class, AbstractSoftRobotWSG50Driver.class,
				HomingActionResult.class, new SoftRobotSchunkWsgHomingMapper());
		registry.registerActionMapper(SoftRobotRuntime.class, Homing.class, new SoftRobotHomingMapper());

		registry.registerActuatorDriverMapper(SoftRobotRuntime.class, AbstractSoftRobotWSG50Driver.class,
				PrepositioningActionResult.class, new SoftRobotSchunkWsgPrepositioningMapper());
		registry.registerActionMapper(SoftRobotRuntime.class, Prepositioning.class,
				new SoftRobotPrepositioningMapper());

		registry.registerActuatorDriverMapper(SoftRobotRuntime.class, AbstractSoftRobotWSG50Driver.class,
				GraspingActionResult.class, new SoftRobotSchunkWsgGraspingMapper());
		registry.registerActionMapper(SoftRobotRuntime.class, Grasping.class, new SoftRobotGraspingMapper());

		registry.registerActuatorDriverMapper(SoftRobotRuntime.class, AbstractSoftRobotWSG50Driver.class,
				ReleasingActionResult.class, new SoftRobotSchunkWsgReleasingMapper());
		registry.registerActionMapper(SoftRobotRuntime.class, Releasing.class, new SoftRobotReleasingMapper());

		registry.registerActuatorDriverMapper(SoftRobotRuntime.class, AbstractSoftRobotWSG50Driver.class,
				SetAccelerationActionResult.class, new SoftRobotSchunkWsgSetAccelerationMapper());
		registry.registerActionMapper(SoftRobotRuntime.class, SetAcceleration.class,
				new SoftRobotSetAccelerationMapper());

		registry.registerActuatorDriverMapper(SoftRobotRuntime.class, AbstractSoftRobotWSG50Driver.class,
				SetForceLimitActionResult.class, new SoftRobotSchunkWsgSetForceLimitMapper());
		registry.registerActionMapper(SoftRobotRuntime.class, SetForceLimit.class, new SoftRobotSetForceLimitMapper());

		registry.registerActuatorDriverMapper(SoftRobotRuntime.class, AbstractSoftRobotWSG50Driver.class,
				SetSoftLimitsActionResult.class, new SoftRobotSchunkWsgSetSoftLimitsMapper());
		registry.registerActionMapper(SoftRobotRuntime.class, SetSoftLimits.class, new SoftRobotSetSoftLimitsMapper());

		registry.registerActuatorDriverMapper(SoftRobotRuntime.class, AbstractSoftRobotWSG50Driver.class,
				ClearSoftLimitsActionResult.class, new SoftRobotSchunkWsgClearSoftLimitsMapper());
		registry.registerActionMapper(SoftRobotRuntime.class, ClearSoftLimits.class,
				new SoftRobotClearSoftLimitsMapper());

		// Sensors
		registry.registerSensorMapper(SoftRobotRuntime.class, AbstractSoftRobotWSG50Driver.OpeningWidthSensor.class,
				new SoftRobotOpeningWidthSensorMapper());
		registry.registerSensorMapper(SoftRobotRuntime.class, AbstractSoftRobotWSG50Driver.VelocitySensor.class,
				new SoftRobotVelocitySensorMapper());
		registry.registerSensorMapper(SoftRobotRuntime.class, AbstractSoftRobotWSG50Driver.ForceSensor.class,
				new SoftRobotForceSensorMapper());
		registry.registerSensorMapper(SoftRobotRuntime.class, AbstractSoftRobotWSG50Driver.AccelerationSensor.class,
				new SoftRobotAccelerationSensorMapper());
		registry.registerSensorMapper(SoftRobotRuntime.class,
				AbstractSoftRobotWSG50Driver.GraspingStateDoubleSensor.class,
				new SoftRobotGraspingStateDoubleSensorMapper());
		registry.registerSensorMapper(SoftRobotRuntime.class, GraspingStateSensor.class,
				new SoftRobotGraspingStateSensorMapper());
	}

	@Override
	protected void onRuntimeUnavailable(SoftRobotRuntime runtime) {
		// TODO Auto-generated method stub

	}

}
