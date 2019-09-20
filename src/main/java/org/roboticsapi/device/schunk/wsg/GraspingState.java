/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg;

public enum GraspingState {

	IDLE, // The grasping process is in idle state and is waiting for a
	// command.
	GRASPING, // The fingers are currently closing to grasp a part. The part
	// has
	// not been grasped, yet
	NO_PART_FOUND, // The fingers have been closed, but no part was found at
	// the
	// specified nominal width within the given clamping
	// range.
	// This state will persist until the next Grasp Part,
	// Release Part or Preposition Fingers command is
	// issued.
	PART_LOST, // A part was grasped but then lost before the fingers have
	// been
	// opened again. This state will persist until the next
	// Grasp
	// Part, Release Part or Pre-position Fingers command is
	// issued.
	HOLDING, // A part was grasped successfully and is now being hold with
	// the
	// grasping force.
	RELEASING, // The fingers are currently opening towards the opening
	// width to
	// release a part.
	POSITIONING, // The fingers are currently pre-positioned using a
	// Pre-position Fingers command.
	ERROR;

}