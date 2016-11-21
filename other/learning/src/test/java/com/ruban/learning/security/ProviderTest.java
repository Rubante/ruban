package com.ruban.learning.security;

import java.security.Provider;
import java.security.Security;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.Test;

public class ProviderTest {

    @Test
    public void testProvider() {
        Provider[] providers = Security.getProviders();
        System.out.println("all provider:");
        for (Provider provider : providers) {
            System.out.println(provider);
        }
    }

    @Test
    public void testProviderFilter() {
        Provider[] providers = Security.getProviders();

        System.out.println("filter provider:");
        for (Provider provider : providers) {
            System.out.println(provider);
            Set<Map.Entry<Object, Object>> entry = provider.entrySet();
            Iterator<Entry<Object, Object>> iterator = entry.iterator();

            while (iterator.hasNext()) {
                Entry<Object, Object> ele = iterator.next();
                System.out.println("\t" + ele.getKey());
            }
        }
    }
}
