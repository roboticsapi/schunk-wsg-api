/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.runtime.mapper.releasing;

import org.roboticsapi.runtime.mapping.result.ActionResult;

public class ReleasingActionResult extends ActionResult {

	private final double baseJawWidth;
	private final double speed;

	/**
	 * 
	 * @param baseJawWidth in [m]
	 * @param speed        in [m/s]
	 */
	public ReleasingActionResult(double baseJawWidth, double speed) {
		super(null);
		this.baseJawWidth = baseJawWidth;
		this.speed = speed;
	}

	public double getBaseJawWidth() {
		return baseJawWidth;
	}

	public double getSpeed() {
		return speed;
	}

}
