CachingTransformerFactory
=========================

An Java XSLT TransformerFactory which caches Templates objects using Guava Cache. By switching to this transformer you can get a significant performance boost from caching Templates objects without any code changes.

Building
--------

    $ git clone git://github.com/sam-lewis/CachingTransformerFactory.git
    $ cd CachingTransformerFactory
    $ mvn install

Basic Usage
-----------
Add guava and CachingTransformerFactory-1.0.jar to your classpath.  
Set the system properties

    -Djavax.xml.transform.TransformerFactory=com.github.cachingtransformerfactory.CachingTransformerFactory
    -Dcom.github.cachingtransformerfactory.CachingTransformerFactory.delegate={TransformerFactory to use}

An example is:

    java -cp CachingTransformerFactory-1.0.jar:guava-13.0.jar\
    -Djavax.xml.transform.TransformerFactory=com.github.cachingtransformerfactory.CachingTransformerFactory\
    -Dcom.github.cachingtransformerfactory.CachingTransformerFactory.delegate=\
    com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl mypackage.MyApp
    