package com.project_classes.gui;

import com.project_classes.db.DbException;
import com.project_classes.gui.util.Alerts;
import com.project_classes.gui.util.Constraints;
import com.project_classes.gui.util.Utils;
import com.project_classes.model.entities.Department;
import com.project_classes.model.services.ServicesDepartamento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FormDepartamentosController implements Initializable {

    private Department entity;

    private ServicesDepartamento service;

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

    public void setServiceDepartamento(ServicesDepartamento service) {
        this.service = service;
    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {

        if (entity == null) {
            throw new IllegalStateException("Entity estava null");
        }

        if (service == null) {
            throw new IllegalStateException("Service estava null");
        }

        try {
            entity = getFormData();
            service.saveOrUpdate(entity);

            Utils.currentStage(event).close();
        } catch (DbException e) {
            Alerts.showAlert("Erro ao salvar objeto", null, e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
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

    private Department getFormData() {
        Department obj = new Department();

        obj.setId(Utils.tryParseToInt(txtId.getText()));
        obj.setName(txtName.getText());

        return obj;
    }
}
