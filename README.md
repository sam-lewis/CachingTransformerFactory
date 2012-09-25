# CachingTransformerFactory

A Java XSLT TransformerFactory which caches Templates objects using Guava Cache. 

By switching to this transformer you can get a significant performance boost from caching Templates objects.

The cache only operates on StreamSource objects with a SystemId set, it will cache out-of-the-box for StreamSource objects created from files. To enable caching from StreamSource objects created from an InputStream / Reader set StreamSource.systemId to a unique value for that stylesheet.



## Building
    $ git clone git://github.com/sam-lewis/CachingTransformerFactory.git
    $ cd CachingTransformerFactory
    $ mvn install

## Basic Usage
Add`guava-13.0.jar`and`CachingTransformerFactory-1.0.jar`to your classpath.  
Set the system properties

    -Djavax.xml.transform.TransformerFactory=com.github.cachingtransformerfactory.CachingTransformerFactory
    -Dcom.github.cachingtransformerfactory.CachingTransformerFactory.delegate={TransformerFactory to use}

An example is:

    java -cp CachingTransformerFactory-1.0.jar:guava-13.0.jar\
    -Djavax.xml.transform.TransformerFactory=com.github.cachingtransformerfactory.CachingTransformerFactory\
    -Dcom.github.cachingtransformerfactory.CachingTransformerFactory.delegate=\
    com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl mypackage.MyApp
    
## Cache Settings

By default, the cache does not perform any kind of eviction and will grow unbounded. The cache settings can be set with an additional optional system property:

    -Dcom.github.cachingtransformerfactory.CachingTransformerFactory.cache=initialCapacity=50,maximumSize=100

The cache specification string format is documented in the javadocs for Guavas[`CacheBuilderSpec`](http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/cache/CacheBuilderSpec.html)class 

## Code Samples

### Caching from Files
### Caching from InputStreams
### Caching from Readers
### Cache Stats

## Todo
* Improve xslt integration tests.
* Adding logging

