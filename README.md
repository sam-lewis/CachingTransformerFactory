CachingTransformerFactory
=========================

An Java XSLT TransformerFactory which caches Templates objects using Guava Cache. By switching to this transformer you can get a significant performance boost from caching Templates objects without any code changes.

Building
========
$ git clone git://github.com/sam-lewis/CachingTransformerFactory.git
$ cd CachingTransformerFactory
$ mvn install

Usage
=====
- Add guava and CachingTransformerFactory-1.0.jar to your classpath.
- Set the system properties:
 - javax.xml.transform.TransformerFactory=com.github.cachingtransformerfactory.CachingTransformerFactory
 - com.github.cachingtransformerfactory.CachingTransformerFactory.delegate={TransformerFactory to use}
  - e.g. com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl 
 - com.github.cachingtransformerfactory.CachingTransformerFactory.cache={guava cache spec string}
  - see http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/cache/CacheBuilderSpec.html 
