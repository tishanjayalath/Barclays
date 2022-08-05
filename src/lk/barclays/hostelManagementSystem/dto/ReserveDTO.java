package lk.barclays.hostelManagementSystem.dto;

import lk.barclays.hostelManagementSystem.entity.Room;
import lk.barclays.hostelManagementSystem.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReserveDTO {
    private Room room;
    private Student student;
    private LocalDate date;
    private double keyMoney;
    private double monthlyPaidTotal;
    private String isPayForThisMonth;
}
