package lk.barclays.hostelManagementSystem.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationUtil {
    public static void setChildApn(AnchorPane apn, String location) throws IOException {
        apn.getChildren().clear();
        apn.getChildren().add(FXMLLoader.load(NavigationUtil.class.getResource("../view/"+location+".fxml")));
    }

    public static void replaceApn(AnchorPane apn, String location) throws IOException {
        Stage stage = (Stage) apn.getScene().getWindow();
        Parent root = FXMLLoader.load(NavigationUtil.class.getResource("../view/"+location+".fxml"));
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    public static void newApn(String location) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(NavigationUtil.class.getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();
        stage.show();
    }

    public static void closeApn(AnchorPane apn){
        Stage stage = (Stage) apn.getScene().getWindow();
        stage.close();
    }
}
