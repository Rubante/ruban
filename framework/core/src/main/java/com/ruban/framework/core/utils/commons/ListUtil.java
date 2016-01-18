package com.ruban.framework.core.utils.commons;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description User: I8800
 */
public class ListUtil {

    private final static Logger logger = LoggerFactory.getLogger(ListUtil.class);

    /**
     * list元素是对象时排序
     * 
     * @param list
     * @param method
     */
    public static void sort(List list, final String method) {
        Collections.sort(list, new Comparator() {
            public int compare(Object a, Object b) {
                int result;
                try {
                    Method m1 = a.getClass().getMethod(method);
                    Method m2 = b.getClass().getMethod(method);
                    result = m1.invoke(a).toString().compareTo(m2.invoke(b).toString());
                } catch (NoSuchMethodException ne) {
                    if (logger.isErrorEnabled()) {
                        logger.error("ListUtil sort Error.", ne);
                    }
                    throw new RuntimeException(ne);
                } catch (IllegalAccessException ie) {
                    if (logger.isErrorEnabled()) {
                        logger.error("ListUtil sort Error.", ie);
                    }
                    throw new RuntimeException(ie);
                } catch (InvocationTargetException it) {
                    if (logger.isErrorEnabled()) {
                        logger.error("ListUtil sort Error.", it);
                    }
                    throw new RuntimeException(it);
                }
                return result;
            }
        });
    }
}
