package com.project_classes.gui;

import com.project_classes.db.DbException;
import com.project_classes.gui.listeners.DataChangeListener;
import com.project_classes.gui.util.Alerts;
import com.project_classes.gui.util.Constraints;
import com.project_classes.gui.util.Utils;
import com.project_classes.model.entities.Department;
import com.project_classes.model.entities.Seller;
import com.project_classes.model.exceptions.ValidationException;
import com.project_classes.model.services.ServicesDepartamento;
import com.project_classes.model.services.ServicesVendedores;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class FormVendedoresController implements Initializable {

    private Seller entity;

    private ServicesVendedores service;

    private ServicesDepartamento departmentService;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker dpBirthDate;

    @FXML
    private TextField txtBaseSalary;

    @FXML
    private ComboBox<Department> comboBoxDepartment;

    @FXML
    private Label labelErrorName;

    @FXML
    private Label labelErrorEmail;

    @FXML
    private Label labelErrorBirthDate;

    @FXML
    private Label labelErrorBaseSalary;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    @FXML
    private ObservableList<Department> obsList;

    public void setServices(Seller entity) {
        this.entity = entity;
    }

    public void setServiceVendedores(ServicesVendedores service, ServicesDepartamento servicesDepartamento) {
        this.service = service;
        this.departmentService = servicesDepartamento;
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
        Constraints.setTextFieldMaxLength(txtName, 50);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Constraints.setTextFieldMaxLength(txtEmail, 60);

        Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");

        initializeComboBoxDepartment();
    }

    public void updateFormData() {

        if (entity == null) {
            throw  new IllegalStateException("Entity é null");
        }

        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
        txtEmail.setText(entity.getEmail());

        Locale.setDefault(Locale.US);
        txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));

        if (entity.getBirthDate() != null) {
            dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
        }

        if (entity.getDepartment() == null) {
            comboBoxDepartment.getSelectionModel().selectFirst();
        }
        else {
            comboBoxDepartment.setValue(entity.getDepartment());
        }
    }



    private Seller getFormData() {
        Seller obj = new Seller();

        ValidationException exception = new ValidationException("Erro de validação");

        obj.setId(Utils.tryParseToInt(txtId.getText()));

        if (txtName.getText() == null || txtName.getText().trim().equals("")) {
            exception.addErrors("name", "O campo não pode estar vazio!");
        }
        obj.setName(txtName.getText());

        if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")) {
            exception.addErrors("email", "O campo não pode estar vazio!");
        }
        obj.setEmail(txtEmail.getText());

        if (dpBirthDate.getValue() == null) {
            exception.addErrors("birthDate", "O campo não pode estar vazio!");
        } else {
            Instant instant = Instant.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            obj.setBirthDate(Date.from(instant));
        }

        if (txtBaseSalary.getText() == null || txtBaseSalary.getText().trim().equals("")) {
            exception.addErrors("baseSalary", "O campo não pode estar vazio!");
        }
        obj.setBaseSalary(Utils.tryParseToDouble(txtBaseSalary.getText()));

        obj.setDepartment(comboBoxDepartment.getValue());

        if (exception.getErrors().size() > 0) {
            throw exception;
        }
        return obj;
    }

    public void loadAssociatedObjects() {

        if (departmentService == null) {
            throw new IllegalStateException("Serviço de Departamento está null");
        }

        List<Department> list = departmentService.findAll();

        obsList = FXCollections.observableArrayList(list);
        comboBoxDepartment.setItems(obsList);
    }

    private void setErrorMesssages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        if (fields.contains("name")) {
            labelErrorName.setText(errors.get("name"));
        }
        else {
            labelErrorName.setText("");
        }

        if (fields.contains("email")) {
            labelErrorEmail.setText(errors.get("email"));
        }
        else {
            labelErrorEmail.setText("");
        }

        if (fields.contains("birthDate")) {
            labelErrorBirthDate.setText(errors.get("birthDate"));
        }
        else {
            labelErrorBirthDate.setText("");
        }

        if (fields.contains("baseSalary")) {
            labelErrorBaseSalary.setText(errors.get("baseSalary"));
        }
        else {
            labelErrorBaseSalary.setText("");
        }
    }

    private void initializeComboBoxDepartment() {
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }

}
