package lk.barclays.hostelManagementSystem.bo;

import lk.barclays.hostelManagementSystem.bo.custom.impl.ReserveBOImpl;
import lk.barclays.hostelManagementSystem.bo.custom.impl.RoomBOImpl;
import lk.barclays.hostelManagementSystem.bo.custom.impl.StudentBOImpl;
import lk.barclays.hostelManagementSystem.bo.custom.impl.UserBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getInstance(){
        return boFactory == null? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        STUDENT, ROOM, RESERVE, USER
    }

    public SuperBO getBO(BOTypes boTypes){
        switch (boTypes){
            case ROOM: return new RoomBOImpl();
            case RESERVE: return new ReserveBOImpl();
            case STUDENT: return new StudentBOImpl();
            case USER: return new UserBOImpl();
            default: return null;
        }
    }
}
