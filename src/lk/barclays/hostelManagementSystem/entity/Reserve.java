package lk.barclays.hostelManagementSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reserve implements Serializable {
    @Id
    private String reserveId;
    @ManyToOne
    private Room room;
    @ManyToOne
    private Student student;
    private LocalDate date;
    private double keyMoney;
    private double monthlyPaidTotal;
    private String isPayForThisMonth;

    public Reserve(Room room, Student student, LocalDate date, double keyMoney, double monthlyPaidTotal, String isPayForThisMonth) {
        this.reserveId = room.getRoomId() +"-"+ student.getStudentId();
        this.room = room;
        this.student = student;
        this.date = date;
        this.keyMoney = keyMoney;
        this.monthlyPaidTotal = monthlyPaidTotal;
        this.isPayForThisMonth = isPayForThisMonth;
    }
}
