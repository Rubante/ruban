package com.ruban.framework.core.utils.commons;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description User: I8800
 */
public class CloneUtil implements Serializable {

    private final static Logger logger = LoggerFactory.getLogger(CloneUtil.class);

    private static final long serialVersionUID = 0x2022eebf8b69dee4L;

    public static Object clone(Object object) {
        Object objClone;
        try {
            objClone = shallowClone(object);
        } catch (Exception e) {
            if (logger.isWarnEnabled()) {
                logger.warn("Not Found clone method.");
            }
            try {
                objClone = SerializeClone(object);
            } catch (Exception ex) {
                if (logger.isErrorEnabled()) {
                    logger.error("Both ShallowClone and SerializedClone error.", ex);
                }
                throw new RuntimeException(ex);
            }
        }
        return objClone;
    }

    /**
     * 使用序列化产生一个克隆副本
     * 
     * @param object
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object SerializeClone(Object object) throws IOException, ClassNotFoundException {
        if (logger.isDebugEnabled()) {
            logger.debug("CloneUtil SerializeClone an Entity");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Object clonedObj;
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        objectOutputStream.close();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        clonedObj = objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();

        byteArrayOutputStream.close();
        return clonedObj;
    }

    /**
     * 使用反射调用对象中的clone方法产生一个副本
     * 
     * @param obj
     * @return
     */
    public static Object shallowClone(Object obj) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        if (logger.isDebugEnabled()) {
            logger.debug("CloneUtil shallowClone an Entity");
        }
        String cloneMethod = "clone";
        Class<?> clazz = obj.getClass();
        Method method = clazz.getMethod(cloneMethod);
        method.setAccessible(true);
        Object objClone = method.invoke(obj);
        return objClone;
    }
}
