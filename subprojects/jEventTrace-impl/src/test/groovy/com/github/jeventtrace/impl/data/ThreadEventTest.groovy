package com.github.jeventtrace.impl.data

import spock.lang.Specification

class ThreadEventTest extends Specification {

    def "creation"() {
        when:
        def th = new ThreadEvent("Th", 1)
        then:
        th.type == ETraceType.METADATA
        th.toString() == "ThreadEvent{threadName='Th', tid=1}"
        th.tid == 1
    }
}
