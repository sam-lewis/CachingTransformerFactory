package com.github.cachingtransformerfactory

import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl
import org.junit.Test
import org.springframework.core.io.ClassPathResource

import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

class MicroBenchmarkTest {
    def iterations = 500

    @Test
    void transformWithoutCache() {
        System.setProperty("javax.xml.transform.TransformerFactory", TransformerFactoryImpl.class.name)

        benchmark(iterations)
    }

    @Test
    void transformWithCache() {
        System.setProperty("javax.xml.transform.TransformerFactory", CachingTransformerFactory.class.name)
        System.setProperty("com.github.cachingtransformerfactory.CachingTransformerFactory.delegate", TransformerFactoryImpl.class.name)

        benchmark(iterations)
    }

    void benchmark(int iterations) {
        def start = System.currentTimeMillis();

        def xml = new ClassPathResource("example.xml").file
        def xsl = new ClassPathResource("example.xsl").file
        def transformerFactory = TransformerFactory.newInstance()

        iterations.times {
            transform(xml, xsl, transformerFactory)
        }
        def elapsed = System.currentTimeMillis() - start;
        def className = transformerFactory.class.name

        println "$className, iterations $iterations: $elapsed ms"
    }

    void transform(def xml, def xsl, TransformerFactory transformerFactory) {
        def xmlSource = new StreamSource(xml)
        def xslSource = new StreamSource(xsl)

        def result = new StringWriter()

        transformerFactory.newTransformer(xslSource).transform(xmlSource, new StreamResult(result))
    }

}
