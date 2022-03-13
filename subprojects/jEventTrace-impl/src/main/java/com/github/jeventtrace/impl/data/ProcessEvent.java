// SPDX-License-Identifier: Apache-2.0
package com.github.jeventtrace.impl.data;

import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.io.Writer;
import java.lang.management.ManagementFactory;

import static com.github.jeventtrace.impl.EUtil.writeLong;
import static com.github.jeventtrace.impl.EUtil.writeStringEscaped;

@Immutable
public final class ProcessEvent implements ITraceEvent {
    private final String processName;

    public ProcessEvent() {
        //FIXME: Add error handling, if this fails, add property to override name
        this.processName = ManagementFactory.getRuntimeMXBean().getName();
    }

    @Override
    public ETraceType getType() {
        return ETraceType.METADATA;
    }

    @Override
    public long getTid() {
        return INVALID_TID;
    }

    @Override
    public void write(Writer writer) throws IOException {
        writer.write("{\"name\":\"process_name\",\"ph\":\"M\",\"pid\":");
        writeLong(writer, PID);
        writer.write(",\"args\":{\"name\":\"");
        writeStringEscaped(writer, processName);
        writer.write("\"}}");
    }

    @Override
    public String toString() {
        return "ProcessEvent{" +
            "processName='" + processName + '\'' +
            ", pid=" + PID +
            '}';
    }
}
