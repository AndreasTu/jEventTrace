package com.github.jeventtrace.impl.data

import spock.lang.Specification

class TimedEventTest extends Specification {

    def "creation"() {
        when:
        def t = new TimedEvent(1, 5, 2, ETraceType.INSTANT, "inst")
        then:
        t.type == ETraceType.INSTANT
        t.toString() == "TimedEvent{elapsedTimeMicro=5, stack=2, tid=1, type=INSTANT, name='inst'}"
        t.tid == 1
        t.stack == 2
        t.elapsedTimeMicro == 5

        when: "Write with stack"
        def w = new StringWriter()
        t.write(w)
        then:
        w.toString().contains(',"sf":2}')
    }
}
