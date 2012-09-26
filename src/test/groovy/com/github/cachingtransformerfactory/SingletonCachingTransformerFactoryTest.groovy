package com.github.cachingtransformerfactory

import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl
import spock.lang.Specification

class SingletonCachingTransformerFactoryTest extends Specification {

    def setup() {
        System.setProperty("javax.xml.transform.TransformerFactory", CachingTransformerFactory.class.name)
        System.setProperty("com.github.cachingtransformerfactory.CachingTransformerFactory.delegate", TransformerFactoryImpl.class.name)
    }

    def cleanup() {
        System.getProperties().remove("com.github.cachingtransformerfactory.CachingTransformerFactory.delegate")
        System.setProperty("javax.xml.transform.TransformerFactory", TransformerFactoryImpl.class.name)
    }

    def "getInstance() returns the same instance"() {
        when:
        def a = SingletonCachingTransformerFactory.instance
        def b = SingletonCachingTransformerFactory.instance

        then:
        a.is(b)
    }
}
