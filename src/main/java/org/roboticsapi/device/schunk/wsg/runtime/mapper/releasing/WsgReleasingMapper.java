/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.runtime.mapper.releasing;

import org.roboticsapi.device.schunk.wsg.runtime.mapper.PrimitiveWrapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.WsgDriverMapper;
import org.roboticsapi.device.schunk.wsg.runtime.primitives.Releasing;

public class WsgReleasingMapper extends WsgDriverMapper<ReleasingActionResult> {

	@Override
	public PrimitiveWrapper getMappedPrimitive(ReleasingActionResult actionResult, String deviceName) {
		Releasing ar = new Releasing(actionResult.getBaseJawWidth(), actionResult.getSpeed(), deviceName);
		return new PrimitiveWrapper(ar, ar.getOutFinished(), ar.getOutStatusCode());
	}

}
