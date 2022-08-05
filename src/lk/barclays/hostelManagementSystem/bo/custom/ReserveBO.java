package lk.barclays.hostelManagementSystem.bo.custom;

import javafx.collections.ObservableList;
import lk.barclays.hostelManagementSystem.bo.SuperBO;
import lk.barclays.hostelManagementSystem.dto.ReserveDTO;
import lk.barclays.hostelManagementSystem.view.tm.StudentNotPaidTM;

public interface ReserveBO extends SuperBO {
    ObservableList<ReserveDTO> getAllReserves();

    boolean saveReserve(ReserveDTO reserveDTO);

    boolean updateReserve(ReserveDTO dto);

    boolean makeMonthlyPayment(String s, double parseDouble);

    int markAllAsNotPaid();

    ObservableList<StudentNotPaidTM> getStudentsNotPaidToTable();

    boolean deleteReserve(String s);
}
