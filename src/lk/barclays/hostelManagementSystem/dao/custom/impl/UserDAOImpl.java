package lk.barclays.hostelManagementSystem.dao.custom.impl;

import lk.barclays.hostelManagementSystem.dao.custom.UserDAO;
import lk.barclays.hostelManagementSystem.entity.User;
import lk.barclays.hostelManagementSystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public ArrayList<User> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<User> list = session.createQuery("FROM User").list();

        transaction.commit();
        session.close();
        return (ArrayList<User>) list;
    }

    @Override
    public boolean save(User entity) {
        try {
            Session session = FactoryConfiguration.getInstance().getSession();
            Transaction transaction = session.beginTransaction();

            session.save(entity);

            transaction.commit();
            session.close();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        int i = session.createQuery("DELETE FROM User WHERE userid = :id").setParameter("id", id).executeUpdate();

        transaction.commit();
        session.close();
        return i>0;
    }

    @Override
    public boolean update(User entity) {
        try {
            Session session = FactoryConfiguration.getInstance().getSession();
            Transaction transaction = session.beginTransaction();

            session.update(entity);

            transaction.commit();
            session.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getSpecificEntity(String id) {
        return null;
    }
}
