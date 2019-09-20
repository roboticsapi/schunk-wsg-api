/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2013-2019 ISSE, University of Augsburg 
 */

package org.roboticsapi.device.schunk.wsg;

import org.roboticsapi.core.DeviceParameters;
import org.roboticsapi.core.InvalidParametersException;
import org.roboticsapi.core.world.Transformation;
import org.roboticsapi.framework.gripper.AbstractParallelGripper;
import org.roboticsapi.framework.gripper.AccelerationConsideringGripper;
import org.roboticsapi.framework.gripper.BaseJaw;
import org.roboticsapi.framework.gripper.ForceConsideringGripper;
import org.roboticsapi.framework.gripper.GripperDriver;
import org.roboticsapi.framework.gripper.VelocityConsideringGripper;
import org.roboticsapi.framework.gripper.parameters.AccelerationParameter;
import org.roboticsapi.framework.gripper.parameters.ForceParameter;
import org.roboticsapi.framework.gripper.parameters.VelocityParameter;

public class Wsg50 extends AbstractParallelGripper<GripperDriver>
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

	public Wsg50() {
		super(RECOMMENDED_WORKPIECE_WEIGHT, MINIMAL_BASE_JAW_DISTANCE, STROKE_PER_FINGER);
	}

	@Override
	protected final BaseJaw createBaseJaw(int index) {
		return new Wsg50BaseJaw();
	}

	@Override
	protected Transformation createBaseToEffectorTransformation() {
		return new Transformation(0, 0, 0.0725, 0, 0, 0); // TODO:
	}

	@Override
	protected Transformation createBaseToCenterOfBaseJawBasesTransformation() {
		return new Transformation(0, 0, 0.0725, 0, 0, 0);
	}

	@Override
	public final double getMinimumVelocity() {
		return MIN_VELOCITY;
	}

	@Override
	public final double getMaximumVelocity() {
		return MAX_VELOCITY;
	}

	@Override
	public final double getMinimumForce() {
		return MIN_FORCE;
	}

	@Override
	public final double getMaximumForce() {
		return MAX_FORCE;
	}

	@Override
	public final double getMinimumAcceleration() {
		return MIN_ACC;
	}

	@Override
	public final double getMaximumAcceleration() {
		return MAX_ACC;
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
	public final void validateParameters(DeviceParameters parameters) throws InvalidParametersException {
		if (parameters instanceof VelocityParameter) {
			VelocityParameter vParameter = (VelocityParameter) parameters;
			checkValueRange(vParameter.getVelocity(), getMinimumVelocity(), getMaximumVelocity(), "velocity");
		} else if (parameters instanceof ForceParameter) {
			ForceParameter fParameter = (ForceParameter) parameters;
			checkValueRange(fParameter.getForce(), getMinimumForce(), getMaximumForce(), "force");
		} else if (parameters instanceof AccelerationParameter) {
			AccelerationParameter aParameter = (AccelerationParameter) parameters;
			checkValueRange(aParameter.getAcceleration(), getMinimumAcceleration(), getMaximumAcceleration(),
					"acceleration");
		}

		validateMoreParameters(parameters);
	}

	/**
	 * Empty implementation. Subclasses can override to validate further parameters.
	 * Velocity, force and acceleration are already validated by default.
	 *
	 * @param parameters device parameters to validate
	 * @throws InvalidParametersException if the parameters are not valid
	 */
	protected void validateMoreParameters(DeviceParameters parameters) throws InvalidParametersException {
		// empty implementation
	}

	@Override
	protected final void defineMaximumParameters() throws InvalidParametersException {
		// Set maximum values
		if (getMaximumVelocity() != 0d) {
			addMaximumParameters(new VelocityParameter(getMaximumVelocity()));
		}
		if (getMaximumForce() != 0d) {
			addMaximumParameters(new ForceParameter(getMaximumForce()));
		}
		if (getMaximumAcceleration() != 0d) {
			addMaximumParameters(new AccelerationParameter(getMaximumAcceleration()));
		}
		defineMoreMaximumParameters();
	}

	protected void defineMoreMaximumParameters() throws InvalidParametersException {
		// empty implementation
	}

	/**
	 * Checks whether a value is within a certain range. If not, a
	 * {@link InvalidParametersException} is thrown.
	 *
	 * @param value        the value to check
	 * @param min          the allowed maximum value
	 * @param max          the allowed minimum value
	 * @param propertyName the parameter's name
	 *
	 * @throws InvalidParametersException
	 */
	private final void checkValueRange(double value, double min, double max, String propertyName)
			throws InvalidParametersException {
		if (value < min || value > max) {
			throw new InvalidParametersException(
					"Only values in the range [" + min + "," + max + "] are permitted for " + propertyName + ".");
		}
	}

}
