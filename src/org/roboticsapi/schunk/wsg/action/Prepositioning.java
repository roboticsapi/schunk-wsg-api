/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.action;

import org.roboticsapi.core.Action;

public class Prepositioning extends Action {

	private final Type type;
	private final double baseJawWidth;
	private final double speed;
	private final boolean stopOnBlock;

	public enum Type {
		RELATIVE, ABSOLUTE
	};

	/**
	 * 
	 * @param type
	 * @param baseJawWidth in [m]
	 * @param speed        in [m/s]
	 * @param stopOnBlock
	 */
	public Prepositioning(Type type, double baseJawWidth, double speed, boolean stopOnBlock) {
		super(0);
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
