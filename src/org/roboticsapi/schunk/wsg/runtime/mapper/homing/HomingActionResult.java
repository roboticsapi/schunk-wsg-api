/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.runtime.mapper.homing;

import org.roboticsapi.runtime.mapping.result.ActionResult;
import org.roboticsapi.schunk.wsg.action.Homing.HomingDirection;

public class HomingActionResult extends ActionResult {

	private final HomingDirection direction;

	public HomingActionResult(HomingDirection direction) {
		super(null);
		this.direction = direction;
	}

	public HomingDirection getDirection() {
		return direction;
	}

}
