package com.github.cachingtransformerfactory

import spock.lang.Specification

import javax.xml.transform.stream.StreamSource

class StreamSourceWrapperTest extends Specification {

    def "isCachable() returns true for StreamSources with a systemId"() {
        given:
        def streamSource = new StreamSource(systemId)
        def wrapper = new StreamSourceWrapper(streamSource)

        when:
        def result = wrapper.cacheable

        then:
        result == cachable

        where:
        systemId      | isCacheable
        null          | false
        ""            | false
        "file://jada" | true
    }

    def "hashCode() returns the systemId hashcode for StreamSources with a systemId"() {
        given:
        def systemId = "file://jada.txt"
        def streamSource = new StreamSource(systemId)
        def wrapper = new StreamSourceWrapper(streamSource)

        when:
        def result = wrapper.hashCode()

        then:
        result == systemId.hashCode()
    }

    def "hashCode() returns the StreamSource hashcode a StreamSource without a systemId"() {
        given:
        def streamSource = new StreamSource(systemId)
        def wrapper = new StreamSourceWrapper(streamSource)

        when:
        def result = wrapper.hashCode()

        then:
        result == streamSource.hashCode()

        where:
        systemId << [null, ""]
    }

    def "equals() returns true for the same systemId"() {
        given:
        def systemId = "file://jada.txt"
        def wrapper1 = new StreamSourceWrapper(new StreamSource(systemId))
        def wrapper2 = new StreamSourceWrapper(new StreamSource(systemId))

        when:
        def result = wrapper1.equals(wrapper2)

        then:
        result
    }

    def "equals() returns false for the same systemId"() {
        given:
        def wrapper1 = new StreamSourceWrapper(new StreamSource("file://1.txt"))
        def wrapper2 = new StreamSourceWrapper(new StreamSource("file://2.txt"))

        when:
        def result = wrapper1.equals(wrapper2)

        then:
        !result
    }

    def "equals() returns false for other objects"() {
        given:
        def wrapper = new StreamSourceWrapper(new StreamSource("file://1.txt"))

        when:
        def result = wrapper.equals(other)

        then:
        !result

        where:
        other << ["", null, 123, new Date()]
    }
}

