# CachingTransformerFactory

A Java XSLT TransformerFactory which caches Templates objects using Guava Cache. 

Delegates to any`TransformerFactory`. 

By switching to this transformer you can get a significant performance boost to your XSLT transforms usually without any code changes.

The cache only operates on StreamSource objects with a SystemId set, it will cache out-of-the-box for StreamSource objects created from files. To enable caching from StreamSource objects created from an InputStream / Reader set StreamSource.systemId to a unique value for that stylesheet (examples below).

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

## Singleton Transformer Factory

When using`CachingTransformerFactory`each call to`TransformerFactory.newInstance()`returns a new instance with a private cache. If it is desirable to have a shared stylesheet cache for all`TransformerFactory`instances, use`SingletonCachingTransformerFactory`:

    -Djavax.xml.transform.TransformerFactory=com.github.cachingtransformerfactory.SingletonCachingTransformerFactory    

To access the single shared instance of`CachingTransformerFactory`, invoke`SingletonCachingTransformerFactory.getInstance()`.  

## Cache Stats

To programatically get cache statistics from`CachingTransformerFactory`, first enable statistic recording by setting the property:

    -Dcom.github.cachingtransformerfactory.CachingTransformerFactory.stats=true
    
Then access a Guava[`CacheStats`](http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/cache/CacheStats.html) object:

    CachingTransformerFactory factory = (CachingTransformerFactory)TransformerFactory.newInstance();  
    CacheStats stats = factory.stats();
    System.out.println("request count" + stats.requestCount());
    System.out.println("hit count" + stats.hitCount());
    

