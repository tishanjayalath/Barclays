package lk.barclays.hostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.barclays.hostelManagementSystem.bo.BOFactory;
import lk.barclays.hostelManagementSystem.bo.custom.RoomBO;
import lk.barclays.hostelManagementSystem.dto.RoomDTO;
import lk.barclays.hostelManagementSystem.util.ClearDataUtil;
import lk.barclays.hostelManagementSystem.util.RegexUtil;

import java.time.LocalDate;

public class RoomFormController {
    public TableView<RoomDTO> tblRoom;
    public Label lblRoomId;
    public JFXTextField txtMonthlyRent;
    public JFXTextField txtRoomQty;
    public JFXComboBox<String> cmbType;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXButton btnNew;

    private final RoomBO roomBO = (RoomBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ROOM);
    public JFXButton btnUpdate;

    public void initialize(){
        generateId();
        loadTable();
        initializeCmb();
        autoFillData();

        btnSave.setOnMouseClicked(event -> {
//            saveUpdateRoom();
        });
        btnDelete.setOnMouseClicked(event -> {
            deleteRoom();
        });
        btnNew.setOnMouseClicked(event -> {
            clearData();
            generateId();
            btnSave.setText("Save");
        });
    }

    private void deleteRoom() {
        if (roomBO.deleteRoom(lblRoomId.getText())) {
            new Alert(Alert.AlertType.INFORMATION, "Room Deleted!").show();
        }else
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();

        clearData();
        loadTable();
    }

    private void clearData() {
        tblRoom.getSelectionModel().clearSelection();
        ClearDataUtil.clearCmb(cmbType);
        ClearDataUtil.clearTextFields(txtMonthlyRent,txtRoomQty);
    }

    private void autoFillData() {
        btnDelete.setDisable(true);
        tblRoom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                btnDelete.setDisable(false);
                cmbType.setValue(newValue.getType());
                txtMonthlyRent.setText(String.valueOf(newValue.getMonthlyRental()));
                txtRoomQty.setText(String.valueOf(newValue.getRoomsQty()));
                lblRoomId.setText(newValue.getRoomId());
            }else {
                btnDelete.setDisable(true);
                btnSave.setText("Save");
            }
        });
    }

    private void initializeCmb() {
        cmbType.getItems().add("Non-AC");
        cmbType.getItems().add("Non-AC | Food");
        cmbType.getItems().add("AC");
        cmbType.getItems().add("AC | Food");
    }

    private void loadTable() {
        tblRoom.getItems().clear();
        tblRoom.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("roomId"));
        tblRoom.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("type"));
        tblRoom.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("monthlyRental"));
        tblRoom.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("roomsQty"));
        tblRoom.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("availableQty"));
        tblRoom.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("dateCreated"));

        tblRoom.setItems(roomBO.getAllRooms());
    }

    private void generateId() {
        lblRoomId.setText(roomBO.generateNextRoomId());
    }

    public void monthlyRentOKR(KeyEvent keyEvent) {
        RegexUtil.validate(txtMonthlyRent, btnSave, RegexUtil.price);
        if (keyEvent.getCode() == KeyCode.ENTER && !btnSave.isDisable())
            txtRoomQty.requestFocus();
    }

    public void roomQtyOKR(KeyEvent keyEvent) {
        RegexUtil.validate(txtRoomQty, btnSave, RegexUtil.qty);
//        if (keyEvent.getCode() == KeyCode.ENTER && !btnSave.isDisable())
//            saveUpdateRoom();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (!txtRoomQty.getText().equals("") && !txtMonthlyRent.getText().equals("") && cmbType.getValue() != null){
                if (roomBO.saveRoom(new RoomDTO(
                        lblRoomId.getText(),
                        cmbType.getValue(),
                        Double.parseDouble(txtMonthlyRent.getText()),
                        Integer.parseInt(txtRoomQty.getText()),
                        Integer.parseInt(txtRoomQty.getText()),
                        LocalDate.now()
                ))) {
                    new Alert(Alert.AlertType.INFORMATION, "Room Saved!").show();
                }else
                    new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
        }else
            new Alert(Alert.AlertType.ERROR,"Some fields empty!").show();

        initialize();
        clearData();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        if (roomBO.updateRoom(new RoomDTO(
                lblRoomId.getText(),
                cmbType.getValue(),
                Double.parseDouble(txtMonthlyRent.getText()),
                Integer.parseInt(txtRoomQty.getText()),
                Integer.parseInt(txtRoomQty.getText()),
                LocalDate.now()
        ))) {
            new Alert(Alert.AlertType.INFORMATION, "Room Updated!").show();
        }else
            new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
    }
    }
