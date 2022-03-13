// SPDX-License-Identifier: Apache-2.0
package com.github.jeventtrace.impl;

import com.github.jeventtrace.impl.data.*;

import javax.annotation.concurrent.ThreadSafe;
import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class EventLog {
    private static int EVENT_LIST_SIZE = 1024;
    private final long startTick;

    private final List<ITraceEvent> events = new ArrayList<>(EVENT_LIST_SIZE);

    private final ThreadLocal<ThreadData> threadTracked = ThreadLocal.withInitial(ThreadData::new);

    public EventLog() {
        startTick = EUtil.systemNanoTime();
        newEvent(new ProcessEvent());
    }

    public List<ITraceEvent> getEvents() {
        synchronized (this) {
            //FIXME: better memory handling here.
            return List.copyOf(events);
        }
    }

    private long elapsedMicro() {
        long currentNano = EUtil.systemNanoTime();
        return (currentNano - startTick) / 1000;
    }

    void instantEvent(String name) {
        timedEvent(name, ETraceType.INSTANT);
    }


    void startDurationEvent(String name) {
        timedEvent(name, ETraceType.DURATION_START);
    }

    void endDurationEvent(String name) {
        timedEvent(name, ETraceType.DURATION_END);
    }

    void timedEvent(String name, ETraceType type) {
        long elapsedTimeMicro = elapsedMicro();
        ThreadData thread = trackThread();
        long stack = createStack();
        newEvent(new TimedEvent(thread.getTid(), elapsedTimeMicro, stack, type, name));
    }

    private long createStack() {
        return (long) StackWalker.getInstance().walk(stack -> {

            return -1;
        });
    }

    private void newEvent(ITraceEvent traceEvent) {
        synchronized (this) {
            //FIXME: better concurrency here, also better list behavior with multiple list, to avoid copying.
            events.add(traceEvent);
        }
    }

    private ThreadData trackThread() {
        ThreadData threadData = threadTracked.get();
        if (!threadData.tracked) {
            issueThreadEvent(threadData.tid);
            threadData.tracked = true;
        }
        return threadData;
    }

    private void issueThreadEvent(long tid) {
        String threadName = Thread.currentThread().getName();
        newEvent(new ThreadEvent(threadName, tid));
    }

    public static class ThreadData {
        private boolean tracked;
        private final long tid;

        ThreadData() {
            this.tid = Thread.currentThread().getId();
        }

        public long getTid() {
            return tid;
        }
    }
}
