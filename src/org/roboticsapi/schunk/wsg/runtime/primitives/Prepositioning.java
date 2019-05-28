package org.roboticsapi.schunk.wsg.runtime.primitives;

import org.roboticsapi.runtime.core.types.RPIbool;
import org.roboticsapi.runtime.core.types.RPIdouble;
import org.roboticsapi.runtime.core.types.RPIstring;
import org.roboticsapi.runtime.rpi.InPort;
import org.roboticsapi.runtime.rpi.OutPort;
import org.roboticsapi.runtime.rpi.Parameter;
import org.roboticsapi.runtime.rpi.Primitive;

/**
 * Module to control prepositioning of a gripper device.
 */
public class Prepositioning extends Primitive {
	/** Type name of the primitive */
	public static final String PRIMITIVE_TYPE = "schunkwsg::Prepositioning";

	/** Activation port */
	private final InPort inActive = new InPort("inActive");

	/** Reset port */
	private final InPort inReset = new InPort("inReset");

	/** Finished */
	private final OutPort outFinished = new OutPort("outFinished");

	/** StatusCode */
	private final OutPort outStatusCode = new OutPort("outStatusCode");

	/** Opening width in [m]. */
	private final Parameter<RPIdouble> paramWidth = new Parameter<RPIdouble>("Width", new RPIdouble("0"));

	/** Traveling speed in [m/s]. */
	private final Parameter<RPIdouble> paramSpeed = new Parameter<RPIdouble>("Speed", new RPIdouble("0"));

	/**
	 * If 'true', the passed width is treated as an offset to the current opening
	 * width.
	 */
	private final Parameter<RPIbool> paramRelativeMotion = new Parameter<RPIbool>("RelativeMotion",
			new RPIbool("true"));

	/**
	 * Important for the case that the opening movement is blocked by any obstacle.
	 * If 'false', the motor is stopped. If 'true', the motor is not turned off
	 * automatically, but clamps with the previously set force limit.
	 */
	private final Parameter<RPIbool> paramStopOnBlock = new Parameter<RPIbool>("StopOnBlock", new RPIbool("false"));

	/** Name of the device */
	private final Parameter<RPIstring> paramDevice = new Parameter<RPIstring>("Device", new RPIstring(""));

	public Prepositioning() {
		super(PRIMITIVE_TYPE);

		// Add all ports
		add(inActive);
		add(inReset);
		add(outFinished);
		add(outStatusCode);

		// Add all parameters
		add(paramWidth);
		add(paramSpeed);
		add(paramRelativeMotion);
		add(paramStopOnBlock);
		add(paramDevice);
	}

	/**
	 * Creates module to control prepositioning of a gripper device.
	 * 
	 * @param width          Opening width in [m].
	 * @param speed          Traveling speed in [m/s].
	 * @param relativeMotion If 'true', the passed width is treated as an offset to
	 *                       the current opening width.
	 * @param stopOnBlock    Important for the case that the opening movement is
	 *                       blocked by any obstacle. If 'false', the motor is
	 *                       stopped. If 'true', the motor is not turned off
	 *                       automatically, but clamps with the previously set force
	 *                       limit.
	 * @param device         Name of the device
	 */
	public Prepositioning(RPIdouble paramWidth, RPIdouble paramSpeed, RPIbool paramRelativeMotion,
			RPIbool paramStopOnBlock, RPIstring paramDevice) {
		this();

		// Set the parameters
		setWidth(paramWidth);
		setSpeed(paramSpeed);
		setRelativeMotion(paramRelativeMotion);
		setStopOnBlock(paramStopOnBlock);
		setDevice(paramDevice);
	}

	/**
	 * Creates module to control prepositioning of a gripper device.
	 * 
	 * @param width          Opening width in [m].
	 * @param speed          Traveling speed in [m/s].
	 * @param relativeMotion If 'true', the passed width is treated as an offset to
	 *                       the current opening width.
	 * @param stopOnBlock    Important for the case that the opening movement is
	 *                       blocked by any obstacle. If 'false', the motor is
	 *                       stopped. If 'true', the motor is not turned off
	 *                       automatically, but clamps with the previously set force
	 *                       limit.
	 * @param device         Name of the device
	 */
	public Prepositioning(Double paramWidth, Double paramSpeed, Boolean paramRelativeMotion, Boolean paramStopOnBlock,
			String paramDevice) {
		this(new RPIdouble(paramWidth), new RPIdouble(paramSpeed), new RPIbool(paramRelativeMotion),
				new RPIbool(paramStopOnBlock), new RPIstring(paramDevice));
	}

