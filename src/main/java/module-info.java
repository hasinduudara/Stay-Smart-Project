module lk.ijse.gdse.staysmartproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires java.mail;

    opens lk.ijse.gdse.staysmartproject.controller to javafx.fxml;
    opens lk.ijse.gdse.staysmartproject.dto.tm to javafx.base;
    exports lk.ijse.gdse.staysmartproject;
    exports lk.ijse.gdse.staysmartproject.controller;
}