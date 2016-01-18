package com.ruban.framework.core.context;

import java.util.Collection;

public abstract class AbstractApplicationContextInit implements InitPlugIn {
    private ApplicationContextImpl context = (ApplicationContextImpl) ApplicationContextManager.getContext();

    protected Object getAttribute(String key) {
        return this.context.getAttribute(key);
    }

    protected void removeAttribute(String key) {
        this.context.removeAttribute(key);
    }

    protected void setAttribute(String key, Object value) {
        this.context.setAttribute(key, value);
    }

    protected Collection<String> getAttributeNames() {
        return this.context.getAttributeNames();
    }
}
