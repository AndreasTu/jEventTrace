package com.github.jeventtrace.impl

import spock.lang.Specification

class EUtilTest extends Specification {

    def "checkNotNullOrEmpty"() {
        expect:
        EUtil.checkNotNullOrEmpty("A") == "A"
        when:
        EUtil.checkNotNullOrEmpty(null)
        then:
        thrown(IllegalArgumentException)
        when:
        EUtil.checkNotNullOrEmpty("")
        then:
        thrown(IllegalArgumentException)
    }

    def "writeLong"() {
        setup:
        def sb = new StringWriter()
        when:
        EUtil.writeLong(sb, 1)
        then:
        sb.toString() == "1"
    }

    def "writeStringEscaped"(String input, String expectedOutput) {
        setup:
        def sb = new StringWriter()
        when:
        EUtil.writeStringEscaped(sb, input)
        then:
        input && sb.toString() == expectedOutput

        where:
        input           | expectedOutput
        "A"             | "A"
        '"B"'           | '\\"B\\"'
        'Prefix"Suffix' | 'Prefix\\"Suffix'
    }
}
