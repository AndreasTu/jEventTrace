package com.github.jeventtrace.impl.data

import spock.lang.Specification

class ProcessEventTest extends Specification {

    def "creation"() {
        when:
        def ph = new ProcessEvent()
        then:
        ph.type == ETraceType.METADATA
        ph.toString().startsWith("ProcessEvent{processName='")
        ph.tid == ITraceEvent.INVALID_TID
    }
}
