package hibernate_test.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Test {

    public Test(){

        try (SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Something.class)
                .buildSessionFactory()) {
            Session session = factory.getCurrentSession();
            Test test = new Test();
            session.beginTransaction();
            session.save(test);
            session.getTransaction().commit();
        }


    }
}
