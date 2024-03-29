package com.project_classes.gui;

import com.project_classes.application.Main;
import com.project_classes.db.DBIntegrityException;
import com.project_classes.gui.listeners.DataChangeListener;
import com.project_classes.gui.util.Alerts;
import com.project_classes.gui.util.Utils;
import com.project_classes.model.entities.Department;
import com.project_classes.model.services.ServicesDepartamento;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListaDepertamentosController implements Initializable, DataChangeListener {

    private ServicesDepartamento service;
    private ObservableList<Department> obsList;

    @FXML
    private TableView<Department> tableViewDepartment;

    @FXML
    private TableColumn<Department, Integer> tableColumnId;

    @FXML
    private TableColumn<Department, String> tableColumnName;

    @FXML
    private TableColumn<Department, Department> tableColumnEDIT;

    @FXML
    private TableColumn<Department, Department> tableColumnREMOVE;

    @FXML
    private Button btNew;


    @FXML
    public void onBtNewAction(ActionEvent event) {

        Stage parentStage = Utils.currentStage(event);
        Department obj = new Department();

        createDialogForm(obj, "/com/project_classes/FormDepartamentos.fxml", parentStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    initializeNodes();
    }

    public void setServiceDepartamentos(ServicesDepartamento service) {
        this.service = service;
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView() {
        if (service == null) {
            throw new IllegalStateException();
        }
        List<Department> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);

        tableViewDepartment.setItems(obsList);

        initEditButtons();
        initRemoveButtons();
    }

    private void createDialogForm(Department obj, String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            Pane pane = loader.load();

            FormDepartamentosController controller = loader.getController();
            controller.setDepartment(obj);
            controller.setServiceDepartamento(new ServicesDepartamento());
            controller.subscribeDataChangeListener(this);

            controller.updateFormData();

            Stage dialogStage = new Stage();

            dialogStage.setTitle("Coloque os dados do Departamento");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();

        } catch (IOException e) {
            Alerts.showAlert("IOException", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }

    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Department, Department>() {
            private final Button button = new Button("Editar");
            @Override
            protected void updateItem(Department obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(
                        event -> createDialogForm(
                                obj, "/com/project_classes/FormDepartamentos.fxml",Utils.currentStage(event)));
            }
        });
    }

    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Department, Department>() {
            private final Button button = new Button("Remover");
            @Override
            protected void updateItem(Department obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    private void removeEntity(Department obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Tem certeza de que quer deletar?");

        if (result.get() == ButtonType.OK) {
            if (service == null) {
                throw new IllegalStateException("Serviço é null");
            }

            try {
                service.remove(obj);
                updateTableView();
            } catch (DBIntegrityException e) {
                Alerts.showAlert("Erro ao remover objeto", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }


}
