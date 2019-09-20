/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.runtime.mapper.homing;

import org.roboticsapi.device.schunk.wsg.runtime.action.Homing.HomingDirection;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.PrimitiveWrapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.WsgDriverMapper;
import org.roboticsapi.device.schunk.wsg.runtime.primitives.Homing;

public class WsgHomingMapper extends WsgDriverMapper<HomingActionResult> {

	@Override
	public PrimitiveWrapper getMappedPrimitive(HomingActionResult actionResult, String deviceName) {
		Homing ar = new Homing((actionResult.getDirection() == HomingDirection.OPEN), deviceName);
		return new PrimitiveWrapper(ar, ar.getOutFinished(), ar.getOutStatusCode());
	}

}
