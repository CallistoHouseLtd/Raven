/*******************************************************************************
 * The MIT License (MIT)
 *
 * Copyright (c) 2003, 2016 Robert Withers
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * ******************************************************************************
 * porcini/whisper would not be possible without the ideas, implementation, 
 * brilliance and passion of the Squeak/Pharo communities and the cryptography 
 * team, which are this software's foundation.
 * ******************************************************************************
 * porcini/whisper would not be possible without the ideas, implementation, 
 * brilliance and passion of the erights.org community, which is also this software's 
 * foundation.  In particular, I would like to thank the following individuals:
 *         Mark Miller
 *         Marc Stiegler
 *         Bill Franz
 *         Tyler Close 
 *         Kevin Reid
 *******************************************************************************/
package club.callistohouse.raven.remote;

import java.io.IOException;

import club.callistohouse.raven.PassByCopy;
import club.callistohouse.raven.Ref;
import club.callistohouse.raven.exceptions.NotResolvedException;
import club.callistohouse.raven.scope.Scope;

@SuppressWarnings("serial")
public abstract class RavenMessage implements PassByCopy {

	public static String argumentsToString(Object[] arguments) {
		if(arguments.length == 0)
			return "[]";
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		for(int i = 0; i < arguments.length - 1; i++) {
			builder.append(arguments[i].toString());
			builder.append(", ");
		}
		builder.append(arguments[arguments.length - 1].toString());
		builder.append("]");
		return builder.toString();
	}

	public Byte getId() { return getType().getId(); }
	public abstract MessageEnum getType();
    public String description() { return getType().description(); }

	public Ref sendMessageOnScope(Scope scope) throws IOException, NotResolvedException {
		scope.send(this);
		return null;
	}
	public abstract void receiveMessageOnScope(Scope scope) throws IOException, NotResolvedException;
}
