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
import org.roboticsapi.framework.gripper.activity.GripperVelocitySensorInterface;

public class GripperVelocitySensorInterfaceImpl extends SensorInterfaceImpl implements GripperVelocitySensorInterface {

	private WsgGenericDriver driver;

	public GripperVelocitySensorInterfaceImpl(WsgGenericDriver driver) {
		super(driver.getDevice(), driver.getRuntime());
		this.driver = driver;
	}

	@Override
	public double getMinimumVelocity() {
		return driver.getDevice().getMinimumVelocity();
	}

	@Override
	public double getMaximumVelocity() {
		return driver.getDevice().getMaximumVelocity();
	}

	@Override
	public RealtimeDouble getVelocity() {
		return driver.getVelocity();
	}

}
