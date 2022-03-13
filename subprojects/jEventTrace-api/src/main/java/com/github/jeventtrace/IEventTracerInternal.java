// SPDX-License-Identifier: Apache-2.0
package com.github.jeventtrace;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface IEventTracerInternal extends IEventTracer {

    IEventTracer initTracer(IEventTracer noOpTracer);
}
