package com.github.jeventtrace

import spock.lang.Specification

import java.nio.file.Paths
import java.util.concurrent.atomic.AtomicInteger

class EventTraceTest extends Specification {
    private static final String NAME = "Code"
    private static final String RET = "Return-Value"

    def "Init"() {
        when:
        def tracer = EventTrace.getEventTracer()
        then:
        tracer != null
        tracer instanceof NoOpEventTracer
        tracer.toString() == "NoOpJEventTracer"
    }

    def "eventDuration(String,Action)"() {
        expect:
        EventTrace.eventDuration(NAME, { return RET } as IAction) == RET
    }

    def "eventDuration(String,Runnable)"() {
        when:
        def count = new AtomicInteger()
        EventTrace.eventDuration(NAME, {
            count.incrementAndGet()
        } as Runnable)
        then:
        count.get() == 1
    }

    def "eventDurationEx(String,Callable)"() {
        expect:
        EventTrace.eventDurationEx(NAME, { return RET }) == RET
    }

    def "eventDurationEx(String,Callable) throws ex"() {
        setup:
        def ex = new Exception()
        when:
        EventTrace.eventDurationEx(NAME, { throw ex })

        then:
        def caught = thrown(Exception)
        ex == caught
    }

    def "eventInstant with NOOP"() {
        when:
        EventTrace.eventInstant(NAME)
        then:
        noExceptionThrown()
    }

    def "eventDuration(String)"() {
        when:
        def event = EventTrace.eventDuration(NAME)
        then:
        event != null
        when:
        event.close()
        then:
        noExceptionThrown()
    }

    def "getEventTracer"() {
        expect:
        EventTrace.getEventTracer() == NoOpEventTracer.INSTANCE
    }

    def "tryLoadEventTracerFromClass with wrong class"() {
        when:
        def tracer = EventTrace.tryLoadEventTracerFromClass(this.class.name)
        then:
        tracer == NoOpEventTracer.INSTANCE
    }

    def "writeEvents() does nothing"() {
        when:
        EventTrace.getEventTracer().writeEvents(Paths.get("file"))
        then:
        noExceptionThrown()
    }
}
