/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. 
 *
 * Copyright 2010-2017 ISSE, University of Augsburg 
 */

package org.roboticsapi.schunk.wsg.runtime;

import org.roboticsapi.runtime.rpi.OutPort;
import org.roboticsapi.runtime.rpi.Primitive;

public final class PrimitiveWrapper {

	private final Primitive primitive;
	private final OutPort finished;
	private final OutPort statusCode;

	public PrimitiveWrapper(Primitive primitive, OutPort finished, OutPort statusCode) {
		this.primitive = primitive;
		this.finished = finished;
		this.statusCode = statusCode;
	}

	public Primitive getPrimitive() {
		return primitive;
	}

	public OutPort getFinished() {
		return finished;
	}

	public OutPort getStatusCode() {
		return statusCode;
	}

}
