package com.github.jeventtrace.impl

import com.github.jeventtrace.EventTrace
import com.github.jeventtrace.EventTraceProperties
import com.github.jeventtrace.IAction
import com.github.jeventtrace.IEventTracer
import com.github.jeventtrace.impl.data.ITraceEvent
import com.github.jeventtrace.impl.data.ProcessEvent
import com.github.jeventtrace.impl.data.ThreadEvent
import com.github.jeventtrace.impl.data.TimedEvent
import spock.lang.Specification
import spock.util.environment.RestoreSystemProperties

import java.util.concurrent.atomic.AtomicInteger

class EventTracerTest extends Specification {
    private static final String NAME = "Code"
    private static final String RET = "Return-Value"

    def t = new EventTracer()

    def "Lookup EventTracer"() {
        expect:
        EventTrace.eventTracer instanceof EventTracer
    }

    def "Creation"() {
        expect:
        t != null
        t.toString() == "JEventTracer"

        when:
        def noopTracer = Mock(IEventTracer)
        def result = t.initTracer(noopTracer)
        then:
        result == t
    }

    @RestoreSystemProperties
    def "disabled event tracing"() {
        setup:
        System.setProperty(EventTraceProperties.ENABLED, "false")
        when:
        def noopTracer = Mock(IEventTracer)
        def result = t.initTracer(noopTracer)
        then:
        result == noopTracer
    }

    def "eventDuration(String,Action)"() {
        expect:
        t.eventDuration(NAME, { return RET } as IAction) == RET
        eventLogSize() == 4
        eventByIndex(0) instanceof ProcessEvent
        eventByIndex(1) instanceof ThreadEvent
        eventByIndex(2) instanceof TimedEvent
        eventByIndex(3) instanceof TimedEvent
    }

    def "eventDuration(String,Runnable)"() {
        when:
        def count = new AtomicInteger()
        t.eventDuration(NAME, {
            count.incrementAndGet()
        } as Runnable)
        then:
        count.get() == 1
        eventLogSize() == 4
        eventByIndex(2) instanceof TimedEvent
        eventByIndex(3) instanceof TimedEvent
    }

    def "eventDurationEx(String,Callable)"() {
        expect:
        t.eventDurationEx(NAME, { return RET }) == RET
        eventLogSize() == 4
        eventByIndex(2) instanceof TimedEvent
        eventByIndex(3) instanceof TimedEvent
    }

    def "eventDurationEx(String,Callable) throws ex"() {
        setup:
        def ex = new Exception()
        when:
        t.eventDurationEx(NAME, { throw ex })

        then:
        def caught = thrown(Exception)
        ex == caught
        eventLogSize() == 4
        eventByIndex(2) instanceof TimedEvent
        eventByIndex(3) instanceof TimedEvent
    }

    def "eventInstant"() {
        when:
        t.eventInstant(NAME)
        then:
        noExceptionThrown()
        eventLogSize() == 3
        eventByIndex(2) instanceof TimedEvent
    }

    def "eventDuration(String)"() {
        when:
        def event = t.eventDuration(NAME)
        then:
        event != null
        t.eventLog.events.size() == 3
        when:
        event.close()
        then:
        noExceptionThrown()
        eventLogSize() == 4
        eventByIndex(2) instanceof TimedEvent
        eventByIndex(3) instanceof TimedEvent
    }

    private int eventLogSize() {
        t.eventLog.events.size()
    }

    private ITraceEvent eventByIndex(int idx) {
        t.eventLog.events.get(idx)
    }
}
