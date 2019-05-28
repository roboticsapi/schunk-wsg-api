/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.runtime;

import java.util.Map;

public class SimulatedWSG50Driver extends AbstractSoftRobotWSG50Driver {

	private final static String DEVICE_TYPE = "schunk_wsg_sim";

	@Override
	public String getDeviceType() {
		return DEVICE_TYPE;
	}

	@Override
	protected void collectDriverSpecificParameters(Map<String, String> parameters) {
		// nothing to do
	}

}
