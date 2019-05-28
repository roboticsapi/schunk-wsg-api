package org.roboticsapi.schunk.wsg.runtime.primitives;

import org.roboticsapi.runtime.core.types.RPIdouble;
import org.roboticsapi.runtime.core.types.RPIstring;
import org.roboticsapi.runtime.rpi.InPort;
import org.roboticsapi.runtime.rpi.OutPort;
import org.roboticsapi.runtime.rpi.Parameter;
import org.roboticsapi.runtime.rpi.Primitive;

/**
 * Module to control the acceleration of a gripper device.
 */
public class SetAcceleration extends Primitive {
	/** Type name of the primitive */
	public static final String PRIMITIVE_TYPE = "schunkwsg::SetAcceleration";

	/** Activation port */
	private final InPort inActive = new InPort("inActive");

	/** Reset port */
	private final InPort inReset = new InPort("inReset");

	/** Finished */
	private final OutPort outFinished = new OutPort("outFinished");

	/** StatusCode */
	private final OutPort outStatusCode = new OutPort("outStatusCode");

	/**
	 * Acceleration in [m/s²]. The value is clamped, if it is outside the device's
	 * capabilities.
	 */
	private final Parameter<RPIdouble> paramAcceleration = new Parameter<RPIdouble>("Acceleration", new RPIdouble("0"));

	/** Name of the device */
	private final Parameter<RPIstring> paramDevice = new Parameter<RPIstring>("Device", new RPIstring(""));

	public SetAcceleration() {
		super(PRIMITIVE_TYPE);

		// Add all ports
		add(inActive);
		add(inReset);
		add(outFinished);
		add(outStatusCode);

		// Add all parameters
		add(paramAcceleration);
		add(paramDevice);
	}

	/**
	 * Creates module to control the acceleration of a gripper device.
	 * 
	 * @param acceleration Acceleration in [m/s²]. The value is clamped, if it is
	 *                     outside the device's capabilities.
	 * @param device       Name of the device
	 */
	public SetAcceleration(RPIdouble paramAcceleration, RPIstring paramDevice) {
		this();

		// Set the parameters
		setAcceleration(paramAcceleration);
		setDevice(paramDevice);
	}

	/**
	 * Creates module to control the acceleration of a gripper device.
	 * 
	 * @param acceleration Acceleration in [m/s²]. The value is clamped, if it is
	 *                     outside the device's capabilities.
	 * @param device       Name of the device
	 */
	public SetAcceleration(Double paramAcceleration, String paramDevice) {
		this(new RPIdouble(paramAcceleration), new RPIstring(paramDevice));
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
	 * Acceleration in [m/s²]. The value is clamped, if it is outside the device's
	 * capabilities.
	 * 
	 * @return the parameter of the block
	 */
	public final Parameter<RPIdouble> getAcceleration() {
		return this.paramAcceleration;
	}

	/**
	 * Sets a parameter of the block: Acceleration in [m/s²]. The value is clamped,
	 * if it is outside the device's capabilities.
	 * 
	 * @param value new value of the parameter
	 */
	public final void setAcceleration(RPIdouble value) {
		this.paramAcceleration.setValue(value);
	}

	/**
	 * Sets a parameter of the block: Acceleration in [m/s²]. The value is clamped,
	 * if it is outside the device's capabilities.
	 * 
	 * @param value new value of the parameter
	 */
	public final void setAcceleration(Double value) {
		this.setAcceleration(new RPIdouble(value));
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
