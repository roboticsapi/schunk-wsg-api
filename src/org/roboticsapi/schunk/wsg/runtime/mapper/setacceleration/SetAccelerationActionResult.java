/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.runtime.mapper.setacceleration;

import org.roboticsapi.runtime.mapping.result.ActionResult;

public class SetAccelerationActionResult extends ActionResult {

	private final double acceleration;

	public SetAccelerationActionResult(double acceleration) {
		super(null);
		this.acceleration = acceleration;
	}

	public double getAcceleration() {
		return acceleration;
	}

}
