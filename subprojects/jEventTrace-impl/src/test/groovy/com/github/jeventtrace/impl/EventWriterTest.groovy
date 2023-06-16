package com.github.jeventtrace.impl

import com.github.jeventtrace.impl.data.ETraceType
import com.github.jeventtrace.impl.data.ITraceEvent

import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.nio.file.Paths

class EventWriterTest extends AbstractFileSpecification {
    def "Write"() {
        setup:
        def outFile = newEventFile()
        def eventTracer = new EventTracer()
        def pid = ITraceEvent.PID
        eventTracer.eventInstant("INST")
        eventTracer.eventDuration("DUR", {})
        when:
        eventTracer.writeEvents(outFile)
        then:
        def content = Files.readString(outFile)
        content.startsWith("""{"traceEvents":[
{"name":"process_name","ph":"M","pid":$pid,"args":{"name":""")

        content.contains("""{"name":"thread_name","ph":"M","pid":$pid,"tid":""")
        content.contains("""{"name":"INST","ph":"I","pid":$pid,"tid":""")

        content.contains("""{"name":"DUR","ph":"B","pid":$pid""")
        content.contains("""{"name":"DUR","ph":"E","pid":$pid""")

        content.endsWith("""}
]}""")
        def events = assertIsValidEventGson(outFile)
        events.size() == 5
        def pEvent = events[0]
        pEvent.name == "process_name"
        pEvent.ph == ETraceType.METADATA.tag
        pEvent.pid == pid

        def thEvent = events[1]
        thEvent.name == "thread_name"
        pEvent.ph == ETraceType.METADATA.tag
        thEvent.pid == pid
        int tid = thEvent.tid

        def instEvent = events[2]
        instEvent.name == "INST"
        instEvent.ph == ETraceType.INSTANT.tag
        instEvent.pid == pid
        instEvent.tid == tid

        def durStartEvent = events[3]
        durStartEvent.name == "DUR"
        durStartEvent.ph == ETraceType.DURATION_START.tag
        durStartEvent.pid == pid
        durStartEvent.tid == tid

        def durEndEvent = events[4]
        durEndEvent.name == "DUR"
        durEndEvent.ph == ETraceType.DURATION_END.tag
        durEndEvent.pid == pid
        durEndEvent.tid == tid
    }

    def "write to invalidFile"() {
        when:
        def invalidFile = Paths.get("invalidDir/invalidFile.json")
        new EventWriter().write(invalidFile, new EventTracer().getEventLog())
        then:
        def ex = thrown(UncheckedIOException)
        ex.message.startsWith("Failed to write jEventTrace data to file '")
        ex.cause instanceof NoSuchFileException
    }
}
