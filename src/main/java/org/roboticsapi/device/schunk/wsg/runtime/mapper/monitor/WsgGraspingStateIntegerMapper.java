/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.runtime.mapper.monitor;

import org.roboticsapi.device.schunk.wsg.runtime.WsgGenericDriver;
import org.roboticsapi.device.schunk.wsg.runtime.WsgGenericDriver.RealtimeGraspingStateInteger;
import org.roboticsapi.device.schunk.wsg.runtime.primitives.Monitor;
import org.roboticsapi.facet.runtime.rpi.RpiException;
import org.roboticsapi.facet.runtime.rpi.mapping.MappingException;
import org.roboticsapi.facet.runtime.rpi.mapping.RealtimeValueFragment;
import org.roboticsapi.facet.runtime.rpi.mapping.TypedRealtimeValueFragmentFactory;
import org.roboticsapi.facet.runtime.rpi.mapping.core.RealtimeIntegerFragment;

public class WsgGraspingStateIntegerMapper
		extends TypedRealtimeValueFragmentFactory<Integer, WsgGenericDriver.RealtimeGraspingStateInteger> {

	public WsgGraspingStateIntegerMapper() {
		super(RealtimeGraspingStateInteger.class);
	}

	@Override
	protected RealtimeValueFragment<Integer> createFragment(RealtimeGraspingStateInteger value)
			throws MappingException, RpiException {
		return new RealtimeIntegerFragment(value,
				new Monitor(value.getDriver().getRpiDeviceName()).getOutGraspingState());
	}

}
