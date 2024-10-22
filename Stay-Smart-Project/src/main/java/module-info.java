module lk.ijse.gdse.staysmartproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens lk.ijse.gdse.staysmartproject to javafx.fxml;
    exports lk.ijse.gdse.staysmartproject;
}