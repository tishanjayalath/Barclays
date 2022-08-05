package lk.barclays.hostelManagementSystem.dao.custom;

import lk.barclays.hostelManagementSystem.dao.CrudDAO;
import lk.barclays.hostelManagementSystem.entity.Reserve;


public interface ReserveDAO extends CrudDAO<Reserve> {
    boolean makeMonthlyPayment(String id, double payment);

    int markAllAsNotPaid();
}
