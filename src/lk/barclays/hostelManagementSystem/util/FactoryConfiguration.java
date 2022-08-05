package lk.barclays.hostelManagementSystem.util;

import lk.barclays.hostelManagementSystem.entity.Reserve;
import lk.barclays.hostelManagementSystem.entity.Room;
import lk.barclays.hostelManagementSystem.entity.Student;
import lk.barclays.hostelManagementSystem.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private final SessionFactory sessionFactory;

    private FactoryConfiguration(){
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("hibernate.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        sessionFactory = new Configuration().mergeProperties(properties)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Room.class)
                .addAnnotatedClass(Reserve.class)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

    }

    public static FactoryConfiguration getInstance(){
        return factoryConfiguration==null? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }
}
