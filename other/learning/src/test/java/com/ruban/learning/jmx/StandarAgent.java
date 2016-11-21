package com.ruban.learning.jmx;

import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

public class StandarAgent {
    private MBeanServer mBeanServer = null;
    
    public StandarAgent() {
        mBeanServer = MBeanServerFactory.createMBeanServer();
    }
    
    public MBeanServer getMBeanServer(){
        return mBeanServer;
    }
    
    public ObjectName createObjectName(String name){
        ObjectName objectName = null;
        
        try{
            objectName = new ObjectName(name);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        
        return objectName;
    }
    
    private void createStandardBean(ObjectName objectName,String managedResourceClassName) {
        try{
            mBeanServer.createMBean(managedResourceClassName, objectName);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        StandarAgent agent = new StandarAgent();
        
        MBeanServer mBeanServer = agent.getMBeanServer();
        String domain = mBeanServer.getDefaultDomain();
        String managedResourceClassName = "com.ruban.learning.jmx.Car";
        ObjectName objectName = agent.createObjectName(domain=":type="+managedResourceClassName);
        
        agent.createStandardBean(objectName, managedResourceClassName);
        
        try{
            mBeanServer.invoke(objectName,"drive",null,null);
            Attribute attribute = new Attribute("Color","blue");
            mBeanServer.setAttribute(objectName, attribute);
            mBeanServer.invoke(objectName, "drive", null,null);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
