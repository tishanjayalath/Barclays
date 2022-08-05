package lk.barclays.hostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import lk.barclays.hostelManagementSystem.bo.BOFactory;
import lk.barclays.hostelManagementSystem.bo.custom.ReserveBO;
import lk.barclays.hostelManagementSystem.bo.custom.RoomBO;
import lk.barclays.hostelManagementSystem.bo.custom.StudentBO;
import lk.barclays.hostelManagementSystem.dto.ReserveDTO;
import lk.barclays.hostelManagementSystem.dto.RoomDTO;
import lk.barclays.hostelManagementSystem.dto.StudentDTO;
import lk.barclays.hostelManagementSystem.entity.Room;
import lk.barclays.hostelManagementSystem.entity.Student;
import lk.barclays.hostelManagementSystem.util.ClearDataUtil;
import lk.barclays.hostelManagementSystem.util.RegexUtil;

import java.time.LocalDate;

public class ReserveRoomController {
    public TableView<ReserveDTO> tblReserve;
    public Label lblRoomId;
    public JFXComboBox<String> cmbRoomId;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXButton btnNew;
    public JFXComboBox<String> cmbStudentId;
    public Label lblType;
    public Label lblMonthlyRent;
    public Label lblRoomQty;
    public Label lblAvailableQty;
    public Label lblCreatedDate;
    public Label lblName;
    public Label lblAddress;
    public Label lblContact;
    public Label lblGender;
    public Label lblDob;
    public Label lblRegisteredDate;
    public JFXTextField txtKeyMoney;
    public Pane pnPayment;
    public JFXTextField txtRentPay;
    public JFXButton btnPaid;

    private final ReserveBO reserveBO = (ReserveBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.RESERVE);
    private final RoomBO roomBO = (RoomBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ROOM);
    private final StudentBO studentBO = (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.STUDENT);

    private RoomDTO selectedRoom;
    private StudentDTO selectedStudent;

    public void initialize(){
        initializeCmb();
        autoSetFromCmbs();
        autoSetFromTable();
        loadTable();

        btnSave.setOnMouseClicked(event -> {
        });
        btnDelete.setOnMouseClicked(event -> {
            deleteReserve();
        });
        btnNew.setOnMouseClicked(event -> {
            clearData();
        });
        btnPaid.setOnMouseClicked(event -> {
            makePayment();
        });
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        saveUpdateReserve();
    }
    private void deleteReserve() {
        if (reserveBO.deleteReserve(cmbRoomId.getValue()+"-"+cmbStudentId.getValue())) {
            new Alert(Alert.AlertType.INFORMATION,"Reservation Deleted!").show();
        }else
            new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();

        clearData();
        loadTable();
    }

    private void makePayment() {
        if (!txtRentPay.getText().equals("")){
            if (reserveBO.makeMonthlyPayment(selectedRoom.getRoomId()+"-"+selectedStudent.getStudentId(), Double.parseDouble(txtRentPay.getText()))) {
                new Alert(Alert.AlertType.INFORMATION,"Payment Success!").show();
            }else
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
        }else
            new Alert(Alert.AlertType.ERROR,"Enter a value to pay!").show();

        clearData();
        loadTable();
    }

