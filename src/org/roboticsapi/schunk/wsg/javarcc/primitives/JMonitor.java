/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.javarcc.primitives;

import java.util.Set;

import org.roboticsapi.runtime.core.javarcc.devices.JDevice;
import org.roboticsapi.runtime.core.types.RPIdouble;
import org.roboticsapi.runtime.core.types.RPIstring;
import org.roboticsapi.runtime.javarcc.JOutPort;
import org.roboticsapi.runtime.javarcc.JParameter;
import org.roboticsapi.runtime.javarcc.JPrimitive;
import org.roboticsapi.schunk.wsg.javarcc.devices.JMockSchunkWsg;

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

	private JMockSchunkWsg device = null;

	@Override
	public void checkParameters() throws IllegalArgumentException {
		device = device(propDevice, JMockSchunkWsg.class);
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
