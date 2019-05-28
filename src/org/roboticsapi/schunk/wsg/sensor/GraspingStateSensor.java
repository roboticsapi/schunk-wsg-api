/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.sensor;

import org.roboticsapi.core.Sensor;
import org.roboticsapi.core.State;
import org.roboticsapi.core.sensor.DoubleSensor;
import org.roboticsapi.schunk.wsg.sensor.GraspingStateSensor.GraspingState;

public final class GraspingStateSensor extends Sensor<GraspingState> {

	public static enum GraspingState {
		/** The grasping process is in idle state and is waiting for a command. */
		IDLE,
		/**
		 * The fingers are currently closing to grasp a part. The part has not been
		 * grasped, yet
		 */
		GRASPING,
		/**
		 * The fingers have been closed, but no part was found at the specified nominal
		 * width within the given clamping range. This state will persist until the next
		 * Grasp Part, Release Part or Preposition Fingers command is issued.
		 */
		NO_PART_FOUND,
		/**
		 * A part was grasped but then lost before the fingers have been opened again.
		 * This state will persist until the next Grasp Part, Release Part or
		 * Pre-position Fingers command is issued.
		 */
		PART_LOST,
		/**
		 * A part was grasped successfully and is now being hold with the grasping
		 * force.
		 */
		HOLDING,
		/**
		 * The fingers are currently opening towards the opening width to release a
		 * part.
		 */
		RELEASING,
		/**
		 * The fingers are currently pre-positioned using a Pre-position Fingers
		 * command.
		 */
		POSITIONING, ERROR;

		public boolean isEqualGraspingState(GraspingState graspingState) {
			return this.equals(graspingState);
		}
	}

	private final DoubleSensor doubleSensor;

	public GraspingStateSensor(DoubleSensor doubleSensor) {
		super(selectRuntime(doubleSensor));
		addInnerSensors(doubleSensor);
		this.doubleSensor = doubleSensor;
	}

	public DoubleSensor getDoubleSensor() {
		return doubleSensor;
	}

	@Override
	protected GraspingState getDefaultValue() {
		return GraspingState.IDLE;
	}

	@Override
	protected GraspingState calculateCheapValue() {
		Double cheapValue = getDoubleSensor().getCheapValue();
		if (cheapValue == null) {
			return null;
		}
		return GraspingState.values()[cheapValue.intValue()];
	}

	public State hasGraspingState(GraspingState state) {
		return getDoubleSensor().isEqual(state.ordinal(), 0.1);
	}

	@Override
	public boolean equals(Object obj) {
		return classEqual(obj) && doubleSensor.equals(((GraspingStateSensor) obj).doubleSensor);
	}

	@Override
	public int hashCode() {
		return classHash(doubleSensor);
	}

	@Override
	public boolean isAvailable() {
		return doubleSensor.isAvailable();
	}
}
