/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.action;

import org.roboticsapi.core.Action;

public class Homing extends Action {

	private final HomingDirection direction;

	public enum HomingDirection {
		OPEN, CLOSE
	};

	public Homing(HomingDirection direction) {
		super(0);
		this.direction = direction;
	}

	public HomingDirection getDirection() {
		return direction;
	}

}
