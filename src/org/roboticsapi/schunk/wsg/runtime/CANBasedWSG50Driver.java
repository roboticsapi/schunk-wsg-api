/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.runtime;

import java.util.Map;

import org.roboticsapi.configuration.ConfigurationProperty;
import org.roboticsapi.core.exception.ConfigurationException;
import org.roboticsapi.runtime.fieldbus.can.CanFieldbusDriver;

public class CANBasedWSG50Driver extends AbstractSoftRobotWSG50Driver {

	private final static String DEVICE_TYPE = "schunk_wsg_can";
	private CanFieldbusDriver canDriver;

	public CANBasedWSG50Driver() {
	}

	@Override
	public String getDeviceType() {
		return DEVICE_TYPE;
	}

	@Override
	protected void collectDriverSpecificParameters(Map<String, String> parameters) {
		parameters.put("candevice", canDriver.getDeviceName());
	}

	@ConfigurationProperty(Optional = false)
	public void setCanDriver(CanFieldbusDriver canDriver) {
		immutableWhenInitialized();
		this.canDriver = canDriver;
	}
	
	public CanFieldbusDriver getCanDriver() {
		return canDriver;
	}

	@Override
	protected void validateConfigurationProperties() throws ConfigurationException {
		super.validateConfigurationProperties();

		checkNotNullAndInitialized("canDriver", canDriver);
	}
	
}
