/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.extension;

import org.roboticsapi.device.schunk.wsg.Wsg50;
import org.roboticsapi.device.schunk.wsg.Wsg50Finger;
import org.roboticsapi.extension.AbstractRoboticsObjectBuilder;

public class WsgExtension extends AbstractRoboticsObjectBuilder {

	public WsgExtension() {
		super(Wsg50.class, Wsg50Finger.class);
	}

}
