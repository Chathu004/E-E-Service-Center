package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class CustomersFormController {
    @FXML
    private AnchorPane pane;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TableView<?> tblCustomer;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colOption;


    public void backBtnOnAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"))));
            stage.setTitle("Main ");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateBtnOnAction(javafx.event.ActionEvent actionEvent) {


    }

    public void saveBtnOnAction(javafx.event.ActionEvent actionEvent) {

    }

    public void reloadBtnOnAction(javafx.event.ActionEvent actionEvent) {

    }
}
