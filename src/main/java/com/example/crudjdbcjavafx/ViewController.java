package com.example.crudjdbcjavafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

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
        System.out.println("onMenuItemDepartamentoAction");
    }

    @FXML
    public void onMenuItemSobreAction() {
        System.out.println("onMenuItemSobreAction");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }
}
