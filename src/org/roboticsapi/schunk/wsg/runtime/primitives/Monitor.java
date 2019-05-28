package org.roboticsapi.schunk.wsg.runtime.primitives;

import org.roboticsapi.runtime.core.types.RPIstring;
import org.roboticsapi.runtime.rpi.OutPort;
import org.roboticsapi.runtime.rpi.Parameter;
import org.roboticsapi.runtime.rpi.Primitive;

/**
 * Module which gives information about current values of a gripper device.
 */
public class Monitor extends Primitive {
	/** Type name of the primitive */
	public static final String PRIMITIVE_TYPE = "schunkwsg::Monitor";

	/** Force */
	private final OutPort outForce = new OutPort("outForce");

	/** Grasping state */
	private final OutPort outGraspingState = new OutPort("outGraspingState");

	/** Opening width */
	private final OutPort outOpeningWidth = new OutPort("outOpeningWidth");

	/** Speed */
	private final OutPort outSpeed = new OutPort("outSpeed");

	/** Name of the device */
	private final Parameter<RPIstring> paramDevice = new Parameter<RPIstring>("Device", new RPIstring(""));

	public Monitor() {
		super(PRIMITIVE_TYPE);

		// Add all ports
		add(outForce);
		add(outGraspingState);
		add(outOpeningWidth);
		add(outSpeed);

		// Add all parameters
		add(paramDevice);
	}

	/**
	 * Creates module which gives information about current values of a gripper
	 * device.
	 * 
	 * @param device Name of the device
	 */
	public Monitor(RPIstring paramDevice) {
		this();

		// Set the parameters
		setDevice(paramDevice);
	}

	/**
	 * Creates module which gives information about current values of a gripper
	 * device.
	 * 
	 * @param device Name of the device
	 */
	public Monitor(String paramDevice) {
		this(new RPIstring(paramDevice));
	}

	/**
	 * Force
	 * 
	 * @return the output port of the block
	 */
	public final OutPort getOutForce() {
		return this.outForce;
	}

	/**
	 * Grasping state
	 * 
	 * @return the output port of the block
	 */
	public final OutPort getOutGraspingState() {
		return this.outGraspingState;
	}

	/**
	 * Opening width
	 * 
	 * @return the output port of the block
	 */
	public final OutPort getOutOpeningWidth() {
		return this.outOpeningWidth;
	}

	/**
	 * Speed
	 * 
	 * @return the output port of the block
	 */
	public final OutPort getOutSpeed() {
		return this.outSpeed;
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
