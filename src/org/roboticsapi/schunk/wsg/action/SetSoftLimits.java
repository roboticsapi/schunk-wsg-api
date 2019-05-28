/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.action;

import org.roboticsapi.core.Action;

public class SetSoftLimits extends Action {

	private final float inner;
	private final float outer;

	public SetSoftLimits(float inner, float outer) {
		super(0);
		this.inner = inner;
		this.outer = outer;
	}

	public float getInner() {
		return inner;
	}

	public float getOuter() {
		return outer;
	}

}
