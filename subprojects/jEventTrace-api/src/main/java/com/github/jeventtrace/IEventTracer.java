// SPDX-License-Identifier: Apache-2.0
package com.github.jeventtrace;

import javax.annotation.concurrent.ThreadSafe;
import java.nio.file.Path;
import java.util.concurrent.Callable;

@ThreadSafe
public interface IEventTracer {

    void eventInstant(String name);

    IEvent eventDuration(String name);

    void eventDuration(String name, Runnable action);

    <T> T eventDuration(String name, IAction<T> action);

    <T> T eventDurationEx(String name, Callable<T> callable) throws Exception;

    void writeEvents(Path file);
}
