package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;


import java.awt.event.ActionEvent;
import java.io.IOException;

public class UserDashBoardFormController {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPassword;

    @FXML
    private JFXTextField txtConfirm;

    public void submitButtonOnAction(javafx.event.ActionEvent actionEvent) {

    }

    public void backButtonOnAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardForm.fxml"))));
            stage.setTitle("Dashboard ");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
