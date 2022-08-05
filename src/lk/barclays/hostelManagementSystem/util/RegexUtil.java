package lk.barclays.hostelManagementSystem.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.paint.Paint;

import java.util.regex.Pattern;

public class RegexUtil {
    public static Pattern qty = Pattern.compile("^[0-9]{1,6}$");
    public static Pattern price = Pattern.compile("^[0-9]*(.[0-9]{2})?$");
    public static Pattern name = Pattern.compile("^[A-z ]{3,30}$");
    public static Pattern address = Pattern.compile("^[A-za-z0-9,./-]*$");
    public static Pattern contact = Pattern.compile("^[0-9]{10}$");

    public static void validate(JFXTextField tf, JFXButton btn, Pattern pattern){
        if (!pattern.matcher(tf.getText()).matches() && !tf.getText().equals("")) {
            btn.setDisable(true);
            tf.setFocusColor(Paint.valueOf("EC4451"));
        }else {
            btn.setDisable(false);
            tf.setFocusColor(Paint.valueOf("A272E9"));
        }
    }
}
