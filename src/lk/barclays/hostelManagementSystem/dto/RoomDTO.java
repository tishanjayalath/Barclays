package lk.barclays.hostelManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomDTO {
    private String roomId;
    private String type;
    private double monthlyRental;
    private int roomsQty;
    private int availableQty;
    private LocalDate dateCreated;
}
