package com.github.cachingtransformerfactory;

import com.google.common.base.Strings;

import javax.xml.transform.stream.StreamSource;

class StreamSourceWrapper {

    private final StreamSource delegate;

    StreamSourceWrapper(StreamSource delegate) {
        this.delegate = delegate;
    }

    public boolean isCachable() {
        return Strings.nullToEmpty(getDelegate().getSystemId()).length() > 0;
    }

    @Override
    public int hashCode() {
        if(isCachable()) {
            return getDelegate().getSystemId().hashCode();
        } else {
            return super.hashCode();
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StreamSourceWrapper)) {
            return false;
        }

        StreamSourceWrapper otherWrapper = (StreamSourceWrapper) other;
        if(isCachable() && otherWrapper.isCachable()) {
            return getDelegate().getSystemId().equals(otherWrapper.getDelegate().getSystemId());
        } else {
            return false;
        }
    }

    public StreamSource getDelegate() {
        return delegate;
    }
}
