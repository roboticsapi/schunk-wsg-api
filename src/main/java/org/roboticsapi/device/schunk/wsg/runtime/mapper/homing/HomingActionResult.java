/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.runtime.mapper.homing;

import org.roboticsapi.core.Action;
import org.roboticsapi.core.realtimevalue.realtimeboolean.RealtimeBoolean;
import org.roboticsapi.device.schunk.wsg.runtime.action.Homing.HomingDirection;
import org.roboticsapi.facet.runtime.rpi.mapping.ActionResult;

public class HomingActionResult extends ActionResult {

	private final HomingDirection direction;

	public HomingActionResult(Action action, RealtimeBoolean completion, HomingDirection direction) {
		super(action, completion);
		this.direction = direction;
	}

	public HomingDirection getDirection() {
		return direction;
	}

}
