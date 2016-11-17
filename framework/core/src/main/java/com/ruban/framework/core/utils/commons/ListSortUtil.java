package com.ruban.framework.core.utils.commons;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 排序工具类，倒序，正序
 * 
 * @author ruban
 *
 * @param <E>
 */
public class ListSortUtil<E> {

    private static Logger logger = LoggerFactory.getLogger(ListSortUtil.class);

    /**
     * 对list的对象进行排序，method为排序依据
     * 
     * @param list
     * @param method
     */
    public static <E> void sort(List<E> list, final String method) {
        ListSortUtil.sort(list, method, null);
    }

    /**
     * 对list的对象进行排序，method为排序依据
     * 
     * @param list
     * @param method
     */
    public static <E> void reverseSort(List<E> list, final String method) {
        ListSortUtil.sort(list, method, "desc");
    }

    /**
     * 对list中的对象进行排序，method为排序的依据方法
     * 
     * sort为desc
     * 
     * @param list
     * @param method
     * @param sort
     */
    public static <E> void sort(List<E> list, final String method, final String sort) {
        Comparator<E> comparator = new Comparator<E>() {

            public int compare(E a, E b) {
                int ret = 0;

                try {
                    Method m1 = (a).getClass().getMethod(method);
                    Method m2 = (b).getClass().getMethod(method);
                    Object valueB = (Object)m2.invoke(b);
                    Object valueA = (Object)m1.invoke(a);

                    if (sort != null && "desc".equals(sort)) {
                        // 倒序
                        ret = (int) ((Comparable<Object>) valueB).compareTo(valueA);
                    } else {
                        // 正序
                        ret = (int) ((Comparable<Object>) valueA).compareTo(valueB);
                    }
                } catch (NoSuchMethodException ne) {
                    logger.error("NoSuchMethod", ne);
                } catch (IllegalAccessException ie) {
                    logger.error("IllegalAccess", ie);
                } catch (InvocationTargetException it) {
                    logger.error("InvocationTarget", it);
                }

                return ret;
            }
        };

        Collections.sort(list, comparator);
    }

}