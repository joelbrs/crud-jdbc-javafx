module com.example.crudjdbcjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.project_classes to javafx.fxml;
    //exports com.example.crudjdbcjavafx;
    exports com.project_classes.gui;
    opens com.project_classes.gui to javafx.fxml, javafx.graphics;
    exports com.project_classes.application;
    opens com.project_classes.application to javafx.fxml;
    opens com.project_classes.model.entities to javafx.graphics, javafx.fxml, javafx.base;
    opens com.project_classes.model.services to javafx.graphics, javafx.fxml;

}