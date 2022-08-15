module com.example.crudjdbcjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.project_classes to javafx.fxml;
    //exports com.example.crudjdbcjavafx;
    exports com.project_classes.gui;
    opens com.project_classes.gui to javafx.fxml;
    exports com.project_classes.application;
    opens com.project_classes.application to javafx.fxml;
}