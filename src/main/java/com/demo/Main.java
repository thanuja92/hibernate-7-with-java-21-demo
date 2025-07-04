package com.demo;

import com.demo.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {

        createCustomer();
        getCustomer();
        updateCustomer();
        deleteCustomer();

    }

    public static void createCustomer() {

        try (SessionFactory sessionFactory = new Configuration().addAnnotatedClass(Customer.class).configure("hibernate.cfg.xml").buildSessionFactory()) {

            try (Session session = sessionFactory.openSession()) {

                Transaction transaction = session.beginTransaction();

                Customer customer = new Customer();
                customer.setFirstName("John");
                customer.setLastName("Doe");
                customer.setContactNo("123456");

                // persist == save in Hibernate 6
                session.persist(customer);

                transaction.commit();

            }
        }
    }

    public static void getCustomer() {

        try (SessionFactory sessionFactory = new Configuration().addAnnotatedClass(Customer.class).configure("hibernate.cfg.xml").buildSessionFactory()) {

            try (Session session = sessionFactory.openSession()) {

                Customer customer = session.find(Customer.class, 1); // Eager fetching

                // Customer customer = session.byId(Customer.class).getReference(1); // Lazy fetching

                System.out.println(customer);


            }
        }
    }

    public static void updateCustomer() {

        try (SessionFactory sessionFactory = new Configuration().addAnnotatedClass(Customer.class).configure("hibernate.cfg.xml").buildSessionFactory()) {

            try (Session session = sessionFactory.openSession()) {

                Transaction transaction = session.beginTransaction();

                Customer customer = session.find(Customer.class, 1); // Eager fetching
                customer.setContactNo("00000000");

                Customer updatedCustomer = session.merge(customer);
                System.out.println(updatedCustomer);

                transaction.commit();

            }
        }
    }


    public static void deleteCustomer() {

        try (SessionFactory sessionFactory = new Configuration().addAnnotatedClass(Customer.class).configure("hibernate.cfg.xml").buildSessionFactory()) {

            try (Session session = sessionFactory.openSession()) {

                Transaction transaction = session.beginTransaction();
                Customer customer = session.find(Customer.class, 1); // Eager fetching
                session.remove(customer);

                transaction.commit();
            }
        }
    }
}