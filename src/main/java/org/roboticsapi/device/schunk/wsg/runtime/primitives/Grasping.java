package org.roboticsapi.device.schunk.wsg.runtime.primitives;

import org.roboticsapi.facet.runtime.rpi.InPort;
import org.roboticsapi.facet.runtime.rpi.OutPort;
import org.roboticsapi.facet.runtime.rpi.Parameter;
import org.roboticsapi.facet.runtime.rpi.Primitive;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIdouble;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIstring;

/**
 * Module to control grasping of a gripper device.
 */
public class Grasping extends Primitive {
	/** Type name of the primitive */
	public static final String PRIMITIVE_TYPE = "schunkwsg::Grasping";

	/** Activation port */
	private final InPort inActive = new InPort("inActive");

	/** Reset port */
	private final InPort inReset = new InPort("inReset");

	/** Finished */
	private final OutPort outFinished = new OutPort("outFinished");

	/** StatusCode */
	private final OutPort outStatusCode = new OutPort("outStatusCode");

	/** Expected part width in [m]. */
	private final Parameter<RPIdouble> paramWidth = new Parameter<RPIdouble>("Width", new RPIdouble("0"));

	/** Traveling speed in [m/s]. */
	private final Parameter<RPIdouble> paramSpeed = new Parameter<RPIdouble>("Speed", new RPIdouble("0"));

	/** Name of the device */
	private final Parameter<RPIstring> paramDevice = new Parameter<RPIstring>("Device", new RPIstring(""));

	public Grasping() {
		super(PRIMITIVE_TYPE);

		// Add all ports
		add(inActive);
		add(inReset);
		add(outFinished);
		add(outStatusCode);

		// Add all parameters
		add(paramWidth);
		add(paramSpeed);
		add(paramDevice);
	}

	/**
	 * Creates module to control grasping of a gripper device.
	 * 
	 * @param width  Expected part width in [m].
	 * @param speed  Traveling speed in [m/s].
	 * @param device Name of the device
	 */
	public Grasping(RPIdouble paramWidth, RPIdouble paramSpeed, RPIstring paramDevice) {
		this();

		// Set the parameters
		setWidth(paramWidth);
		setSpeed(paramSpeed);
		setDevice(paramDevice);
	}

	/**
	 * Creates module to control grasping of a gripper device.
	 * 
	 * @param width  Expected part width in [m].
	 * @param speed  Traveling speed in [m/s].
	 * @param device Name of the device
	 */
	public Grasping(Double paramWidth, Double paramSpeed, String paramDevice) {
		this(new RPIdouble(paramWidth), new RPIdouble(paramSpeed), new RPIstring(paramDevice));
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
	 * Expected part width in [m].
	 * 
	 * @return the parameter of the block
	 */
	public final Parameter<RPIdouble> getWidth() {
		return this.paramWidth;
	}

	/**
	 * Sets a parameter of the block: Expected part width in [m].
	 * 
	 * @param value new value of the parameter
	 */
	public final void setWidth(RPIdouble value) {
		this.paramWidth.setValue(value);
	}

	/**
	 * Sets a parameter of the block: Expected part width in [m].
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
