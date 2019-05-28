/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.extension;

import org.roboticsapi.core.DeviceInterface;
import org.roboticsapi.core.RoboticsObject;
import org.roboticsapi.core.SingleDeviceInterfaceFactory;
import org.roboticsapi.extension.AbstractRoboticsObjectBuilder;
import org.roboticsapi.extension.RoboticsObjectListener;
import org.roboticsapi.schunk.wsg.WSG50;
import org.roboticsapi.schunk.wsg.WSG50Finger;
import org.roboticsapi.schunk.wsg.internal.WsgGraspingInterfaceImpl;

public class WSG50Extension extends AbstractRoboticsObjectBuilder implements RoboticsObjectListener {

	public WSG50Extension() {
		super(WSG50.class, WSG50Finger.class);
	}

	@Override
	public void onAvailable(final RoboticsObject object) {
		if (object instanceof WSG50) {
			((WSG50) object).addInterfaceFactory(new SingleDeviceInterfaceFactory<DeviceInterface>() {

				@Override
				protected DeviceInterface build() {
					return new WsgGraspingInterfaceImpl((WSG50) object);
				}
			});
		}

	}

	@Override
	public void onUnavailable(RoboticsObject object) {
		// TODO Auto-generated method stub

	}

}
