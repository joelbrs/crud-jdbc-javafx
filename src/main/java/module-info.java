module com.example.crudjdbcjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.crudjdbcjavafx to javafx.fxml;
    //exports com.example.crudjdbcjavafx;
    exports com.example.crudjdbcjavafx.gui;
    opens com.example.crudjdbcjavafx.gui to javafx.fxml;
    exports com.example.crudjdbcjavafx.application;
    opens com.example.crudjdbcjavafx.application to javafx.fxml;
}