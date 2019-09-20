/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.runtime.mapper.setsoftlimits;

import org.roboticsapi.core.Action;
import org.roboticsapi.core.realtimevalue.realtimeboolean.RealtimeBoolean;
import org.roboticsapi.facet.runtime.rpi.mapping.ActionResult;

public class SetSoftLimitsActionResult extends ActionResult {

	private final double inner;
	private final double outer;

	public SetSoftLimitsActionResult(Action action, RealtimeBoolean completion, double inner, double outer) {
		super(action, completion);
		this.inner = inner;
		this.outer = outer;
	}

	public double getInner() {
		return inner;
	}

	public double getOuter() {
		return outer;
	}

}
