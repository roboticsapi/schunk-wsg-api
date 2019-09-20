/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.javarcc.primitives;

import java.util.Set;

import org.roboticsapi.device.schunk.wsg.javarcc.devices.JMockWsg;
import org.roboticsapi.facet.javarcc.JInPort;
import org.roboticsapi.facet.javarcc.JOutPort;
import org.roboticsapi.facet.javarcc.JParameter;
import org.roboticsapi.facet.javarcc.JPrimitive;
import org.roboticsapi.facet.javarcc.devices.JDevice;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIbool;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIstring;

/**
 * Module to stop any motion of a gripper device.
 */
public class JStopDevice extends JPrimitive {

	/** Activation port */
	final JInPort<RPIbool> inActive = add("inActive", new JInPort<RPIbool>());

	/** Reset port */
	final JInPort<RPIbool> inReset = add("inReset", new JInPort<RPIbool>());

	/** Finished */
	final JOutPort<RPIbool> outFinished = add("outFinished", new JOutPort<RPIbool>());

	/** Name of the device */
	final JParameter<RPIstring> propDevice = add("Device", new JParameter<RPIstring>(new RPIstring("")));

	private JMockWsg device = null;
	private boolean init = true;

	@Override
	public void checkParameters() throws IllegalArgumentException {
		device = device(propDevice, JMockWsg.class);
	}

	@Override
	public void updateData() {
		if (inReset.isConnected() && inReset.get().get())
			init = true;

		if (!inActive.isConnected() || inActive.get().get()) {
			if (init) {
				init = false;
				device.stopDevice();
				init = false;
			}
			outFinished.set(new RPIbool(true));
		}
	}

	@Override
	public Set<JDevice> getActuators() {
		return deviceSet(device);
	}

}
