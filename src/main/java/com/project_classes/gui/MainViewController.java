package com.project_classes.gui;

import com.project_classes.application.Main;
import com.project_classes.gui.util.Alerts;
import com.project_classes.model.entities.Department;
import com.project_classes.model.services.ServicesDepartamento;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemVendedor;

    @FXML
    private Menu menuItemDepartamento;

    @FXML
    private MenuItem menuItemSobre;

    @FXML
    public void onMenuItemVendedorAction() {
        System.out.println("onMenuItemVendedorAction");
    }

    @FXML
    public void onMenuItemDepartamentoAction() {
        loadView2("/com/project_classes/ListaDepartamentos.fxml");
    }

    @FXML
    public void onMenuItemSobreAction() {
        loadView("/com/project_classes/Sobre.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    private synchronized void loadView(String absoluteName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVBox = loader.load();

            Scene mainScene = Main.getMainScene();
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

            Node mainMenu = mainVBox.getChildren().get(0);

            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVBox.getChildren());

        } catch (IOException e) {
            Alerts.showAlert("IOException", "Erro ao carregar a página", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private synchronized void loadView2(String absoluteName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            VBox newVBox = loader.load();

            Scene mainScene = Main.getMainScene();
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

            Node mainMenu = mainVBox.getChildren().get(0);

            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(newVBox.getChildren());

            ListaDepertamentosController controller = loader.getController();
            controller.setServiceDepartamentos(new ServicesDepartamento());
            controller.updateTableView();

        } catch (IOException e) {
            Alerts.showAlert("IOException", "Erro ao carregar a página", e.getMessage(), Alert.AlertType.ERROR);
        }

    }


}
