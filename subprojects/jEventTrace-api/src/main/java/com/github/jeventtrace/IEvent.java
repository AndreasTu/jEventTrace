// SPDX-License-Identifier: Apache-2.0
package com.github.jeventtrace;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public interface IEvent extends AutoCloseable{

    void close();
}
