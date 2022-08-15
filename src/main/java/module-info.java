module com.example.crudjdbcjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.crudjdbcjavafx to javafx.fxml;
    exports com.example.crudjdbcjavafx;
}