package com.nagarro.pointofsaleapplication.utils;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * providing session factory for creating session
 * 
 * @author chetanmahajan
 *
 */
public class HibernateUtility {
    private static SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
            System.out.println("created session factory");
        } catch (HibernateException cause) {
            System.out.println("Error Creating Session Factory");
            System.out.println(cause.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
