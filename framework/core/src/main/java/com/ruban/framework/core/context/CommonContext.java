package com.ruban.framework.core.context;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 公共上下文实现类
 * 
 * @author ruban
 *
 */
public abstract class CommonContext implements Context {
    private Map<String, Object> map = new HashMap<String, Object>();

    public Object getAttribute(String key) {
        return this.map.get(key);
    }

    public void setAttribute(String key, Object value) {
        this.map.put(key, value);
    }

    public void removeAttribute(String key) {
        this.map.remove(key);
    }

    public Collection<String> getAttributeNames() {
        return this.map.keySet();
    }

    public void clear() {
        this.map.clear();
    }
}