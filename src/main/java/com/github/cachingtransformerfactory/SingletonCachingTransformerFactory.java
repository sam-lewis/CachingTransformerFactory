package com.github.cachingtransformerfactory;

import javax.xml.transform.*;

/**
 * Usage java -Djavax.xml.transform.TransformerFactory=com.github.cachingtransformerfactory.SingletonCachingTransformerFactory
 */
public final class SingletonCachingTransformerFactory extends TransformerFactory {

    public static final CachingTransformerFactory INSTANCE = new CachingTransformerFactory();

    public static CachingTransformerFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public Transformer newTransformer(Source source) throws TransformerConfigurationException {
        return getInstance().newTransformer();
    }

    @Override
    public Transformer newTransformer() throws TransformerConfigurationException {
        return getInstance().newTransformer();
    }

    @Override
    public Templates newTemplates(Source source) throws TransformerConfigurationException {
        return getInstance().newTemplates(source);
    }

    @Override
    public Source getAssociatedStylesheet(Source source, String media, String title, String charset) throws TransformerConfigurationException {
        return getInstance().getAssociatedStylesheet(source, media, title, charset);
    }

    @Override
    public void setURIResolver(URIResolver resolver) {
        getInstance().setURIResolver(resolver);
    }

    @Override
    public URIResolver getURIResolver() {
        return getInstance().getURIResolver();
    }

    @Override
    public void setFeature(String name, boolean value) throws TransformerConfigurationException {
        getInstance().setFeature(name, value);
    }

    @Override
    public boolean getFeature(String name) {
        return getInstance().getFeature(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        getInstance().setAttribute(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return getInstance().getAttribute(name);
    }

    @Override
    public void setErrorListener(ErrorListener listener) {
        getInstance().setErrorListener(listener);
    }

    @Override
    public ErrorListener getErrorListener() {
        return getInstance().getErrorListener();
    }
}
