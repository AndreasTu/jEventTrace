// SPDX-License-Identifier: Apache-2.0
package com.github.jeventtrace;

import javax.annotation.concurrent.ThreadSafe;
import java.nio.file.Path;
import java.util.concurrent.Callable;

@ThreadSafe
final class NoOpEventTracer implements IEventTracer {
    static final NoOpEventTracer INSTANCE = new NoOpEventTracer();
    private static final IEvent NOOP_EVENT = () -> {
    };

    private NoOpEventTracer() {

    }

    @Override
    public void eventInstant(String name) {

    }

    @Override
    public IEvent eventDuration(String name) {
        return NOOP_EVENT;
    }

    @Override
    public void eventDuration(String name, Runnable action) {
        action.run();
    }

    @Override
    public <T> T eventDuration(String name, IAction<T> action) {
        return action.run();
    }

    @Override
    public <T> T eventDurationEx(String name, Callable<T> callable) throws Exception {
        return callable.call();
    }

    @Override
    public void writeEvents(Path file) {

    }

    @Override
    public String toString() {
        return "NoOpJEventTracer";
    }
}
