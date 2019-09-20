/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.runtime.activity;

import org.roboticsapi.core.activity.runtime.SensorInterfaceImpl;
import org.roboticsapi.device.schunk.wsg.RealtimeGraspingState;
import org.roboticsapi.device.schunk.wsg.activity.GraspingStateSensorInterface;
import org.roboticsapi.device.schunk.wsg.runtime.WsgGenericDriver;

public class GraspingStateSensorInterfaceImpl extends SensorInterfaceImpl implements GraspingStateSensorInterface {

	private WsgGenericDriver driver;

	public GraspingStateSensorInterfaceImpl(WsgGenericDriver driver) {
		super(driver.getDevice(), driver.getRuntime());
		this.driver = driver;
	}

	@Override
	public RealtimeGraspingState getGraspingState() {
		return driver.getGraspingState();
	}

}
