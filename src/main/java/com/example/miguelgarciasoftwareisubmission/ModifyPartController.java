package com.example.miguelgarciasoftwareisubmission;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the ModifyPart Form. Will provide the logic for the application form/screen.
 * Will take the user-selected Part and save updated information if 'Save' button is pressed.
 * Will be modified in Main Screen Part table
 */
public class ModifyPartController {
    @FXML
    private Label finalLabelText;

    @FXML private TextField modifyPartID;
    @FXML private TextField modifyPartName;
    @FXML private TextField modifyPartInvLevel;
    @FXML private TextField modifyPartPrice;
    @FXML private TextField modifyPartMax;
    @FXML private TextField modifyPartMin;
    @FXML private TextField modifyPartCoNameOrID;
    @FXML private Button modifyPartButton;
    @FXML private RadioButton modifyPartInHouseRadio;
    @FXML private RadioButton modifyPartOutsourcedRadio;

    private int currentIndex = 0;
    /**
     * this two below help change the text on the bottom text area to 'Company Name' or 'Machine ID'
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
     * This methods helps get the partInfo to display in the modify form. Grabs the index of the item in the menu
     * and the part. Gets user-selected part information and populates the modifyPart form
     *
     * RUNTIME ERROR
     * We ran into issues of getting 'modifyPartID' is null.
     * Fix: We forgot to assign an fx:id because the text field line on the FXML file was disabled, and
     * away from the other textfields in the FXML file. We later added it to fix the bug
     *
     */
    public void getPartInfo (int selectedIndex, Part part) {

        if (part instanceof InHouse) {
            modifyPartInHouseRadio.setSelected(true);
            modifyPartCoNameOrID.setText(String.valueOf(((InHouse) part).getMachineID()));
        }
        else {
            modifyPartOutsourcedRadio.setSelected(true);
            modifyPartCoNameOrID.setText(((Outsourced) part).getCompanyName());
        }
        currentIndex = selectedIndex;

        modifyPartID.setText(String.valueOf(part.getPartID()));
        modifyPartName.setText(String.valueOf(part.getPartName()));
        modifyPartInvLevel.setText(String.valueOf(part.getInventoryLevel()));
        //modifyPartPrice.setText(String.valueOf(part.getCostPerUnit()));
        //we want to format the double to 2 decimal places
        modifyPartPrice.setText(String.format("%.2f", part.getCostPerUnit()));
        modifyPartMax.setText(String.valueOf(part.getInventoryMax()));
        modifyPartMin.setText(String.valueOf(part.getInventoryMin()));

    }

    /**
     * method will save the modify part information from the Modify part form
     * throws exception if
     * Will throw exceptions if Max < Min OR if Inventory is not within the max and min.
     * Will throw error if any fields are incorrectly filled.
     *
     * @param event
     * @throws Exception
     *
     */
    public void modifyPartSaveButton (ActionEvent event) throws Exception {
        try {
            int partID = Integer.parseInt(modifyPartID.getText());
            String partName = modifyPartName.getText();
            int invLvl = Integer.parseInt(modifyPartInvLevel.getText());
            double partPrice = Double.parseDouble(modifyPartPrice.getText());
            int max = Integer.parseInt(modifyPartMax.getText());
            int min = Integer.parseInt(modifyPartMin.getText());


            //setting these aside, we should get these from the row clicked
            int machineID;
            String companyName;

            //check to confirm Max > Min
            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum must be greater than minimum.");
                alert.showAndWait();
                return;
            }

            //check to confirm inventory between min and max
            else if (invLvl < min || max < invLvl) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be within max and min.");
                alert.showAndWait();
                return;
            }

            if (modifyPartInHouseRadio.isSelected()) {
                machineID = Integer.parseInt(modifyPartCoNameOrID.getText());

                //InHouse(int id, String name, int invLVL, double cost, int min, int max, int machineID)
                InHouse updatedPart = new InHouse(partID, partName, invLvl, partPrice, max, min, machineID);
                Inventory.updatePart(currentIndex, updatedPart);
            }

            if (modifyPartOutsourcedRadio.isSelected()) {
                companyName = modifyPartCoNameOrID.getText();
                Outsourced updatedPart = new Outsourced(partID, partName, invLvl, partPrice, max, min, companyName);
                Inventory.updatePart(currentIndex, updatedPart);
            }

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("Main.fxml"));
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
     * method will cancel the part modification and close the window, returning to main menu
     *
     * @param event
     * @throws IOException
     *
     */
    @FXML
    public void modifyPartCancelAction (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
        Stage MainScreenReturn = (Stage)((Node)event.getSource()).getScene().getWindow();
        MainScreenReturn.setScene(scene);
        MainScreenReturn.show();

    }



}
