package lk.barclays.hostelManagementSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Student {
    @Id
    private String studentId;
    private String name;
    private String address;
    private String contact;
    private LocalDate dob;
    private String gender;
    private LocalDate dateRegistered;

    @OneToMany (mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Reserve> reserveList = new ArrayList<>();

    public Student(String studentId, String name, String address, String contact, LocalDate dob, String gender, LocalDate dateRegistered) {
        this.studentId = studentId;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.dob = dob;
        this.gender = gender;
        this.dateRegistered = dateRegistered;
    }

    @Override
    public String toString() {
        return studentId;
    }
}
