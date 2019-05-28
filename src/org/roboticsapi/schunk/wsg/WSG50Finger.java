/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg;

import org.roboticsapi.tool.gripper.AbstractGrippingFinger;
import org.roboticsapi.world.Frame;

public class WSG50Finger extends AbstractGrippingFinger {

	/**
	 * Gets the finger's contact {@link Frame} where a (possible) contact to the
	 * gripped workpiece is established.
	 *
	 * @return the contact frame.
	 */
	public Frame getContactFrame() {
		return getBase();
	}

	@Override
	public double getOffset() {
		return -0.012d;
	}

	@Override
	public double getLength() {
		return 0.0657;
	}

}
