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
package club.callistohouse.raven.refs;

import club.callistohouse.raven.ReactorInterface;
import club.callistohouse.raven.Ref;
import club.callistohouse.raven.exceptions.NotResolvedException;
import club.callistohouse.raven.vat.MessageSend;



public class BrokenRefImpl extends RefImpl {

	Throwable exception = null;

	public BrokenRefImpl(Throwable e) {
		this.exception = e;
	}

	public String toString() {
		return getClass().getSimpleName() 
				+ "(hash: " + hashCode() 
				+ ", exception: " + (getReceiver() != null ? getReceiver().getLocalizedMessage() : "Not Specified") 
				+ ")";
	}

	public Throwable getReceiver() { return exception; }
	public Throwable getReceiver(long timeout) { return getReceiver(); }

	public Ref redirectMessage(MessageSend send) {
		if(!send.isOneWay() && send.getResolver() != null)
			try {
				send.getResolver().smash(getReceiver());
			} catch (NotResolvedException e) {
				e.printStackTrace();
			}
		return getProxy();
	}
	public void whenMoreResolved(ReactorInterface reactor) throws NotResolvedException {
		((ReactorInterface) reactor).value(getReceiver());
	}
	public void whenBroken(ReactorInterface reactor) throws NotResolvedException {
		reactor.value(getReceiver());
	}
}
