package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgetPasswordController {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXPasswordField txtConfirm;



    @FXML
    void submitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.setTitle(" Dashboard ");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void backButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/VerificationForm.fxml"))));
        stage.setTitle(" Password ");
        stage.show();
        } catch (
        IOException e) {
        e.printStackTrace();
    }

    }
}