	/**
	 * Activation port
	 * 
	 * @return the input port of the block
	 */
	public final InPort getInActive() {
		return this.inActive;
	}

	/**
	 * Reset port
	 * 
	 * @return the input port of the block
	 */
	public final InPort getInReset() {
		return this.inReset;
	}

	/**
	 * Finished
	 * 
	 * @return the output port of the block
	 */
	public final OutPort getOutFinished() {
		return this.outFinished;
	}

	/**
	 * StatusCode
	 * 
	 * @return the output port of the block
	 */
	public final OutPort getOutStatusCode() {
		return this.outStatusCode;
	}

	/**
	 * Opening width in [m].
	 * 
	 * @return the parameter of the block
	 */
	public final Parameter<RPIdouble> getWidth() {
		return this.paramWidth;
	}

	/**
	 * Sets a parameter of the block: Opening width in [m].
	 * 
	 * @param value new value of the parameter
	 */
	public final void setWidth(RPIdouble value) {
		this.paramWidth.setValue(value);
	}

	/**
	 * Sets a parameter of the block: Opening width in [m].
	 * 
	 * @param value new value of the parameter
	 */
	public final void setWidth(Double value) {
		this.setWidth(new RPIdouble(value));
	}

	/**
	 * Traveling speed in [m/s].
	 * 
	 * @return the parameter of the block
	 */
	public final Parameter<RPIdouble> getSpeed() {
		return this.paramSpeed;
	}

	/**
	 * Sets a parameter of the block: Traveling speed in [m/s].
	 * 
	 * @param value new value of the parameter
	 */
	public final void setSpeed(RPIdouble value) {
		this.paramSpeed.setValue(value);
	}

	/**
	 * Sets a parameter of the block: Traveling speed in [m/s].
	 * 
	 * @param value new value of the parameter
	 */
	public final void setSpeed(Double value) {
		this.setSpeed(new RPIdouble(value));
	}

	/**
	 * If 'true', the passed width is treated as an offset to the current opening
	 * width.
	 * 
	 * @return the parameter of the block
	 */
	public final Parameter<RPIbool> getRelativeMotion() {
		return this.paramRelativeMotion;
	}

	/**
	 * Sets a parameter of the block: If 'true', the passed width is treated as an
	 * offset to the current opening width.
	 * 
	 * @param value new value of the parameter
	 */
	public final void setRelativeMotion(RPIbool value) {
		this.paramRelativeMotion.setValue(value);
	}

	/**
	 * Sets a parameter of the block: If 'true', the passed width is treated as an
	 * offset to the current opening width.
	 * 
	 * @param value new value of the parameter
	 */
	public final void setRelativeMotion(Boolean value) {
		this.setRelativeMotion(new RPIbool(value));
	}

	/**
	 * Important for the case that the opening movement is blocked by any obstacle.
	 * If 'false', the motor is stopped. If 'true', the motor is not turned off
	 * automatically, but clamps with the previously set force limit.
	 * 
	 * @return the parameter of the block
	 */
	public final Parameter<RPIbool> getStopOnBlock() {
		return this.paramStopOnBlock;
	}

	/**
	 * Sets a parameter of the block: Important for the case that the opening
	 * movement is blocked by any obstacle. If 'false', the motor is stopped. If
	 * 'true', the motor is not turned off automatically, but clamps with the
	 * previously set force limit.
	 * 
	 * @param value new value of the parameter
	 */
	public final void setStopOnBlock(RPIbool value) {
		this.paramStopOnBlock.setValue(value);
	}

	/**
	 * Sets a parameter of the block: Important for the case that the opening
	 * movement is blocked by any obstacle. If 'false', the motor is stopped. If
	 * 'true', the motor is not turned off automatically, but clamps with the
	 * previously set force limit.
	 * 
	 * @param value new value of the parameter
	 */
	public final void setStopOnBlock(Boolean value) {
		this.setStopOnBlock(new RPIbool(value));
	}

	/**
	 * Name of the device
	 * 
	 * @return the parameter of the block
	 */
	public final Parameter<RPIstring> getDevice() {
		return this.paramDevice;
	}

	/**
	 * Sets a parameter of the block: Name of the device
	 * 
	 * @param value new value of the parameter
	 */
	public final void setDevice(RPIstring value) {
		this.paramDevice.setValue(value);
	}

	/**
	 * Sets a parameter of the block: Name of the device
	 * 
	 * @param value new value of the parameter
	 */
	public final void setDevice(String value) {
		this.setDevice(new RPIstring(value));
	}

}
