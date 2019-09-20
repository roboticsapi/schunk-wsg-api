/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.runtime.extension;

import org.roboticsapi.device.schunk.wsg.runtime.WsgCanbusDriver;
import org.roboticsapi.device.schunk.wsg.runtime.WsgGenericDriver;
import org.roboticsapi.device.schunk.wsg.runtime.WsgMockDriver;
import org.roboticsapi.device.schunk.wsg.runtime.WsgSimulationDriver;
import org.roboticsapi.device.schunk.wsg.runtime.action.ClearSoftLimits;
import org.roboticsapi.device.schunk.wsg.runtime.action.Grasping;
import org.roboticsapi.device.schunk.wsg.runtime.action.Homing;
import org.roboticsapi.device.schunk.wsg.runtime.action.Prepositioning;
import org.roboticsapi.device.schunk.wsg.runtime.action.Releasing;
import org.roboticsapi.device.schunk.wsg.runtime.action.SetAcceleration;
import org.roboticsapi.device.schunk.wsg.runtime.action.SetForceLimit;
import org.roboticsapi.device.schunk.wsg.runtime.action.SetSoftLimits;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.clearsoftlimits.ClearSoftLimitsActionResult;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.clearsoftlimits.ClearSoftLimitsMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.clearsoftlimits.WsgClearSoftLimitsMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.grasping.GraspingActionResult;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.grasping.GraspingMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.grasping.WsgGraspingMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.homing.HomingActionResult;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.homing.HomingMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.homing.WsgHomingMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.monitor.WsgAccelerationMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.monitor.WsgForceMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.monitor.WsgGraspingStateIntegerMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.monitor.WsgOpeningWidthMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.monitor.WsgRealtimeVelocityMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.prepositioning.PrepositioningActionResult;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.prepositioning.PrepositioningMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.prepositioning.WsgPrepositioningMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.releasing.ReleasingActionResult;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.releasing.ReleasingMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.releasing.WsgReleasingMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.setacceleration.WsgSetAccelerationMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.setacceleration.SetAccelerationActionResult;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.setacceleration.SetAccelerationMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.setforcelimit.WsgSetForceLimitMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.setforcelimit.SetForceLimitActionResult;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.setforcelimit.SetForceLimitMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.setsoftlimits.WsgSetSoftLimitsMapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.setsoftlimits.SetSoftLimitsActionResult;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.setsoftlimits.SetSoftLimitsMapper;
import org.roboticsapi.facet.runtime.rpi.extension.RpiExtension;
import org.roboticsapi.facet.runtime.rpi.mapping.MapperRegistry;

public class WsgRuntimeExtension extends RpiExtension {

	public WsgRuntimeExtension() {
		super(WsgGenericDriver.class, WsgMockDriver.class, WsgCanbusDriver.class,
				WsgSimulationDriver.class);
	}

	@Override
	protected void registerMappers(MapperRegistry registry) {

		registry.registerRealtimeValueMapper(new WsgAccelerationMapper());
		registry.registerRealtimeValueMapper(new WsgForceMapper());
		registry.registerRealtimeValueMapper(new WsgGraspingStateIntegerMapper());
		registry.registerRealtimeValueMapper(new WsgOpeningWidthMapper());
		registry.registerRealtimeValueMapper(new WsgRealtimeVelocityMapper());

		registry.registerActionMapper(ClearSoftLimits.class, new ClearSoftLimitsMapper());
		registry.registerActionMapper(Grasping.class, new GraspingMapper());
		registry.registerActionMapper(Homing.class, new HomingMapper());
		registry.registerActionMapper(Prepositioning.class, new PrepositioningMapper());
		registry.registerActionMapper(Releasing.class, new ReleasingMapper());
		registry.registerActionMapper(SetAcceleration.class, new SetAccelerationMapper());
		registry.registerActionMapper(SetForceLimit.class, new SetForceLimitMapper());
		registry.registerActionMapper(SetSoftLimits.class, new SetSoftLimitsMapper());

		registry.registerActuatorDriverMapper(WsgGenericDriver.class, ClearSoftLimitsActionResult.class,
				new WsgClearSoftLimitsMapper());
		registry.registerActuatorDriverMapper(WsgGenericDriver.class, GraspingActionResult.class,
				new WsgGraspingMapper());
		registry.registerActuatorDriverMapper(WsgGenericDriver.class, HomingActionResult.class,
				new WsgHomingMapper());
		registry.registerActuatorDriverMapper(WsgGenericDriver.class, PrepositioningActionResult.class,
				new WsgPrepositioningMapper());
		registry.registerActuatorDriverMapper(WsgGenericDriver.class, ReleasingActionResult.class,
				new WsgReleasingMapper());
		registry.registerActuatorDriverMapper(WsgGenericDriver.class, SetAccelerationActionResult.class,
				new WsgSetAccelerationMapper());
		registry.registerActuatorDriverMapper(WsgGenericDriver.class, SetForceLimitActionResult.class,
				new WsgSetForceLimitMapper());
		registry.registerActuatorDriverMapper(WsgGenericDriver.class, SetSoftLimitsActionResult.class,
				new WsgSetSoftLimitsMapper());

	}

	@Override
	protected void unregisterMappers(MapperRegistry mr) {
		// TODO Auto-generated method stub

	}

}
