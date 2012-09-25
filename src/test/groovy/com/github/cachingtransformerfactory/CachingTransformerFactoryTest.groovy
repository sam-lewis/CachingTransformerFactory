package com.github.cachingtransformerfactory

import com.google.common.cache.CacheBuilder
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

import javax.xml.transform.Source
import javax.xml.transform.Templates
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamSource

class CachingTransformerFactoryTest extends Specification {
    TransformerFactory delegate = Mock()
    def cachingTransformerFactory = new CachingTransformerFactory(delegate, new CacheBuilder().recordStats())

    def "default constructor throws IllegalArgumentException when system property delegate class is not set"() {
        given:
        System.getProperties().remove("com.github.cachingtransformerfactory.CachingTransformerFactory.delegate")

        when:
        new CachingTransformerFactory()

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "System property is not set: com.github.cachingtransformerfactory.CachingTransformerFactory.delegate"
    }

    def "default constructor throws RuntimeException when system property delegate class does not exist"() {
        given:
        System.setProperty("com.github.cachingtransformerfactory.CachingTransformerFactory.delegate", "not.a.package.NotAClass")

        when:
        new CachingTransformerFactory()

        then:
        def e = thrown(RuntimeException)
        e.cause instanceof ClassNotFoundException
    }

    def "default constructor throws ClassCastException when system property delegate class is not a TransformerFactory"() {
        given:
        System.setProperty("com.github.cachingtransformerfactory.CachingTransformerFactory.delegate", "java.lang.String")

        when:
        new CachingTransformerFactory()

        then:
        thrown(ClassCastException)
    }

    def "default constructor rejects an invalid cache spec from the system property with an IllegalArgumentException"() {
        given:
        System.getProperties().setProperty("com.github.cachingtransformerfactory.CachingTransformerFactory.delegate",
                TransformerFactoryImpl.class.name)
        System.setProperty("com.github.cachingtransformerfactory.CachingTransformerFactory.cache", "jada jada jada")

        when:
        new CachingTransformerFactory()

        then:
        def e = thrown(IllegalArgumentException)
        e.message.startsWith("unknown key")
    }


    def "newTransformer(StreanSource) uses a cached template for the same source"() {
        given:
        def source = new StreamSource(new ClassPathResource("example.xsl").file);
        Templates template = Mock()
        Transformer transformer1 = Mock()
        Transformer transformer2 = Mock()

        when:
        def result1 = cachingTransformerFactory.newTransformer(source)
        def result2 = cachingTransformerFactory.newTransformer(source)

        then:
        1 * delegate.newTemplates(source) >> template
        2 * template.newTransformer() >>> [transformer1, transformer2]
        result1 == transformer1
        result2 == transformer2
        cachingTransformerFactory.stats().requestCount() == 2
        cachingTransformerFactory.stats().missCount() == 1
        cachingTransformerFactory.stats().hitCount() == 1
        cachingTransformerFactory.templateCache.size() == 1
    }

    def "newTransformer() just delegates"() {
        given:
        Transformer transformer = Mock()

        when:
        def result = cachingTransformerFactory.newTransformer()

        then:
        1 * delegate.newTransformer() >> transformer
        result == transformer
        cachingTransformerFactory.templateCache.size() == 0
    }

    def "newTemplates() call is not cached when the source is not a stream source"() {
        given:
        Source source = Mock()
        Templates template = Mock()

        when:
        def result = cachingTransformerFactory.newTemplates(source)

        then:
        1 * delegate.newTemplates(source) >> template
        result == template
        cachingTransformerFactory.templateCache.size() == 0
    }

    def "newTemplates() call is not cached when the stream source doesn't have a systemId"() {
        given:
        def source = new StreamSource(new ClassPathResource("example.xsl").inputStream)
        Templates template = Mock()

        when:
        def result = cachingTransformerFactory.newTemplates(source)

        then:
        1 * delegate.newTemplates(source) >> template
        result == template
        cachingTransformerFactory.templateCache.size() == 0
    }

    def "newTemplates() call is cached when the stream source has a systemId"() {
        given:
        def source = new StreamSource(new ClassPathResource("example.xsl").file)
        Templates template = Mock()

        when:
        def result = cachingTransformerFactory.newTemplates(source)

        then:
        1 * delegate.newTemplates(source) >> template
        result == template
        cachingTransformerFactory.templateCache.size() == 1
    }
}
