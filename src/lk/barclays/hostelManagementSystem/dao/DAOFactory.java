package lk.barclays.hostelManagementSystem.dao;

import lk.barclays.hostelManagementSystem.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){}

    public static DAOFactory getInstance(){
        return daoFactory == null? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        QUERY, STUDENT, ROOM, RESERVE, USER
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case STUDENT: return new StudentDAOImpl();
            case ROOM: return new RoomDAOImpl();
            case RESERVE: return new ReserveDAOImpl();
            case USER: return new UserDAOImpl();
            case QUERY: return new QueryDAOImpl();
            default: return null;
        }
    }
}
