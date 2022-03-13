// SPDX-License-Identifier: Apache-2.0
package com.github.jeventtrace.impl.data;

import java.io.IOException;
import java.io.Writer;

public interface ITraceEvent {
    long INVALID_TID = -1;
    long PID = ProcessHandle.current().pid();

    ETraceType getType();

    long getTid();

    void write(Writer writer) throws IOException;
}
