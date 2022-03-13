package com.github.jeventtrace

import spock.lang.Specification

class EventTraceTest extends Specification {

    def "Init"() {
        when:
        def tracer = EventTrace.getEventTracer()
        then:
        tracer != null
        tracer instanceof NoOpEventTracer
        tracer.toString() == "NoOpJEventTracer"
    }
}
