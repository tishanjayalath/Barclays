package lk.barclays.hostelManagementSystem.dao.custom.impl;

import lk.barclays.hostelManagementSystem.dao.custom.ReserveDAO;
import lk.barclays.hostelManagementSystem.entity.Reserve;
import lk.barclays.hostelManagementSystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ReserveDAOImpl implements ReserveDAO {
    @Override
    public ArrayList<Reserve> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Reserve> list = session.createQuery("FROM Reserve").list();

        transaction.commit();
        session.close();
        return (ArrayList<Reserve>) list;
    }

    @Override
    public boolean save(Reserve entity) {
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

        int i = session.createQuery("DELETE FROM Reserve WHERE reserveId = :id").setParameter("id", id).executeUpdate();

        transaction.commit();
        session.close();
        return i>0;
    }

    @Override
    public boolean update(Reserve entity) {
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
    public Reserve getSpecificEntity(String id) {
        return null;
    }

    @Override
    public boolean makeMonthlyPayment(String id, double payment) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        int i = session.createQuery(
                "UPDATE Reserve SET monthlyPaidTotal = monthlyPaidTotal+:payment, isPayForThisMonth = 'Paid' WHERE reserveId = :id")
                .setParameter("payment", payment)
                .setParameter("id", id)
                .executeUpdate();

        transaction.commit();
        session.close();
        return i>0;
    }

    @Override
    public int markAllAsNotPaid() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        int i = session.createQuery("UPDATE Reserve SET isPayForThisMonth = 'Not Paid'").executeUpdate();

        transaction.commit();
        session.close();
        return i;
    }
}
