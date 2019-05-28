/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg;

import org.roboticsapi.core.InvalidParametersException;
import org.roboticsapi.core.sensor.DoubleSensor;
import org.roboticsapi.schunk.wsg.internal.WSG50BaseJaw;
import org.roboticsapi.schunk.wsg.sensor.GraspingStateSensor;
import org.roboticsapi.tool.gripper.AbstractParallelGripper;
import org.roboticsapi.tool.gripper.AccelerationConsideringGripper;
import org.roboticsapi.tool.gripper.BaseJaw;
import org.roboticsapi.tool.gripper.ForceConsideringGripper;
import org.roboticsapi.tool.gripper.VelocityConsideringGripper;
import org.roboticsapi.tool.gripper.parameters.AccelerationParameter;
import org.roboticsapi.tool.gripper.parameters.ForceParameter;
import org.roboticsapi.tool.gripper.parameters.VelocityParameter;
import org.roboticsapi.world.Frame;
import org.roboticsapi.world.Transformation;

public class WSG50 extends AbstractParallelGripper<WSG50GripperDriver>
		implements AccelerationConsideringGripper, ForceConsideringGripper, VelocityConsideringGripper {

	private static final double RECOMMENDED_WORKPIECE_WEIGHT = 0.8d;

	private static final double MINIMAL_BASE_JAW_DISTANCE = 0d;
	private static final double STROKE_PER_FINGER = 0.055d;

	private static final double MIN_VELOCITY = 0.005;
	private static final double MAX_VELOCITY = 0.42;

	private static final double MIN_FORCE = 5.0;
	private static final double MAX_FORCE = 80.0;

	private static final double MIN_ACC = 0.1;
	private static final double MAX_ACC = 5.0;

	public WSG50() {
		super(RECOMMENDED_WORKPIECE_WEIGHT, MINIMAL_BASE_JAW_DISTANCE, STROKE_PER_FINGER, MIN_VELOCITY, MAX_VELOCITY,
				MIN_FORCE, MAX_FORCE, MIN_ACC, MAX_ACC);
	}

	@Override
	public DoubleSensor getBaseJawOpeningWidthSensor() {

		// return new ConstantDoubleSensor(0.02); //
		return getDriver().getBaseJawOpeningWidthSensor();
	}

	@Override
	public DoubleSensor getVelocitySensor() {
		return getDriver().getVelocitySensor();
	}

	@Override
	public DoubleSensor getForceSensor() {
		return getDriver().getForceSensor();
	}

	@Override
	public DoubleSensor getAccelerationSensor() {
		return getDriver().getAccelerationSensor();
	}

	public GraspingStateSensor getGraspingStateSensor() {
		return getDriver().getGraspingStateSensor();
	}

	@Override
	protected final void defineDefaultParameters() throws InvalidParametersException {
		super.defineDefaultParameters();

		// Set default values
		addDefaultParameters(new VelocityParameter((MAX_VELOCITY + MIN_VELOCITY) / 2));
		addDefaultParameters(new ForceParameter((MAX_FORCE + MIN_FORCE) / 2));
		addDefaultParameters(new AccelerationParameter((MAX_ACC + MIN_ACC) / 2));
		// TODO: Softlimits
	}

	@Override
	protected Transformation createBase2CenterTransformation() {
		return new Transformation(0, 0, 0.0725, 0, 0, 0);
	}

	@Override
	protected BaseJaw createBaseJaw(int index, Frame mountFrame) {
		return new WSG50BaseJaw(mountFrame);
	}

}
