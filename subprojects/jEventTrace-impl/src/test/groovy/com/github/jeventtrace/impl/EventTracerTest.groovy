package com.github.jeventtrace.impl

import spock.lang.Specification

class EventTracerTest extends Specification {

    def "Creation"() {
        when:
        def t = new EventTracer()
        then:
        t != null
        t.toString() == "JEventTracer"
    }
}
