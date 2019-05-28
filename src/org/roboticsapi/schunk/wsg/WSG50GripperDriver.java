/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg;

import org.roboticsapi.core.sensor.DoubleSensor;
import org.roboticsapi.schunk.wsg.sensor.GraspingStateSensor;
import org.roboticsapi.tool.gripper.GripperDriver;

/**
 * Gripper driver for Schunk WSG.
 */
public interface WSG50GripperDriver extends GripperDriver {

	/**
	 * Returns a sensor for the current gripping state.
	 * 
	 * @return a sensor for the current gripping state.
	 */
	GraspingStateSensor getGraspingStateSensor();

	/**
	 * Returns a sensor for the gripper's base jaw opening width.
	 * 
	 * @return a sensor for the gripper's base jaw opening width.
	 */
	DoubleSensor getBaseJawOpeningWidthSensor();

	/**
	 * Returns a sensor for the gripper's current velocity.
	 * 
	 * @return a sensor for the gripper's current velocity.
	 */
	DoubleSensor getVelocitySensor();

	/**
	 * Returns a sensor for the gripper's current gripping force.
	 * 
	 * @return a sensor for the gripper's current gripping force.
	 */
	DoubleSensor getForceSensor();

	/**
	 * Returns a sensor for the gripper's current acceleration.
	 * 
	 * @return a sensor for the gripper's current acceleration.
	 */
	DoubleSensor getAccelerationSensor();

}
