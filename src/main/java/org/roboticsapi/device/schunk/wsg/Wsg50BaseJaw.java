/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg;

import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.core.world.Transformation;
import org.roboticsapi.framework.gripper.AbstractBaseJaw;
import org.roboticsapi.framework.gripper.GrippingFinger;

public class Wsg50BaseJaw extends AbstractBaseJaw {

	private static final Transformation BASE_TO_MOUNT_TRANSFORMATION = new Transformation(0.009d, 0d, 0.0165d, 0, 0, 0);

	@Override
	protected void validateFinger(GrippingFinger finger) throws RoboticsException {
		if (!(finger instanceof Wsg50Finger)) {
			throw new RoboticsException("Illegal gripping finger");
		}
	}

	@Override
	public Transformation getBaseMountTransformation() {
		return BASE_TO_MOUNT_TRANSFORMATION;
	}

}
