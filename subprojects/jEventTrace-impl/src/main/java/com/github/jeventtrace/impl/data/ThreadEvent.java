// SPDX-License-Identifier: Apache-2.0
package com.github.jeventtrace.impl.data;

import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.io.Writer;

import static com.github.jeventtrace.impl.EUtil.*;

@Immutable
public final class ThreadEvent implements ITraceEvent {

    private final String threadName;
    private final long tid;

    public ThreadEvent(String threadName, long tid) {
        this.tid = tid;
        this.threadName = checkNotNullOrEmpty(threadName);
    }

    @Override
    public ETraceType getType() {
        return ETraceType.METADATA;
    }

    @Override
    public long getTid() {
        return tid;
    }

    @Override
    public void write(Writer writer) throws IOException {
        writer.write("{\"name\":\"thread_name\",\"ph\":\"");
        writeStringEscaped(writer, getType().getTag());
        writer.write("\",\"pid\":");
        writeLong(writer, PID);
        writer.write(",\"tid\":");
        writeLong(writer, getTid());
        writer.write(",\"args\":{\"name\":\"");
        writeStringEscaped(writer, threadName);
        writer.write("\"}}");
    }

    @Override
    public String toString() {
        return "ThreadEvent{" +
            "threadName='" + threadName + '\'' +
            ", tid=" + tid +
            '}';
    }
}
