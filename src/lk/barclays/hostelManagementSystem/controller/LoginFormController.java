package lk.barclays.hostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import lk.barclays.hostelManagementSystem.bo.BOFactory;
import lk.barclays.hostelManagementSystem.bo.custom.UserBO;
import lk.barclays.hostelManagementSystem.dto.UserDTO;
import lk.barclays.hostelManagementSystem.util.ClearDataUtil;
import lk.barclays.hostelManagementSystem.util.NavigationUtil;
import lk.barclays.hostelManagementSystem.util.RegexUtil;
import lombok.SneakyThrows;

import java.io.IOException;
import java.time.LocalDate;

public class LoginFormController {
    public AnchorPane apnMain;
    public AnchorPane apnWelcome;
    public AnchorPane apnReg;
    public JFXTextField txtUsernameReg;
    public JFXButton btnRegister;
    public Label lblUserId;
    public AnchorPane apnLogin;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public JFXButton btnLogin;
    public JFXButton btnLoginOnReg;
    public Label lblRegister;
    public JFXPasswordField pwPassword;
    public ImageView imgShowPw;
    public JFXPasswordField pwPasswordReg;
    public JFXPasswordField pwConfirmPasswordReg;

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);

    public void initialize(){
        welcomeTransition();
        generateId();
        btnLogin.setDisable(true);

        imgShowPw.setOnMouseClicked(event -> {
            showPassword();
        });

        btnLogin.setOnMouseClicked(event -> {
            login();
        });
        btnRegister.setOnMouseClicked(event -> {
            register();
        });
        lblRegister.setOnMouseClicked(event -> {
            apnReg.toFront();
            apnLogin.toBack();
            clearData();
        });
        btnLoginOnReg.setOnMouseClicked(event -> {
            apnLogin.toFront();
            clearData();
        });
    }

    private void generateId() {
        lblUserId.setText(userBO.generateNextUserId());
    }

    private void clearData() {
        ClearDataUtil.clearTextFields(txtUserName,txtPassword,txtUsernameReg);
        pwPassword.setText("");
        pwPasswordReg.setText("");
        pwConfirmPasswordReg.setText("");
    }

    private void showPassword() {
        if (pwPassword.isVisible()){
            pwPassword.setVisible(false);
            txtPassword.setVisible(true);
        }else {
            pwPassword.setVisible(true);
            txtPassword.setVisible(false);
        }
    }

    private void welcomeTransition() {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1),apnWelcome);
        fadeOut.setFromValue(1);
        fadeOut.setDelay(Duration.seconds(1.3));
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);
        fadeOut.play();
        fadeOut.setOnFinished(event -> {
            apnWelcome.setDisable(true);
        });
    }

    private void register() {
        if (userBO.saveUser(new UserDTO(
                lblUserId.getText(),
                txtUsernameReg.getText(),
                pwPasswordReg.getText(),
                LocalDate.now()
        ))) {
            new Alert(Alert.AlertType.INFORMATION,"Registration Successfull!").show();
            clearData();
        }else
            new Alert(Alert.AlertType.ERROR,"OOps! Something went wrong").show();
    }

    @SneakyThrows
    private void login() {
        UserDTO userDTO = userBO.checkUserExists(txtUserName.getText());
        if (userDTO == null) {
            new Alert(Alert.AlertType.ERROR,"User not Found!").show();
        }else {
            if (userDTO.getPassword().equals(txtPassword.getText())) {
                MainFormController.userDTO = userDTO;
                NavigationUtil.replaceApn(apnLogin,"MainForm");
            }else
                new Alert(Alert.AlertType.ERROR,"Password Incorrect!").show();
        }
//        NavigationUtil.replaceApn(apnLogin,"MainForm");
    }

    public void usernameOnKeyRls(KeyEvent keyEvent) {
        RegexUtil.validate(txtUserName, btnLogin, RegexUtil.name);
        if (keyEvent.getCode() == KeyCode.ENTER && !btnLogin.isDisable()) {
            pwPassword.requestFocus();
        }
    }

    public void passwordOnKeyRls(KeyEvent keyEvent) throws IOException {
        txtPassword.setText(pwPassword.getText());
        if (keyEvent.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    public void userNameRegOKR(KeyEvent keyEvent) {
        RegexUtil.validate(txtUsernameReg, btnLoginOnReg, RegexUtil.name);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            pwPasswordReg.requestFocus();
        }
    }

    public void pwRegOKR(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            pwConfirmPasswordReg.requestFocus();
        }
    }

    public void confirmPwOKR(KeyEvent keyEvent) {
        if (!pwPasswordReg.getText().equals(pwConfirmPasswordReg.getText())){
            btnRegister.setDisable(true);
            pwConfirmPasswordReg.setFocusColor(Paint.valueOf("EC4451"));
        }else {
            btnRegister.setDisable(false);
            pwConfirmPasswordReg.setFocusColor(Paint.valueOf("A272E9"));
        }
        if (keyEvent.getCode() == KeyCode.ENTER){
            register();
        }
    }
}
