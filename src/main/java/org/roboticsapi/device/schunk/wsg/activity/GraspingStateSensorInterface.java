/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.activity;

import org.roboticsapi.core.SensorInterface;
import org.roboticsapi.core.realtimevalue.RealtimeValueReadException;
import org.roboticsapi.device.schunk.wsg.GraspingState;
import org.roboticsapi.device.schunk.wsg.RealtimeGraspingState;

public interface GraspingStateSensorInterface extends SensorInterface {

	RealtimeGraspingState getGraspingState();

	default GraspingState getCurrentGraspingState() throws RealtimeValueReadException {
		return getGraspingState().getCurrentValue();
	}

}
