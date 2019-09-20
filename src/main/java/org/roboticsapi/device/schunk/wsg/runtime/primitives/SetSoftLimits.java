package org.roboticsapi.device.schunk.wsg.runtime.primitives;

import org.roboticsapi.facet.runtime.rpi.InPort;
import org.roboticsapi.facet.runtime.rpi.OutPort;
import org.roboticsapi.facet.runtime.rpi.Parameter;
import org.roboticsapi.facet.runtime.rpi.Primitive;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIdouble;
import org.roboticsapi.facet.runtime.rpi.core.types.RPIstring;

/**
 * Module to control releasing of a gripper device.
 */
public class SetSoftLimits extends Primitive {
	/** Type name of the primitive */
	public static final String PRIMITIVE_TYPE = "schunkwsg::SetSoftLimits";

	/** Activation port */
	private final InPort inActive = new InPort("inActive");

	/** Reset port */
	private final InPort inReset = new InPort("inReset");

	/** Finished */
	private final OutPort outFinished = new OutPort("outFinished");

	/** StatusCode */
	private final OutPort outStatusCode = new OutPort("outStatusCode");

	/** Soft limit opening width in negative motion direction ([m]). */
	private final Parameter<RPIdouble> paramInner = new Parameter<RPIdouble>("Inner", new RPIdouble("0"));

	/** Soft limit opening width in positive motion direction ([m]). */
	private final Parameter<RPIdouble> paramOuter = new Parameter<RPIdouble>("Outer", new RPIdouble("0"));

	/** Name of the device */
	private final Parameter<RPIstring> paramDevice = new Parameter<RPIstring>("Device", new RPIstring(""));

	public SetSoftLimits() {
		super(PRIMITIVE_TYPE);

		// Add all ports
		add(inActive);
		add(inReset);
		add(outFinished);
		add(outStatusCode);

		// Add all parameters
		add(paramInner);
		add(paramOuter);
		add(paramDevice);
	}

	/**
	 * Creates module to control releasing of a gripper device.
	 * 
	 * @param inner  Soft limit opening width in negative motion direction ([m]).
	 * @param outer  Soft limit opening width in positive motion direction ([m]).
	 * @param device Name of the device
	 */
	public SetSoftLimits(RPIdouble paramInner, RPIdouble paramOuter, RPIstring paramDevice) {
		this();

		// Set the parameters
		setInner(paramInner);
		setOuter(paramOuter);
		setDevice(paramDevice);
	}

	/**
	 * Creates module to control releasing of a gripper device.
	 * 
	 * @param inner  Soft limit opening width in negative motion direction ([m]).
	 * @param outer  Soft limit opening width in positive motion direction ([m]).
	 * @param device Name of the device
	 */
	public SetSoftLimits(Double paramInner, Double paramOuter, String paramDevice) {
		this(new RPIdouble(paramInner), new RPIdouble(paramOuter), new RPIstring(paramDevice));
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
	 * Soft limit opening width in negative motion direction ([m]).
	 * 
	 * @return the parameter of the block
	 */
	public final Parameter<RPIdouble> getInner() {
		return this.paramInner;
	}

	/**
	 * Sets a parameter of the block: Soft limit opening width in negative motion
	 * direction ([m]).
	 * 
	 * @param value new value of the parameter
	 */
	public final void setInner(RPIdouble value) {
		this.paramInner.setValue(value);
	}

	/**
	 * Sets a parameter of the block: Soft limit opening width in negative motion
	 * direction ([m]).
	 * 
	 * @param value new value of the parameter
	 */
	public final void setInner(Double value) {
		this.setInner(new RPIdouble(value));
	}

	/**
	 * Soft limit opening width in positive motion direction ([m]).
	 * 
	 * @return the parameter of the block
	 */
	public final Parameter<RPIdouble> getOuter() {
		return this.paramOuter;
	}

	/**
	 * Sets a parameter of the block: Soft limit opening width in positive motion
	 * direction ([m]).
	 * 
	 * @param value new value of the parameter
	 */
	public final void setOuter(RPIdouble value) {
		this.paramOuter.setValue(value);
	}

	/**
	 * Sets a parameter of the block: Soft limit opening width in positive motion
	 * direction ([m]).
	 * 
	 * @param value new value of the parameter
	 */
	public final void setOuter(Double value) {
		this.setOuter(new RPIdouble(value));
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
