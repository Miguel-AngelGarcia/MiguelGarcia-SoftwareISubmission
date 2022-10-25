package com.example.miguelgarciasoftwareisubmission;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This is the main method file
 *
 * /MiguelGarcia-SoftwareISubmission/Javadoc
 * location of the javadocs file
 */

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        /** RUNTIME ERROR with Max and Min
         * We resolved these by going through Part, Inventory, Outsourced, & InHouse classes and standardizing
         * the order:  ID, Name, inventory number, Max, Min, IDorCoName
         */
        Part brakes = new InHouse(1, "Brakes", 10, 11.50, 1000, 0, 1234);
        Inventory.addPart(brakes);

        Part wheels = new Outsourced(2, "Wheels", 15, 12.99, 1000, 0, "TireCo, Inc.");
        Inventory.addPart(wheels);

        Part seat = new InHouse(3, "Seat", 10, 15.00, 200, 0, 4569);
        Inventory.addPart(seat);

        Part smallWheels = new Outsourced(4, "Small Wheels", 12, 10.99, 100, 0, "TireCo, Inc.");
        Inventory.addPart(smallWheels);

        Product GiantBike = new Product(1000, "Giant Bike", 3, 199.99, 500, 0);
        Inventory.addProduct(GiantBike);


        launch(args);
    }
}