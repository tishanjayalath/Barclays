package lk.barclays.hostelManagementSystem.dao.custom.impl;

import lk.barclays.hostelManagementSystem.dao.custom.RoomDAO;
import lk.barclays.hostelManagementSystem.entity.Room;
import lk.barclays.hostelManagementSystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {

    @Override
    public ArrayList<Room> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Room> list = session.createQuery("FROM Room").list();

        transaction.commit();
        session.close();
        return (ArrayList<Room>) list;
    }

    @Override
    public boolean save(Room entity) {
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

        int i = session.createQuery("DELETE FROM Room WHERE roomId = :id").setParameter("id", id).executeUpdate();

        transaction.commit();
        session.close();
        return i>0;
    }

    @Override
    public boolean update(Room entity) {
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
    public Room getSpecificEntity(String id) {
        try {
            Session session = FactoryConfiguration.getInstance().getSession();
            Transaction transaction = session.beginTransaction();

            Room room = (Room) session.createQuery("FROM Room WHERE roomId = :id").setParameter("id",id).list().get(0);

            transaction.commit();
            session.close();
            return room;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
