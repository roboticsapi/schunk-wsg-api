/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.runtime.activity;

import org.roboticsapi.core.activity.runtime.SensorInterfaceImpl;
import org.roboticsapi.core.realtimevalue.realtimedouble.RealtimeDouble;
import org.roboticsapi.device.schunk.wsg.runtime.WsgGenericDriver;
import org.roboticsapi.framework.gripper.activity.GripperOpeningWidthSensorInterface;

public class GripperOpeningWidthSensorInterfaceImpl extends SensorInterfaceImpl
		implements GripperOpeningWidthSensorInterface {

	private WsgGenericDriver driver;

	public GripperOpeningWidthSensorInterfaceImpl(WsgGenericDriver driver) {
		super(driver.getDevice(), driver.getRuntime());
		this.driver = driver;
	}

	@Override
	public RealtimeDouble getOpeningWidth() {
		return driver.getDevice().getOpeningWidthFrom(getBaseJawOpeningWidth());
	}

	@Override
	public RealtimeDouble getBaseJawOpeningWidth() {
		return driver.getBaseJawOpeningWidth();
	}

}
