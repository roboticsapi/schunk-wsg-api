/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.runtime.mapper;

import org.roboticsapi.core.DeviceParameterBag;
import org.roboticsapi.core.actuator.ActuatorDriverRealtimeException;
import org.roboticsapi.core.actuator.ActuatorNotOperationalException;
import org.roboticsapi.core.actuator.ConcurrentAccessException;
import org.roboticsapi.core.realtimevalue.realtimeboolean.RealtimeBoolean;
import org.roboticsapi.core.realtimevalue.realtimedouble.RealtimeDouble;
import org.roboticsapi.device.schunk.wsg.runtime.WsgGenericDriver;
import org.roboticsapi.device.schunk.wsg.runtime.exception.PartLostException;
import org.roboticsapi.device.schunk.wsg.runtime.exception.Wsg50Exception;
import org.roboticsapi.device.schunk.wsg.runtime.exception.Wsg50Exception.WSG50Error;
import org.roboticsapi.facet.runtime.rpi.RpiException;
import org.roboticsapi.facet.runtime.rpi.core.primitives.BooleanAnd;
import org.roboticsapi.facet.runtime.rpi.core.primitives.IntEquals;
import org.roboticsapi.facet.runtime.rpi.mapping.ActionResult;
import org.roboticsapi.facet.runtime.rpi.mapping.ActuatorDriverMapper;
import org.roboticsapi.facet.runtime.rpi.mapping.ActuatorFragment;
import org.roboticsapi.facet.runtime.rpi.mapping.MapperRegistry;
import org.roboticsapi.facet.runtime.rpi.mapping.MappingException;
import org.roboticsapi.facet.runtime.rpi.mapping.RealtimeValueConsumerFragment;

public abstract class WsgDriverMapper<T extends ActionResult>
		implements ActuatorDriverMapper<WsgGenericDriver, T> {

	public abstract PrimitiveWrapper getMappedPrimitive(T actionResult, String deviceName);

	@Override
	public RealtimeValueConsumerFragment map(WsgGenericDriver actuatorDriver, T actionResult,
			DeviceParameterBag parameters, MapperRegistry registry, RealtimeBoolean cancel, RealtimeDouble override,
			RealtimeDouble time) throws MappingException, RpiException {

		PrimitiveWrapper gripperWrapper = getMappedPrimitive(actionResult, actuatorDriver.getRpiDeviceName());

		IntEquals success = new IntEquals(0, WSG50Error.E_SUCCESS.ordinal(), 0);
		BooleanAnd completed = new BooleanAnd();
		ActuatorFragment netFragment = new ActuatorFragment(actuatorDriver, completed.getOutValue(),
				gripperWrapper.getPrimitive(), completed, success);
		netFragment.connect(gripperWrapper.getStatusCode(), success.getInFirst());
		netFragment.connect(success.getOutValue(), completed.getInFirst());
		netFragment.connect(gripperWrapper.getFinished(), completed.getInSecond());

		IntEquals concurrentAccess = netFragment.add(new IntEquals(0, WSG50Error.CONCURRENT_ACCESS.ordinal(), 0));
		netFragment.connect(gripperWrapper.getStatusCode(), concurrentAccess.getInFirst());

		IntEquals notOperational = netFragment.add(new IntEquals(0, WSG50Error.NOT_OPERATIONAL.ordinal(), 0));
		netFragment.connect(gripperWrapper.getStatusCode(), notOperational.getInFirst());

		netFragment.addException(ConcurrentAccessException.class, concurrentAccess.getOutValue(),
				"outConcurrentAccess");
		netFragment.addException(ActuatorNotOperationalException.class, notOperational.getOutValue(),
				"outNotOperational");
		netFragment.addException(PartLostException.class, RealtimeBoolean.FALSE);

		for (ActuatorDriverRealtimeException ex : actuatorDriver.defineActuatorDriverExceptions()) {
			if (ex instanceof Wsg50Exception) {

				IntEquals anotherError = netFragment
						.add(new IntEquals(0, ((Wsg50Exception) ex).getErrorNumber(), 0));
				netFragment.connect(gripperWrapper.getStatusCode(), anotherError.getInFirst());

				netFragment.addException(ex.getClass(), anotherError.getOutValue(),
						"outError" + ((Wsg50Exception) ex).getErrorNumber());

			}
		}

		return netFragment;
	}

}
