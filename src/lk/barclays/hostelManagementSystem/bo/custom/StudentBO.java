package lk.barclays.hostelManagementSystem.bo.custom;

import javafx.collections.ObservableList;
import lk.barclays.hostelManagementSystem.bo.SuperBO;
import lk.barclays.hostelManagementSystem.dto.StudentDTO;

public interface StudentBO extends SuperBO {

    boolean saveStudent(StudentDTO studentDTO);

    boolean deleteStudent(String id);

    String generateNextStudentId();

    ObservableList<StudentDTO> getAllStudents();

    boolean UpdateStudent(StudentDTO dto);

    StudentDTO getSpecificStudent(String newValue);

    int getStudentCount();

    int getStudentsCountByGender(String male);
}
