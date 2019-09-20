/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg;

import org.roboticsapi.framework.gripper.AbstractGrippingFinger;

public class Wsg50Finger extends AbstractGrippingFinger {

	@Override
	public double getOffset() {
		return -0.003d;
	}

	@Override
	public double getLength() {
		return 0.0657;
	}

	// @Override
	// protected Transformation getBase2ContactFrameTransformation() {
	// return new Transformation(-0.003d, 0d, 0.0557, 0d, 0d, 0d);
	// }

}
