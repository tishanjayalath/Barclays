package lk.barclays.hostelManagementSystem.view.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentNotPaidTM {
    private String studentId;
    private String roomId;
    private String name;
}
