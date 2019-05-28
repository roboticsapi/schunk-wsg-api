/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.collada.extension;

import java.net.URL;

import org.roboticsapi.core.RoboticsObject;
import org.roboticsapi.extension.PluginableExtension;
import org.roboticsapi.extension.RoboticsObjectListener;
import org.roboticsapi.schunk.wsg.WSG50;
import org.roboticsapi.schunk.wsg.WSG50Finger;
import org.roboticsapi.tool.gripper.Finger;
import org.roboticsapi.visualization.property.Visualisation;
import org.roboticsapi.visualization.property.VisualizationProperty;

public class WsgColladaExtension implements RoboticsObjectListener, PluginableExtension {

	private static final Visualisation MODEL_BASE = new Visualisation("COLLADA", getResource("Schunk_WSG50_Base.dae"));
	private static final Visualisation MODEL_JAW = new Visualisation("COLLADA",
			getResource("Schunk_WSG50_BaseJaw.dae"));
	private static final Visualisation MODEL_FINGER = new Visualisation("COLLADA",
			getResource("Schunk_WSG50_Finger.dae"));

	@Override
	public void onAvailable(RoboticsObject object) {
		if (object instanceof WSG50) {
			WSG50 wsg = (WSG50) object;
			Finger jaw0 = wsg.getBaseJaw(0);
			Finger jaw1 = wsg.getBaseJaw(1);

			wsg.addProperty(new VisualizationProperty(MODEL_BASE));

			if (jaw0 != null) {
				jaw0.addProperty(new VisualizationProperty(MODEL_JAW));
			}

			if (jaw1 != null) {
				jaw1.addProperty(new VisualizationProperty(MODEL_JAW));
			}
		}

		if (object instanceof WSG50Finger) {
			WSG50Finger finger = (WSG50Finger) object;

			finger.addProperty(new VisualizationProperty(MODEL_FINGER));
		}
	}

	@Override
	public void onUnavailable(RoboticsObject object) {
		// do nothing...
	}

	private static final URL getResource(String resource) {
		return WsgColladaExtension.class.getClassLoader().getResource("models/" + resource);
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Schunk WSG Collada visualization model";
	}

	@Override
	public String getDescription() {
		return "Collada model of Schunk WSG to display it in Robotics API visualization";
	}
}
