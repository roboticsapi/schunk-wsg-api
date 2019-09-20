/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.runtime.mapper.monitor;

import org.roboticsapi.device.schunk.wsg.runtime.WsgGenericDriver;
import org.roboticsapi.device.schunk.wsg.runtime.WsgGenericDriver.RealtimeVelocity;
import org.roboticsapi.device.schunk.wsg.runtime.primitives.Monitor;
import org.roboticsapi.facet.runtime.rpi.RpiException;
import org.roboticsapi.facet.runtime.rpi.mapping.MappingException;
import org.roboticsapi.facet.runtime.rpi.mapping.RealtimeValueFragment;
import org.roboticsapi.facet.runtime.rpi.mapping.TypedRealtimeValueFragmentFactory;
import org.roboticsapi.facet.runtime.rpi.mapping.core.RealtimeDoubleFragment;

public class WsgRealtimeVelocityMapper
		extends TypedRealtimeValueFragmentFactory<Double, WsgGenericDriver.RealtimeVelocity> {

	public WsgRealtimeVelocityMapper() {
		super(RealtimeVelocity.class);
	}

	@Override
	protected RealtimeValueFragment<Double> createFragment(RealtimeVelocity value)
			throws MappingException, RpiException {
		return new RealtimeDoubleFragment(value, new Monitor(value.getDriver().getRpiDeviceName()).getOutSpeed());
	}

}
