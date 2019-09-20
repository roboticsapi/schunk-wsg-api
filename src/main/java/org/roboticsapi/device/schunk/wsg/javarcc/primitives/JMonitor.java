/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.javarcc.primitives;

import java.util.Set;

import org.roboticsapi.device.schunk.wsg.javarcc.devices.JMockWsg;
import org.roboticsapi.facet.javarcc.JOutPort;
import org.roboticsapi.facet.javarcc.JParameter;
import org.roboticsapi.facet.javarcc.JPrimitive;
import org.roboticsapi.facet.javarcc.devices.JDevice;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIdouble;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIstring;

/**
 * Module which gives information about current values of a gripper device.
 */
public class JMonitor extends JPrimitive {

	/** Force */
	final JOutPort<RPIdouble> outForce = add("outForce", new JOutPort<RPIdouble>());

	/** Grasping state */
	final JOutPort<RPIdouble> outGraspingState = add("outGraspingState", new JOutPort<RPIdouble>());

	/** Opening width */
	final JOutPort<RPIdouble> outOpeningWidth = add("outOpeningWidth", new JOutPort<RPIdouble>());

	/** Speed */
	final JOutPort<RPIdouble> outSpeed = add("outSpeed", new JOutPort<RPIdouble>());

	/** Name of the device */
	final JParameter<RPIstring> propDevice = add("Device", new JParameter<RPIstring>(new RPIstring("")));

	private JMockWsg device = null;

	@Override
	public void checkParameters() throws IllegalArgumentException {
		device = device(propDevice, JMockWsg.class);
	}

	@Override
	public Set<JDevice> getSensors() {
		return deviceSet(device);
	}

	@Override
	public void updateData() {
		outOpeningWidth.set(new RPIdouble(device.getOpeningWidth()));
		outSpeed.set(new RPIdouble(device.getVelocity()));
		outForce.set(new RPIdouble(device.getForce()));
		outGraspingState.set(new RPIdouble(device.getGraspingState().ordinal()));
	}

}