    private void autoSetFromTable() {
        tblReserve.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                btnSave.setText("Update");
                btnDelete.setDisable(false);
                Student student = newValue.getStudent();
                selectedStudent = new StudentDTO(
                        student.getStudentId(),
                        student.getName(),
                        student.getAddress(),
                        student.getContact(),
                        student.getDob(),
                        student.getGender(),
                        student.getDateRegistered()
                );
                Room room = newValue.getRoom();
                selectedRoom = new RoomDTO(
                        room.getRoomId(),
                        room.getType(),
                        room.getMonthlyRental(),
                        room.getRoomsQty(),
                        room.getAvailableQty(),
                        room.getDateCreated()
                );

                setStudentLabels();
                setRoomLabels();
                cmbStudentId.setValue(String.valueOf(newValue.getStudent()));
                cmbRoomId.setValue(String.valueOf(newValue.getRoom()));
                txtKeyMoney.setText(String.valueOf(newValue.getKeyMoney()));
            }else {
                pnPayment.setVisible(false);
                btnDelete.setDisable(true);
                btnSave.setText("Reserve");
            }
        });
    }

    private void saveUpdateReserve() {
        if (cmbRoomId.getValue() != null & cmbStudentId.getValue() != null && !txtKeyMoney.getText().equals("")){
            if (btnSave.getText().equals("Book")){
                if (reserveBO.saveReserve(new ReserveDTO(
                        new Room(selectedRoom.getRoomId(), selectedRoom.getType(), selectedRoom.getMonthlyRental(), selectedRoom.getRoomsQty(), selectedRoom.getAvailableQty(), selectedRoom.getDateCreated(), null),
                        new Student(selectedStudent.getStudentId(), selectedStudent.getName(), selectedStudent.getAddress(), selectedStudent.getContact(), selectedStudent.getDob(), selectedStudent.getGender(), selectedStudent.getDateRegistered(), null),
                        LocalDate.now(),
                        Double.parseDouble(txtKeyMoney.getText()),
                        0,
                        "Not Paid"
                ))) {
                    new Alert(Alert.AlertType.INFORMATION,"Room Reserved!").show();
                }else
                    new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }else {
                if (reserveBO.updateReserve(new ReserveDTO(
                        new Room(selectedRoom.getRoomId(), selectedRoom.getType(), selectedRoom.getMonthlyRental(), selectedRoom.getRoomsQty(), selectedRoom.getAvailableQty(), selectedRoom.getDateCreated(), null),
                        new Student(selectedStudent.getStudentId(), selectedStudent.getName(), selectedStudent.getAddress(), selectedStudent.getContact(), selectedStudent.getDob(), selectedStudent.getGender(), selectedStudent.getDateRegistered(), null),
                        LocalDate.now(),
                        Double.parseDouble(txtKeyMoney.getText()),
                        0,
                        "Not Paid"
                ))) {
                    new Alert(Alert.AlertType.INFORMATION,"Reservation Updated!").show();
                }else
                    new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }
        }else
            new Alert(Alert.AlertType.ERROR,"Fill all required data!").show();

        loadTable();
        clearData();
    }

    private void clearData() {
        tblReserve.getSelectionModel().clearSelection();
        ClearDataUtil.clearLabels(lblType,lblMonthlyRent,lblRoomQty,lblAvailableQty,lblCreatedDate,lblName,lblAddress,lblContact,lblGender,lblDob,lblRegisteredDate);
        ClearDataUtil.clearCmb(cmbRoomId,cmbStudentId);
        ClearDataUtil.clearTextFields(txtKeyMoney);
    }

    private void autoSetFromCmbs() {
        cmbRoomId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                selectedRoom = roomBO.getSpecificRoom(newValue);
                setRoomLabels();
                btnSave.setDisable(false);
            }else
                btnSave.setDisable(true);
        });

        cmbStudentId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                selectedStudent = studentBO.getSpecificStudent(newValue);
                setStudentLabels();
                btnSave.setDisable(false);
            }else
                btnSave.setDisable(true);
        });
    }

    private void setStudentLabels() {
        lblName.setText(selectedStudent.getName());
        lblAddress.setText(selectedStudent.getAddress());
        lblContact.setText(selectedStudent.getContact());
        lblGender.setText(selectedStudent.getGender());
        lblDob.setText(String.valueOf(selectedStudent.getDob()));
        lblRegisteredDate.setText(String.valueOf(selectedStudent.getDateRegistered()));
    }

    private void setRoomLabels() {
        lblType.setText(selectedRoom.getType());
        lblMonthlyRent.setText(String.valueOf(selectedRoom.getMonthlyRental()));
        lblRoomQty.setText(String.valueOf(selectedRoom.getRoomsQty()));
        lblAvailableQty.setText(String.valueOf(selectedRoom.getAvailableQty()));
        lblCreatedDate.setText(String.valueOf(selectedRoom.getDateCreated()));
    }

    private void loadTable() {
        tblReserve.getItems().clear();
        tblReserve.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("room"));
        tblReserve.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("student"));
        tblReserve.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblReserve.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("keyMoney"));
        tblReserve.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("monthlyPaidTotal"));
        tblReserve.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("isPayForThisMonth"));

        tblReserve.setItems(reserveBO.getAllReserves());
    }

    private void initializeCmb() {
        ObservableList<RoomDTO> allRooms = roomBO.getAllRooms();
        for (RoomDTO room : allRooms) {
            cmbRoomId.getItems().add(room.getRoomId());
        }
        ObservableList<StudentDTO> allStudents = studentBO.getAllStudents();
        for (StudentDTO student : allStudents) {
            cmbStudentId.getItems().add(student.getStudentId());
        }
    }

    public void paidKeyMoneyOnKeyRls(KeyEvent keyEvent) {
        RegexUtil.validate(txtKeyMoney, btnSave, RegexUtil.price);
        if (keyEvent.getCode() == KeyCode.ENTER && !btnSave.isDisable()){
            saveUpdateReserve();
        }
    }

    public void paidValueOnKeyRls(KeyEvent keyEvent) {
        RegexUtil.validate(txtRentPay, btnPaid, RegexUtil.price);
        if (keyEvent.getCode() == KeyCode.ENTER && !btnPaid.isDisable()) {
            makePayment();
        }
    }


}
