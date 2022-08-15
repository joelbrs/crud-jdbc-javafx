package com.project_classes.gui;

import com.project_classes.db.DbException;
import com.project_classes.gui.listeners.DataChangeListener;
import com.project_classes.gui.util.Alerts;
import com.project_classes.gui.util.Constraints;
import com.project_classes.gui.util.Utils;
import com.project_classes.model.entities.Seller;
import com.project_classes.model.exceptions.ValidationException;
import com.project_classes.model.services.ServicesVendedores;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;

public class FormVendedoresController implements Initializable {

    private Seller entity;

    private ServicesVendedores service;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

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

    public void setSeller(Seller entity) {
        this.entity = entity;
    }

    public void setServiceVendedores(ServicesVendedores service) {
        this.service = service;
    }

    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    private void notifyDataChangeListeners() {

        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }

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

            notifyDataChangeListeners();

            Utils.currentStage(event).close();
        } catch (ValidationException e) {
            setErrorMesssages(e.getErrors());
        }
        catch (DbException e) {
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

    private Seller getFormData() {
        Seller obj = new Seller();

        ValidationException exception = new ValidationException("Erro de validação");

        obj.setId(Utils.tryParseToInt(txtId.getText()));

        if (txtName.getText() == null || txtName.getText().trim().equals("")) {
            exception.addErrors("name", "O campo não pode estar vazio!");
        }
        obj.setName(txtName.getText());

        if (exception.getErrors().size() > 0) {
            throw exception;
        }
        return obj;
    }

    private void setErrorMesssages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        if (fields.contains("name")) {
            labelErrorName.setText(errors.get("name"));
        }
    }
}
