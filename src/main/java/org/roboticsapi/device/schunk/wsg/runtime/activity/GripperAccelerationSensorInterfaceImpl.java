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
import org.roboticsapi.framework.gripper.activity.GripperAccelerationSensorInterface;

public class GripperAccelerationSensorInterfaceImpl extends SensorInterfaceImpl
		implements GripperAccelerationSensorInterface {

	private WsgGenericDriver driver;

	public GripperAccelerationSensorInterfaceImpl(WsgGenericDriver driver) {
		super(driver.getDevice(), driver.getRuntime());
		this.driver = driver;
	}

	@Override
	public double getMinimumAcceleration() {
		return driver.getDevice().getMinimumAcceleration();
	}

	@Override
	public double getMaximumAcceleration() {
		return driver.getDevice().getMaximumAcceleration();
	}

	@Override
	public RealtimeDouble getAcceleration() {
		return driver.getAcceleration();
	}

}
