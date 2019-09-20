/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.runtime.mapper.prepositioning;

import org.roboticsapi.core.Action;
import org.roboticsapi.core.realtimevalue.realtimeboolean.RealtimeBoolean;
import org.roboticsapi.device.schunk.wsg.runtime.action.Prepositioning.Type;
import org.roboticsapi.facet.runtime.rpi.mapping.ActionResult;

public class PrepositioningActionResult extends ActionResult {

	private final Type type;
	private final double baseJawWidth;
	private final double speed;
	private final boolean stopOnBlock;

	/**
	 * 
	 * @param type
	 * @param baseJawWidth in [m]
	 * @param speed        in [m/s]
	 * @param stopOnBlock
	 */
	public PrepositioningActionResult(Action action, RealtimeBoolean completion, Type type, double baseJawWidth,
			double speed, boolean stopOnBlock) {
		super(action, completion);
		this.type = type;
		this.baseJawWidth = baseJawWidth;
		this.speed = speed;
		this.stopOnBlock = stopOnBlock;
	}

	public Type getType() {
		return type;
	}

	public double getBaseJawWidth() {
		return baseJawWidth;
	}

	public double getSpeed() {
		return speed;
	}

	public boolean getStopOnBlock() {
		return stopOnBlock;
	}

}
