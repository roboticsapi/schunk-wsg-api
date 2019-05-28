package org.roboticsapi.schunk.wsg.runtime.primitives;

import org.roboticsapi.runtime.core.types.RPIbool;
import org.roboticsapi.runtime.core.types.RPIstring;
import org.roboticsapi.runtime.rpi.InPort;
import org.roboticsapi.runtime.rpi.OutPort;
import org.roboticsapi.runtime.rpi.Parameter;
import org.roboticsapi.runtime.rpi.Primitive;

/**
 * Module to control homing of a gripper device.
 */
public class Homing extends Primitive {
	/** Type name of the primitive */
	public static final String PRIMITIVE_TYPE = "schunkwsg::Homing";

	/** Activation port */
	private final InPort inActive = new InPort("inActive");

	/** Reset port */
	private final InPort inReset = new InPort("inReset");

	/** Finished */
	private final OutPort outFinished = new OutPort("outFinished");

	/** StatusCode */
	private final OutPort outStatusCode = new OutPort("outStatusCode");

	/** true for 'open', false for 'close'. */
	private final Parameter<RPIbool> paramDirection = new Parameter<RPIbool>("Direction", new RPIbool("true"));

	/** Name of the device */
	private final Parameter<RPIstring> paramDevice = new Parameter<RPIstring>("Device", new RPIstring(""));

	public Homing() {
		super(PRIMITIVE_TYPE);

		// Add all ports
		add(inActive);
		add(inReset);
		add(outFinished);
		add(outStatusCode);

		// Add all parameters
		add(paramDirection);
		add(paramDevice);
	}

	/**
	 * Creates module to control homing of a gripper device.
	 * 
	 * @param direction true for 'open', false for 'close'.
	 * @param device    Name of the device
	 */
	public Homing(RPIbool paramDirection, RPIstring paramDevice) {
		this();

		// Set the parameters
		setDirection(paramDirection);
		setDevice(paramDevice);
	}

	/**
	 * Creates module to control homing of a gripper device.
	 * 
	 * @param direction true for 'open', false for 'close'.
	 * @param device    Name of the device
	 */
	public Homing(Boolean paramDirection, String paramDevice) {
		this(new RPIbool(paramDirection), new RPIstring(paramDevice));
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
	 * true for 'open', false for 'close'.
	 * 
	 * @return the parameter of the block
	 */
	public final Parameter<RPIbool> getDirection() {
		return this.paramDirection;
	}

	/**
	 * Sets a parameter of the block: true for 'open', false for 'close'.
	 * 
	 * @param value new value of the parameter
	 */
	public final void setDirection(RPIbool value) {
		this.paramDirection.setValue(value);
	}

	/**
	 * Sets a parameter of the block: true for 'open', false for 'close'.
	 * 
	 * @param value new value of the parameter
	 */
	public final void setDirection(Boolean value) {
		this.setDirection(new RPIbool(value));
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