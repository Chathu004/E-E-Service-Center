package controller;

import bo.BoFactory;
import bo.custom.ItemBo;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dao.custom.ItemDao;
import dao.custom.impl.ItemDaoImpl;
import dao.util.BoType;
import dto.ItemDto;
import dto.tm.ItemTm;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.function.Predicate;

public class ItemFormController {

    @FXML
    private BorderPane pane;

    @FXML
    private JFXTextField txtCode;

    @FXML
    private JFXTextField txtProduct;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXTreeTableView<ItemTm> tblItem;

    @FXML
    private TreeTableColumn<?, ?> colCode;

    @FXML
    private TreeTableColumn<?, ?> colProduct;

    @FXML
    private TreeTableColumn<?, ?> colCategory;

    @FXML
    private TreeTableColumn<?, ?> colStatus;

    @FXML
    private TreeTableColumn<?, ?> colOption;
    @FXML
    private JFXComboBox<String> cmbCategory;

    @FXML
    private JFXComboBox<String> cmbStatus;
    private ItemDao itemDao = new ItemDaoImpl();
    private ItemBo itemBo = BoFactory.getInstance().getBo(BoType.ITEM);
    private static int itemCounter = 0;

    public void initialize(){
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colProduct.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new TreeItemPropertyValueFactory<>("category"));
        colStatus.setCellValueFactory(new TreeItemPropertyValueFactory<>("status"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));
        loadItems();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                tblItem.setPredicate(new Predicate<TreeItem<ItemTm>>() {
                    @Override
                    public boolean test(TreeItem<ItemTm> treeItem) {
                        return treeItem.getValue().getCode().toLowerCase().contains(newValue.toLowerCase()) ||
                                treeItem.getValue().getName().toLowerCase().contains(newValue.toLowerCase());

                    }
                });
            }
        });
        generateItemCode();
        initializeComboBoxes();
    }

    private void generateItemCode() {
        String prefix = "I#";
        String formattedCounter = String.format("%05d", itemCounter + 1);
        String generatedCode = prefix + formattedCounter;
        txtCode.setText(generatedCode);

    }

    private void initializeComboBoxes() {
        // Populate Category ComboBox
        ObservableList<String> categoryOptions = FXCollections.observableArrayList("Electric", "Electronic");
        cmbCategory.setItems(categoryOptions);

        // Populate Status ComboBox
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Pending", "Processing","Completed");
        cmbStatus.setItems(statusOptions);
    }

    private void setData(TreeItem<ItemTm> newValue) {

        if (newValue != null) {
            txtCode.setEditable(true);
            txtCode.setText(newValue.getValue().getCode());
            txtProduct.setText(newValue.getValue().getName());
            cmbCategory.setValue(newValue.getValue().getCategory());
            cmbStatus.setValue(newValue.getValue().getStatus());


        } else {
            clearFields();
        }
    }
    private void clearFields() {
        txtCode.setEditable(true);
        txtCode.clear();
        txtProduct.clear();
        cmbCategory.getSelectionModel().clearSelection();
        cmbStatus.getSelectionModel().clearSelection();

    }
    private void loadItems() {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();


        try {
            List<ItemDto> dtoList  = itemDao.allItems();
            for (ItemDto dto:dtoList) {
                JFXButton btn = new JFXButton("Delete");

                ItemTm tm = new ItemTm(
                        dto.getCode(),
                        dto.getName(),
                        dto.getCategory(),
                        dto.getStatus(),

                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteItem(dto.getCode());
                });


                tmList.add(tm);
            }
            RecursiveTreeItem<ItemTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            tblItem.setRoot(treeItem);
            tblItem.setShowRoot(false);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteItem(String itmCode) {

            try {
                boolean isDeleted = itemBo.deleteItem(itmCode);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer Deleted!").show();
                    loadItems();
//                } else {
//                    new Alert(Alert.AlertType.ERROR, "Something went wrong!").show();
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }





    @FXML
    void backButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        ItemDto dto = new ItemDto(txtCode.getText(),
                txtProduct.getText(),
                cmbCategory.getValue(),
                cmbStatus.getValue()

        );

        try {
            boolean isSaved = itemBo.saveItem(dto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Item Saved!").show();
                clearFields();

                itemCounter++;
                generateItemCode();

                tblItem.getRoot().getChildren().clear();
                loadItems();
            }

        } catch (SQLIntegrityConstraintViolationException ex){
//            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void updateButtonOnAction(ActionEvent event) {
        ItemDto dto = new ItemDto(txtCode.getText(),
                txtProduct.getText(),
                cmbCategory.getValue(),
                cmbStatus.getValue()
        );

        try {
            boolean isUpdated = itemBo.updateItem(dto);
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Customer "+dto.getCode()+" Updated!").show();
                loadItems();
                clearFields();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


}
