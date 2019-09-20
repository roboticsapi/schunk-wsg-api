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
import org.roboticsapi.framework.gripper.activity.GripperForceSensorInterface;

public class GripperForceSensorInterfaceImpl extends SensorInterfaceImpl implements GripperForceSensorInterface {

	private WsgGenericDriver driver;

	public GripperForceSensorInterfaceImpl(WsgGenericDriver driver) {
		super(driver.getDevice(), driver.getRuntime());
		this.driver = driver;
	}

	@Override
	public double getMinimumForce() {
		return driver.getDevice().getMinimumForce();
	}

	@Override
	public double getMaximumForce() {
		return driver.getDevice().getMaximumForce();
	}

	@Override
	public RealtimeDouble getForce() {
		return driver.getForce();
	}

}
