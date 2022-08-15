package com.project_classes.gui;

import com.project_classes.gui.util.Constraints;
import com.project_classes.model.entities.Department;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FormDepartamentosController implements Initializable {

    private Department entity;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    public void setDepartment(Department entity) {
        this.entity = entity;
    }

    @FXML
    public void onBtSaveAction() {
        System.out.println("onBtSaveAction");
    }

    @FXML
    public void onBtCancelAction() {
        System.out.println("onBtCancelAction");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializableNodes();
    }

    private void initializableNodes() {

        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);

    }

    public void updateFormData() {

        if (entity == null) {
            throw  new IllegalStateException("Entity é null");
        }

        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
    }
}
