/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.runtime;

import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.actuator.ActuatorDriverRealtimeException;
import org.roboticsapi.core.actuator.ActuatorNotOperationalException;
import org.roboticsapi.core.actuator.ConcurrentAccessException;
import org.roboticsapi.runtime.SoftRobotRuntime;
import org.roboticsapi.runtime.mapping.MappingException;
import org.roboticsapi.runtime.mapping.net.DataflowOutPort;
import org.roboticsapi.runtime.mapping.net.IntDataflow;
import org.roboticsapi.runtime.mapping.net.NetFragment;
import org.roboticsapi.runtime.mapping.net.StateDataflow;
import org.roboticsapi.runtime.mapping.parts.ActuatorDriverMapper;
import org.roboticsapi.runtime.mapping.parts.DeviceMappingPorts;
import org.roboticsapi.runtime.mapping.parts.ErrorNumberSwitchFragment;
import org.roboticsapi.runtime.mapping.result.ActionResult;
import org.roboticsapi.runtime.mapping.result.ActuatorDriverMapperResult;
import org.roboticsapi.runtime.mapping.result.impl.BaseActuatorDriverMapperResult;
import org.roboticsapi.runtime.rpi.OutPort;
import org.roboticsapi.runtime.rpi.RPIException;
import org.roboticsapi.schunk.wsg.WSG50Exception;
import org.roboticsapi.schunk.wsg.WSG50Exception.WSG50Error;

public abstract class AbstractSoftRobotWSG50Mapper<T extends ActionResult>
		implements ActuatorDriverMapper<SoftRobotRuntime, AbstractSoftRobotWSG50Driver, T> {

	public abstract PrimitiveWrapper getMappedPrimitive(T actionResult, String deviceName);

	@Override
	public ActuatorDriverMapperResult map(SoftRobotRuntime runtime, AbstractSoftRobotWSG50Driver driver, T actionResult,
			DeviceParameterBag parameters, DeviceMappingPorts ports) throws MappingException, RPIException {

		NetFragment netFragment = new NetFragment("SchunkWsg");
		PrimitiveWrapper gripperWrapper = getMappedPrimitive(actionResult, driver.getDeviceName());
		netFragment.add(gripperWrapper.getPrimitive());

		OutPort outFinished = gripperWrapper.getFinished();
		final DataflowOutPort finished = netFragment.addOutPort(new StateDataflow(), true, outFinished);

		OutPort outErrorPort = gripperWrapper.getStatusCode();
		DataflowOutPort dataflowOutPort = netFragment.addOutPort(new DataflowOutPort(new IntDataflow(), outErrorPort));

		ErrorNumberSwitchFragment netErrorFragment = netFragment.add(new ErrorNumberSwitchFragment(dataflowOutPort));

		BaseActuatorDriverMapperResult result = new BaseActuatorDriverMapperResult(driver, netFragment, finished);

		for (ActuatorDriverRealtimeException ex : driver.defineActuatorDriverExceptions()) {
			if (ex instanceof WSG50Exception) {
				result.addExceptionPort(ex.getClass(),
						netErrorFragment.getCasePort(((WSG50Exception) ex).getErrorNumber()));
			}
		}
		result.addExceptionPort(ActuatorNotOperationalException.class,
				netErrorFragment.getCasePort(WSG50Error.NOT_OPERATIONAL.ordinal()));
		result.addExceptionPort(ConcurrentAccessException.class,
				netErrorFragment.getCasePort(WSG50Error.CONCURRENT_ACCESS.ordinal()));

		return result;
	}
}
