/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.activity;

import org.roboticsapi.activity.ActivityProperty;

public final class ForceProperty implements ActivityProperty {
	private final double force;

	public ForceProperty(double force) {
		this.force = force;
	}

	public double getForce() {
		return force;
	}
}