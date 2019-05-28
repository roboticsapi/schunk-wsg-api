/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.javarcc.primitives;

import java.util.Set;

import org.roboticsapi.runtime.core.javarcc.devices.JDevice;
import org.roboticsapi.runtime.core.types.RPIbool;
import org.roboticsapi.runtime.core.types.RPIdouble;
import org.roboticsapi.runtime.core.types.RPIint;
import org.roboticsapi.runtime.core.types.RPIstring;
import org.roboticsapi.runtime.javarcc.JInPort;
import org.roboticsapi.runtime.javarcc.JOutPort;
import org.roboticsapi.runtime.javarcc.JParameter;
import org.roboticsapi.runtime.javarcc.JPrimitive;
import org.roboticsapi.schunk.wsg.javarcc.devices.JMockSchunkWsg;

/**
 * Module to control prepositioning of a gripper device.
 */
public class JPrepositioning extends JPrimitive {

	/** Activation port */
	final JInPort<RPIbool> inActive = add("inActive", new JInPort<RPIbool>());

	/** Reset port */
	final JInPort<RPIbool> inReset = add("inReset", new JInPort<RPIbool>());

	/** Finished */
	final JOutPort<RPIbool> outFinished = add("outFinished", new JOutPort<RPIbool>());

	/** StatusCode */
	final JOutPort<RPIint> outStatusCode = add("outStatusCode", new JOutPort<RPIint>());

	/** Opening width in [m]. */
	final JParameter<RPIdouble> propWidth = add("Width", new JParameter<RPIdouble>(new RPIdouble("0")));

	/** Traveling speed in [m/s]. */
	final JParameter<RPIdouble> propSpeed = add("Speed", new JParameter<RPIdouble>(new RPIdouble("0")));

	/**
	 * If 'true', the passed width is treated as an offset to the current opening
	 * width.
	 */
	final JParameter<RPIbool> propRelativeMotion = add("RelativeMotion", new JParameter<RPIbool>(new RPIbool("true")));

	/**
	 * Important for the case that the opening movement is blocked by any obstacle.
	 * If 'false', the motor is stopped. If 'true', the motor is not turned off
	 * automatically, but clamps with the previously set force limit.
	 */
	final JParameter<RPIbool> propStopOnBlock = add("StopOnBlock", new JParameter<RPIbool>(new RPIbool("false")));

	/** Name of the device */
	final JParameter<RPIstring> propDevice = add("Device", new JParameter<RPIstring>(new RPIstring("")));

	private JMockSchunkWsg device = null;
	private boolean init = true;

	@Override
	public void checkParameters() throws IllegalArgumentException {
		device = device(propDevice, JMockSchunkWsg.class);
	}

	@Override
	public void updateData() {
		if (inReset.isConnected() && inReset.get().get())
			init = true;

		if (!inActive.isConnected() || inActive.get().get()) {
			if (init) {
				init = false;
				// if (device->getDeviceState()!=RPI::DeviceState::OPERATIONAL) {
				// outStatusCode.Set(31); // 31 is NOT_OPERATIONAL
				// outFinished.Set(true);
				// }
				// else {
				boolean result = device.preposition(propRelativeMotion.get().get(), propWidth.get().get(),
						propSpeed.get().get(), propStopOnBlock.get().get());
				if (!result) {
					outStatusCode.set(new RPIint(32)); // 32 is CONCURRENT_ACCESS
					outFinished.set(new RPIbool(true));
				} else {
					outStatusCode.set(null);
					outFinished.set(new RPIbool(false));
				}
				// }
			} else {
				if (device.isBusy()) {
					outStatusCode.set(null);
					outFinished.set(new RPIbool(false));
				} else {
					outStatusCode.set(new RPIint(device.getStatusCode().ordinal()));
					outFinished.set(new RPIbool(true));
				}
			}
		}
	}

	@Override
	public Set<JDevice> getActuators() {
		return deviceSet(device);
	}

}
