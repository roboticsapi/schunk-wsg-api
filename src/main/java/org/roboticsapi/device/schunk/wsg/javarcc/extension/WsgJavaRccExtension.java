/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg.javarcc.extension;

import org.roboticsapi.device.schunk.wsg.javarcc.devices.JMockWsg;
import org.roboticsapi.device.schunk.wsg.javarcc.primitives.JClearSoftLimits;
import org.roboticsapi.device.schunk.wsg.javarcc.primitives.JGrasping;
import org.roboticsapi.device.schunk.wsg.javarcc.primitives.JHoming;
import org.roboticsapi.device.schunk.wsg.javarcc.primitives.JMonitor;
import org.roboticsapi.device.schunk.wsg.javarcc.primitives.JPrepositioning;
import org.roboticsapi.device.schunk.wsg.javarcc.primitives.JReleasing;
import org.roboticsapi.device.schunk.wsg.javarcc.primitives.JSetAcceleration;
import org.roboticsapi.device.schunk.wsg.javarcc.primitives.JSetForceLimit;
import org.roboticsapi.device.schunk.wsg.javarcc.primitives.JSetSoftLimits;
import org.roboticsapi.device.schunk.wsg.javarcc.primitives.JStopDevice;
import org.roboticsapi.facet.javarcc.extension.JavaRccExtension;
import org.roboticsapi.facet.javarcc.extension.JavaRccExtensionPoint;

public class WsgJavaRccExtension extends JavaRccExtension {

	@Override
	public void extend(JavaRccExtensionPoint extensionPoint) {
		extensionPoint.registerPrimitive("schunkwsg::ClearSoftLimits", JClearSoftLimits.class);
		extensionPoint.registerPrimitive("schunkwsg::Grasping", JGrasping.class);
		extensionPoint.registerPrimitive("schunkwsg::Homing", JHoming.class);
		extensionPoint.registerPrimitive("schunkwsg::Monitor", JMonitor.class);
		extensionPoint.registerPrimitive("schunkwsg::Prepositioning", JPrepositioning.class);
		extensionPoint.registerPrimitive("schunkwsg::Releasing", JReleasing.class);
		extensionPoint.registerPrimitive("schunkwsg::SetAcceleration", JSetAcceleration.class);
		extensionPoint.registerPrimitive("schunkwsg::SetForceLimit", JSetForceLimit.class);
		extensionPoint.registerPrimitive("schunkwsg::SetSoftLimits", JSetSoftLimits.class);
		extensionPoint.registerPrimitive("schunkwsg::StopDevice", JStopDevice.class);

		extensionPoint.registerDevice("schunk_wsg_sim", (p, d) -> new JMockWsg());
	}

}
