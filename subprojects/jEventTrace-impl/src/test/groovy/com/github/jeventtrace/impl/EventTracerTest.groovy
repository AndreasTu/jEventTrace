package com.github.jeventtrace.impl

import com.github.jeventtrace.EventTrace
import spock.lang.Specification

class EventTracerTest extends Specification {

    def "Lookup EventTracer"() {
        expect:
        EventTrace.eventTracer instanceof EventTracer
    }

    def "Creation"() {
        when:
        def t = new EventTracer()
        then:
        t != null
        t.toString() == "JEventTracer"
    }
}
