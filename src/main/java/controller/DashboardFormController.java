package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardFormController {

    @FXML
    public AnchorPane pane;

    @FXML
    private Label lblDate;

    @FXML
    public Label lblTime;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPassword;
    public void initialize(){
        calculateTime();
        updateDate();
    }

    private void updateDate() {
        lblDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    private void calculateTime() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.ZERO,
                actionEvent -> lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
        ),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }



    public void signInButtonOnAction(javafx.event.ActionEvent actionEvent) {

    }

    public void submitButtonOnAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"))));
            stage.setTitle("Main ");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void registerButtonOnAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UserDashBoardForm.fxml"))));
            stage.setTitle("User Dashboard ");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void forgotBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/PasswordForm.fxml"))));
            stage.setTitle("Password ");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
