package com.oa.jbpm.decision;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Repository;


@Repository
public class BeanFactoryHelper implements BeanFactoryAware {

	private static BeanFactory factory;  
    
    public static  BeanFactory getFactory(){  
        return factory;  
    }

    public void setBeanFactory(BeanFactory f) throws BeansException {
         factory = f;  
    }  

}
