// SPDX-License-Identifier: Apache-2.0
package com.github.jeventtrace.impl.data;

import javax.annotation.concurrent.Immutable;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

import static com.github.jeventtrace.impl.EUtil.writeLong;
import static com.github.jeventtrace.impl.EUtil.writeStringEscaped;

@Immutable
public final class TimedEvent implements ITraceEvent {

    private final long elapsedTimeMicro;
    private final long stack;
    private final long tid;
    private final ETraceType type;
    private final String name;

    public TimedEvent(long tid, long elapsedTimeMicro, long stack, ETraceType type, String name) {
        this.tid = tid;
        this.elapsedTimeMicro = elapsedTimeMicro;
        this.stack = stack;
        this.name = name;
        Objects.requireNonNull(this.type = type);
    }

    public long getElapsedTimeMicro() {
        return elapsedTimeMicro;
    }

    public long getStack() {
        return stack;
    }

    @Override
    public ETraceType getType() {
        return type;
    }

    @Override
    public long getTid() {
        return tid;
    }


    @Override
    public void write(Writer writer) throws IOException {
        writer.write("{\"name\":\"");
        writeStringEscaped(writer, name);
        writer.write("\",\"ph\":\"");
        writer.write(getType().getTag());
        writer.write("\",\"pid\":");
        writeLong(writer, PID);
        writer.write(",\"tid\":");
        writeLong(writer, tid);
        writer.write(",\"ts\":");
        writeLong(writer, elapsedTimeMicro);
        if (stack >= 0) {
            writer.write(",\"sf\":");
            writeLong(writer, stack);
        }
        writer.write("}");
    }

    @Override
    public String toString() {
        return "TimedEvent{" +
                "elapsedTimeMicro=" + elapsedTimeMicro +
                ", stack=" + stack +
                ", tid=" + tid +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
