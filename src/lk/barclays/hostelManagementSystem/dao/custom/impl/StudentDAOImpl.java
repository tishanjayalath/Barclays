package lk.barclays.hostelManagementSystem.dao.custom.impl;

import lk.barclays.hostelManagementSystem.dao.custom.StudentDAO;
import lk.barclays.hostelManagementSystem.entity.Student;
import lk.barclays.hostelManagementSystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public ArrayList<Student> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Student> list = session.createQuery("FROM Student").list();

        transaction.commit();
        session.close();
        return (ArrayList<Student>) list;
    }

    @Override
    public boolean save(Student entity) {
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

        int i = session.createQuery("DELETE FROM Student WHERE studentId = :id").setParameter("id", id).executeUpdate();

        transaction.commit();
        session.close();
        return i>0;
    }

    @Override
    public boolean update(Student entity) {
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
    public Student getSpecificEntity(String id) {
        try {
            Session session = FactoryConfiguration.getInstance().getSession();
            Transaction transaction = session.beginTransaction();

            Student student = (Student) session.createQuery("FROM Student WHERE studentId = :id").setParameter("id",id).list().get(0);

            transaction.commit();
            session.close();
            return student;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
