// SPDX-License-Identifier: Apache-2.0
package com.github.jeventtrace;


import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link EventTrace} provides the API to issue trace events.
 */
@ThreadSafe
public final class EventTrace {
    private static final IEventTracer EVENT_TRACER;

    static {
        EVENT_TRACER = lookupEventTracer();
    }

    private static IEventTracer lookupEventTracer() {
        try {
            Class<?> implClass = EventTrace.class.getClassLoader().loadClass("com.github.jeventtrace.impl.EventTracer");
            IEventTracerInternal tracer = (IEventTracerInternal) implClass.getConstructor().newInstance();
            return tracer.initTracer(NoOpEventTracer.INSTANCE);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | ClassCastException ex) {
            Logger.getLogger(EventTrace.class.getName()).log(Level.SEVERE, "Failed to initialize jEventTrace.", ex);
            return NoOpEventTracer.INSTANCE;
        } catch (ClassNotFoundException ex) {
            return NoOpEventTracer.INSTANCE;
        }
    }

    @SuppressFBWarnings("MS")
    public static IEventTracer getEventTracer() {
        return EVENT_TRACER;
    }

    public static <T> T eventDuration(String name, IAction<T> action) {
        return EVENT_TRACER.eventDuration(name, action);
    }

    public static <T> T eventDurationEx(String name, Callable<T> action) throws Exception {
        return EVENT_TRACER.eventDurationEx(name, action);
    }

    public static void eventDuration(String name, Runnable action) {
        EVENT_TRACER.eventDuration(name, action);
    }

    public static void eventInstant(String name) {
        EVENT_TRACER.eventInstant(name);
    }

    public static IEvent eventDuration(String name) {
        return EVENT_TRACER.eventDuration(name);
    }
}
