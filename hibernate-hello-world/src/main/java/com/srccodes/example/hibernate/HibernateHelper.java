package com.srccodes.example.hibernate;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateHelper {

	private static SessionFactory sessionFactory;
    
	
	static {
		Configuration configuration = new Configuration(); 
        configuration.configure(); 
         
        Properties properties = configuration.getProperties();
         
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();         
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	
	
    public static SessionFactory getSessionFactory() {
    	return sessionFactory; 
    }
	
}
