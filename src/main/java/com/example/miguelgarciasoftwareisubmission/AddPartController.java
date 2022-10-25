package com.example.miguelgarciasoftwareisubmission;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

/**
 * Controller for the AddPart Form. Will provide the logic for the application form/screen.
 * Will take the user-entered Part information and it if 'Save' button is pressed.
 * Will be added to Part table on Main Screen
 */
public class AddPartController
{
    @FXML
    private TextField addPartName;
    @FXML
    private TextField addInvLvl;
    @FXML
    private TextField addIDorTitle;
    @FXML
    private TextField addInvMax;
    @FXML
    private TextField addPrice;
    @FXML
    private TextField addInvMin;

    @FXML
    private Label welcomeText;
    @FXML
    private Label finalLabelText;
    //@FXML
    //private Button addPartButton;
    @FXML private RadioButton addPartInHouseRadio;
    @FXML private RadioButton addPartOutsourcedRadio;


    /**
     * these two methods below help change the text on the bottom text area to 'Comapny Name' or 'Machine ID'
     * When the 'Outsourced' or 'InHouse' radio buttons are pressed.
     */
    @FXML
    protected void outsourcedRadioButton() {
        finalLabelText.setText("Company Name");
    }
    @FXML
    protected void inHouseRadioButton() {
        finalLabelText.setText("Machine ID");
    }

    /**
     * methods will take the information filled out in the addPart form and save it as a new part
     * after 'save' button is pressed
     * Will throw exceptions if Max < Min OR if Inventory is not within the max and min.
     * Will throw error if any fields are incorrectly filled.
     *
     * @param event
     * @throws Exception
     *
     */
    @FXML
    void addPartSaveButton(ActionEvent event) throws Exception {
        try {
            // Will try to count parts. Get the largest ID, then +1 to get newest partID for new product
            int numID = 0;
            ObservableList<Part> allParts = Inventory.getAllParts();
            for (int i = 0, allPartsSize = allParts.size(); i < allPartsSize; i++) {
                Part part = allParts.get(i);
                if (part.getPartID() > numID)
                    numID = part.getPartID();
                numID += 1;
            }

            String partName = addPartName.getText();
            int invLVL = Integer.parseInt(addInvLvl.getText());
            double price = Double.parseDouble(addPrice.getText());
            int min = Integer.parseInt(addInvMin.getText());
            int max = Integer.parseInt(addInvMax.getText());

            int machineID = 0;
            String companyName;


            //Min should be less than max.
            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum must be greater than minimum.");
                alert.showAndWait();
                return;
            }
            //Inventory should be between the min and max values.
            else if (invLVL < min || max < invLVL) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be within min and max.");
                alert.showAndWait();
                return;
            }

            if (addPartInHouseRadio.isSelected()) {
                machineID = Integer.parseInt(addIDorTitle.getText());

                //we originally had ID, name, invLVL, price, MIN, MAX
                //the switching of max and min had the numbers in the incorrect place on the Modify Part Form
                InHouse addPart = new InHouse(numID, partName, invLVL, price, max, min, machineID);
                Inventory.addPart(addPart);
            }

            if (addPartOutsourcedRadio.isSelected()) {
                companyName = addIDorTitle.getText();
                Outsourced addPart = new Outsourced(numID, partName, invLVL, price, max, min, companyName);
                Inventory.addPart(addPart);
            }

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("main.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Form contains blank fields or invalid values.");
            alert.showAndWait();
            return;
        }

    }

    /**
     * method cancels the process of adding a new part and closes the window, returning to main window
     * @param event
     * @throws IOException
     *
     */
    @FXML
    public void addPartCancelAction (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
        Stage MainScreenReturn = (Stage)((Node)event.getSource()).getScene().getWindow();
        MainScreenReturn.setScene(scene);
        MainScreenReturn.show();

    }
}
