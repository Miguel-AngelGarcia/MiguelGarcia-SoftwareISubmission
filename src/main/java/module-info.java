module com.example.miguelgarciasoftwareisubmission {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.miguelgarciasoftwareisubmission to javafx.fxml;
    exports com.example.miguelgarciasoftwareisubmission;
}