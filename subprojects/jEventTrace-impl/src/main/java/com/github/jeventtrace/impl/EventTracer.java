// SPDX-License-Identifier: Apache-2.0
package com.github.jeventtrace.impl;

import com.github.jeventtrace.*;

import javax.annotation.concurrent.ThreadSafe;
import java.nio.file.Path;
import java.util.concurrent.Callable;

@ThreadSafe
public class EventTracer implements IEventTracerInternal {
    private final EventLog eventLog = new EventLog();

    public EventTracer() {

    }

    @Override
    public IEventTracer initTracer(IEventTracer noOpTracer) {
        boolean enabled = Boolean.getBoolean(EventTraceProperties.ENABLED);
        if (enabled) {
            return this;
        }
        return noOpTracer;
    }

    @Override
    public void eventInstant(String name) {
        eventLog.instantEvent(name);
    }

    @Override
    public IEvent eventDuration(String name) {
        eventLog.startDurationEvent(name);
        return () -> eventLog.endDurationEvent(name);
    }

    @Override
    public void eventDuration(String name, Runnable action) {
        eventLog.startDurationEvent(name);
        try {
            action.run();
        } finally {
            eventLog.endDurationEvent(name);
        }
    }

    @Override
    public <T> T eventDuration(String name, IAction<T> action) {
        eventLog.startDurationEvent(name);
        try {
            return action.run();
        } finally {
            eventLog.endDurationEvent(name);
        }
    }

    @Override
    public <T> T eventDurationEx(String name, Callable<T> callable) throws Exception {
        eventLog.startDurationEvent(name);
        try {
            return callable.call();
        } finally {
            eventLog.endDurationEvent(name);
        }
    }

    @Override
    public void writeEvents(Path file) {
        new EventWriter().write(file, eventLog);
    }


    @Override
    public String toString() {
        return "JEventTracer";
    }
}
