/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.runtime;

public final class WsgMockDriver extends WsgGenericDriver {

	private final static String DEVICE_TYPE = "schunk_wsg_sim";

	@Override
	public final String getRpiDeviceType() {
		return DEVICE_TYPE;
	}

	@Override
	protected boolean checkDeviceType(String deviceType) {
		return DEVICE_TYPE.equals(deviceType);
	}

}
