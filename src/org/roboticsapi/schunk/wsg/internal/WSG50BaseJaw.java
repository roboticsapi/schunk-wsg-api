/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.internal;

import org.roboticsapi.core.exception.RoboticsException;
import org.roboticsapi.schunk.wsg.WSG50Finger;
import org.roboticsapi.tool.gripper.AbstractBaseJaw;
import org.roboticsapi.tool.gripper.GrippingFinger;
import org.roboticsapi.world.Frame;
import org.roboticsapi.world.Transformation;

public class WSG50BaseJaw extends AbstractBaseJaw {

	public WSG50BaseJaw(Frame mountFrame) {
		super(mountFrame);
	}

	@Override
	protected void validateFinger(GrippingFinger finger) throws RoboticsException {
		if (!(finger instanceof WSG50Finger)) {
			throw new RoboticsException("Illegal gripping finger");
		}
	}

	@Override
	protected Transformation getBaseMountTransformation() {
		return new Transformation(0.009d, 0d, 0.0165d, 0, 0, 0);
	}

}
