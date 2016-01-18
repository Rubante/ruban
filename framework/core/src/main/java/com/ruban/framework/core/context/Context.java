package com.ruban.framework.core.context;

import java.util.Collection;

/**
 * 公共上下文
 * 
 * @author ruban
 *
 */
public abstract interface Context {
    public abstract Object getAttribute(String paramString);

    public abstract void setAttribute(String paramString, Object paramObject);

    public abstract void removeAttribute(String paramString);

    public abstract Collection<String> getAttributeNames();

    public abstract void clear();
}