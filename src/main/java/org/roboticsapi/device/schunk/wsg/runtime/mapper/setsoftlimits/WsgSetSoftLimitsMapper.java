/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.runtime.mapper.setsoftlimits;

import org.roboticsapi.device.schunk.wsg.runtime.mapper.PrimitiveWrapper;
import org.roboticsapi.device.schunk.wsg.runtime.mapper.WsgDriverMapper;
import org.roboticsapi.device.schunk.wsg.runtime.primitives.SetSoftLimits;

public class WsgSetSoftLimitsMapper extends WsgDriverMapper<SetSoftLimitsActionResult> {

	@Override
	public PrimitiveWrapper getMappedPrimitive(SetSoftLimitsActionResult actionResult, String deviceName) {
		SetSoftLimits ar = new SetSoftLimits(actionResult.getInner(), actionResult.getOuter(), deviceName);
		return new PrimitiveWrapper(ar, ar.getOutFinished(), ar.getOutStatusCode());
	}

}
