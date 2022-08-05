package lk.barclays.hostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.barclays.hostelManagementSystem.bo.BOFactory;
import lk.barclays.hostelManagementSystem.bo.custom.StudentBO;
import lk.barclays.hostelManagementSystem.dto.StudentDTO;
import lk.barclays.hostelManagementSystem.util.ClearDataUtil;
import lk.barclays.hostelManagementSystem.util.RegexUtil;

import java.time.LocalDate;

public class StudentFormController {
    private final StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.STUDENT);
    public TableView<StudentDTO> tblStudent;
    public Label lblStudentId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXDatePicker txtDob;
    public JFXRadioButton radMale;
    public ToggleGroup gender;
    public JFXRadioButton radFemale;
    public JFXRadioButton radOther;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXButton btnNew;
    public JFXButton btnUpdate;

    public void initialize() {
        generateId();
        loadTable();
        autoFillData();

        btnSave.setOnMouseClicked(event -> {
            saveUpdateStudent();
        });
        btnDelete.setOnMouseClicked(event -> {
            deleteStudent();
        });
        btnNew.setOnMouseClicked(event -> {
            clearData();
        });
    }

    private void autoFillData() {
        btnDelete.setDisable(true);
        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btnDelete.setDisable(false);
                lblStudentId.setText(newValue.getStudentId());
                txtName.setText(newValue.getName());
                txtContact.setText(newValue.getContact());
                txtAddress.setText(newValue.getAddress());
                txtDob.setValue(newValue.getDob());
                switch (newValue.getGender()) {
                    case "Male":
                        radMale.setSelected(true);
                        break;
                    case "Female":
                        radFemale.setSelected(true);
                        break;
                    case "Other":
                        radOther.setSelected(true);
                }
            } else {
                btnDelete.setDisable(true);
                btnSave.setText("Save");
            }
        });
    }

    private void deleteStudent() {
        if (studentBO.deleteStudent(lblStudentId.getText())) {
            new Alert(Alert.AlertType.INFORMATION, "Student Deleted!").show();
        } else
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();

        loadTable();
        clearData();
    }

    private void saveUpdateStudent() {
        if (!txtName.getText().equals("") && !txtAddress.getText().equals("") && !txtContact.getText().equals("") && txtDob.getValue() != null) {
//            if (btnSave.getText().equals("Save")) {
                if (studentBO.saveStudent(new StudentDTO(
                        lblStudentId.getText(),
                        txtName.getText(),
                        txtAddress.getText(),
                        txtContact.getText(),
                        txtDob.getValue(),
                        ((RadioButton) gender.getSelectedToggle()).getText(),

                        LocalDate.now()
                ))) {
                    new Alert(Alert.AlertType.INFORMATION, "Student Saved!").show();
                } else
                    new Alert(Alert.AlertType.ERROR, "Something Went Wrong!").show();
//            } else {
//                if (studentBO.UpdateStudent(new StudentDTO(
//                        lblStudentId.getText(),
//                        txtName.getText(),
//                        txtAddress.getText(),
//                        txtContact.getText(),
//                        txtDob.getValue(),
//                        ((RadioButton) gender.getSelectedToggle()).getText(),
//                        LocalDate.now()
//                ))) {
//                    new Alert(Alert.AlertType.INFORMATION, "Student Updated!").show();
//                } else
//                    new Alert(Alert.AlertType.ERROR, "Something Went Wrong!").show();
//            }
        } else
            new Alert(Alert.AlertType.ERROR, "Fill all required data!").show();

        initialize();
        clearData();

    }

    private void clearData() {
        ClearDataUtil.clearTextFields(txtAddress, txtName, txtContact);
        tblStudent.getSelectionModel().clearSelection();
        txtDob.getEditor().clear();
    }

    private void loadTable() {
        tblStudent.getItems().clear();
        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("studentId"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblStudent.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblStudent.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("dob"));
        tblStudent.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("gender"));
        tblStudent.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("dateRegistered"));

        tblStudent.setItems(studentBO.getAllStudents());
    }

    private void generateId() {
        lblStudentId.setText(studentBO.generateNextStudentId());
    }

    public void nameOKR(KeyEvent keyEvent) {
        RegexUtil.validate(txtName, btnSave, RegexUtil.name);
        if (keyEvent.getCode() == KeyCode.ENTER && !btnSave.isDisable())
            txtAddress.requestFocus();
    }

    public void addressOKR(KeyEvent keyEvent) {
        RegexUtil.validate(txtAddress, btnSave, RegexUtil.address);
        if (keyEvent.getCode() == KeyCode.ENTER && !btnSave.isDisable())
            txtContact.requestFocus();
    }

    public void contactOKR(KeyEvent keyEvent) {
        RegexUtil.validate(txtContact, btnSave, RegexUtil.contact);
        if (keyEvent.getCode() == KeyCode.ENTER && !btnSave.isDisable())
            txtDob.requestFocus();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
//        if (!txtName.getText().equals("") && !txtAddress.getText().equals("") && !txtContact.getText().equals("") && txtDob.getValue() != null) {
            if (studentBO.UpdateStudent(new StudentDTO(
                    lblStudentId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtContact.getText(),
                    txtDob.getValue(),
                    ((RadioButton) gender.getSelectedToggle()).getText(),
                    LocalDate.now()
            ))) {
                new Alert(Alert.AlertType.INFORMATION, "Student Updated!").show();
            } else
                new Alert(Alert.AlertType.ERROR, "Something Went Wrong!").show();

        initialize();
        clearData();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (!txtName.getText().equals("") && !txtAddress.getText().equals("") && !txtContact.getText().equals("") && txtDob.getValue() != null) {
//            if (btnSave.getText().equals("Save")) {
            if (studentBO.saveStudent(new StudentDTO(
                    lblStudentId.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtContact.getText(),
                    txtDob.getValue(),
                    ((RadioButton) gender.getSelectedToggle()).getText(),

                    LocalDate.now()
            ))) {
                new Alert(Alert.AlertType.INFORMATION, "Student Saved!").show();
            } else
                new Alert(Alert.AlertType.ERROR, "Something Went Wrong!").show();
//            } else {
//                if (studentBO.UpdateStudent(new StudentDTO(
//                        lblStudentId.getText(),
//                        txtName.getText(),
//                        txtAddress.getText(),
//                        txtContact.getText(),
//                        txtDob.getValue(),
//                        ((RadioButton) gender.getSelectedToggle()).getText(),
//                        LocalDate.now()
//                ))) {
//                    new Alert(Alert.AlertType.INFORMATION, "Student Updated!").show();
//                } else
//                    new Alert(Alert.AlertType.ERROR, "Something Went Wrong!").show();
//            }
        } else
            new Alert(Alert.AlertType.ERROR, "Fill all required data!").show();

        initialize();
        clearData();
    }
}
