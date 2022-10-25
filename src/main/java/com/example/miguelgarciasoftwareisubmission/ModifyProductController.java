package com.example.miguelgarciasoftwareisubmission;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

/**
 * Controller for the ModifyProduct Form. Will provide the logic for the application form/screen.
 * Will take the user-selected Product and modify Product if saved.
 */
public class ModifyProductController implements Initializable {

    @FXML private TextField modifyProductSearchBox;
    @FXML private Button modifyProductSearchButton;
    @FXML private TextField modifyProductID;
    @FXML private TextField modifyProductName;
    @FXML private TextField modifyProductInv;
    @FXML private TextField modifyProductPrice;
    @FXML private TextField modifyProductMax;
    @FXML private TextField modifyProductMin;
    @FXML private TableView<Part> modifyProductTable;
    @FXML private TableColumn<Part, Integer> modifyProductIDCol;
    @FXML private TableColumn<Part, String> modifyProductNameCol;
    @FXML private TableColumn<Part, Integer> modifyProductInventoryCol;
    @FXML private TableColumn<Part, Double> modifyProductPriceCol;
    @FXML private TableView<Part> associatedProductTable;
    @FXML private TableColumn<Part, Integer> modifyAssociatedIDCol;
    @FXML private TableColumn<Part, String> modifyAssociatedNameCol;
    @FXML private TableColumn<Part, Integer> modifyAssociatedInventoryCol;
    @FXML private TableColumn<Part, Double> modifyAssociatedPriceCol;
    @FXML private Button modifyProductCancelButton;
    @FXML private Button modifyProductSaveButton;
    @FXML private Button removeAssociatedPartButton;
    @FXML private Button modifyProductmodifyButton;
    @FXML private TextField addProductMachineID;

    private Product selectedProduct;

    /**
     * observableList holds the associatedPartList for a Product
     */
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    private int currentIndex = 0;

    /**
     * method gets user-selected product info from main screen table and populates the form
     * selectedIndex will help us replace the selectedPart with the updatedPart later when the 'Save' button is pressed
     *
     * @param selectedIndex
     * @param selectedProduct
     */
    public void getProductInfo (int selectedIndex, Product selectedProduct) {
        /*
        System.out.println("getting current index: " + selectedIndex);
        System.out.println("working on Product: " + selectedProduct);
        To help us debug and troubleshoot
        */
        this.selectedProduct = selectedProduct;


        currentIndex = selectedIndex;

        modifyProductID.setText(String.valueOf(selectedProduct.getProductID()));
        modifyProductName.setText(String.valueOf(selectedProduct.getProductName()));
        modifyProductInv.setText(String.valueOf(selectedProduct.getInventoryLevel()));
        //modifyProductPrice.setText(String.valueOf(selectedProduct.getCostPerUnit()));
        //set decimal to 2 places
        modifyProductPrice.setText(String.format("%.2f", selectedProduct.getCostPerUnit()));
        modifyProductMax.setText(String.valueOf(selectedProduct.getInventoryMax()));
        modifyProductMin.setText(String.valueOf(selectedProduct.getInventoryMin()));

        for (Part part: selectedProduct.getAllAssociatedParts()) {
            associatedPartsList.add(part);

        }

    }


    /**
     * method will activate the add feature. Will take part from
     * list (top table) and place in associated parts table(bottom table)
     * throws an error if part is not selected from top table
     *
     * @param event
     *
     */
    @FXML
    void modifyProductAdd(ActionEvent event) {
        Part selectedPart = modifyProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Select part from list");
            alert.showAndWait();
            return;
        }
        else if (!associatedPartsList.contains(selectedPart))
        {
            //System.out.println("selected part: " + selectedPart + " not in " + associatedPartsList + ". Adding to APL.");
            associatedPartsList.add(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }

    /**
     * method will take the information filled out in the modifyProduct form and save it as a new product
     * Will throw exceptions if Max < Min OR if Inventory is not within the max and min.
     * Will throw error if any fields are incorrectly filled.
     *
     * RUNTIME ERROR (logical) - updatedProduct != associatedPartsList always equals true. eliminated this logic form.
     *
     * RUNTIME ERROR; anything left in bottom table gets saved AGAIN. Same part shows up in associated part table
     * We resolved this by switching our code from adding parts when saving
     * -> add part if it is not in the associatedPartList
     *
     * RUNTIME ERROR: we ran into the issue of deleting an associated part not saving.
     * We resolved this by realizing we forgot to create a 'delete associated part' method
     *
     * @param event
     * @throws Exception
     *
     */
    @FXML
    void modifyProductSaveButton(ActionEvent event) throws Exception {
        try {
            /*
            System.out.println("selected product: " + selectedProduct + " contains");
            System.out.println("associated parts: " + selectedProduct.getAllAssociatedParts());
            DEBUGGING help
            */

            int numID = Integer.parseInt(modifyProductID.getText());
            String productName = modifyProductName.getText();
            int invLVL = Integer.parseInt(modifyProductInv.getText());
            double price = Double.parseDouble(modifyProductPrice.getText());
            int min = Integer.parseInt(modifyProductMin.getText());
            int max = Integer.parseInt(modifyProductMax.getText());

            //Min should be less than max.
            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum must be greater than minimum.");
                alert.showAndWait();
                return;
            }
            //Inventory should be between the min and max values.
            else if (invLVL < min || max < invLVL) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be within max and min.");
                alert.showAndWait();
                return;
            }

            Product updatedProduct = new Product(numID, productName, invLVL, price, max, min);

            /**
             RUNTIME ERROR (logical) - updatedProduct != associatedPartsList always equals true
             RUNTIME ERROR; anything left in bottom table gets saved AGAIN. Same part shows up in associated part table

             RUNTIME ERROR: we ran into the issue of deleting an associated part not saving.
             We resolved this by realizing we forgot to create a 'delete associated part' method
             */

            for (Part part: associatedPartsList) {
                //println("part: " + part);System.out
                //updatedProduct.addAssociatedParts(part);

                if (updatedProduct.getAllAssociatedParts().contains(part)) {
                    //System.out.println("not added to part list");
                } else if (!updatedProduct.getAllAssociatedParts().contains(part)){
                    //System.out.println(" added to part list for candidate product " + updatedProduct);
                    updatedProduct.addAssociatedParts(part);
                }

            }
            //System.out.println("associatedPartList contains: " + associatedPartsList);
            //System.out.println("updatedProduct's parts: " + updatedProduct.getAllAssociatedParts());

            Inventory.updateProduct(currentIndex, updatedProduct);

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
     * method cancels the process of modifying a new product and closes the window, returning to main window
     * @param event
     * @throws IOException
     *
     */
    @FXML
    public void modifyProductCancelButton (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
        Stage MainScreenReturn = (Stage)((Node)event.getSource()).getScene().getWindow();
        MainScreenReturn.setScene(scene);
        MainScreenReturn.show();

    }

    /**
     * method removes user-selected associated part from table (bottom table)
     * throws error if Part is not selected in bottom table
     * @param event
     *
     * ---The user should not delete a product that has a part associated with it---
     */
    @FXML
    void removeAssociatedPartButton(ActionEvent event) {
        Part selectedPart = associatedProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Select part from list");
            alert.showAndWait();
            return;
        }
        else if (associatedPartsList.contains(selectedPart))
        {
            associatedPartsList.remove(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }


    /**
     * method will take text from user and search for parts in list via PartID or PartName
     * throws error if Part is not found
     *
     * @param event
     *
     */
    @FXML
    void modifyProductPartSearch(ActionEvent event) {
        String searchText = modifyProductSearchBox.getText();
        ObservableList<Part> results = Inventory.lookupPart(searchText);
        try {
            while (results.size() == 0 ) {
                int partID = Integer.parseInt(searchText);
                results.add(Inventory.lookupPart(partID));
            }
            modifyProductTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noPartsFound = new Alert(Alert.AlertType.ERROR);
            noPartsFound.setTitle("Error Message");
            noPartsFound.setContentText("Part not found");
            noPartsFound.showAndWait();
        }
    }

    //will help us format the price to two decimal places as dollar values
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    /**
     * method will initialize the tables on the modify product form
     * Will add all Parts in inventory for top table
     * Will add all associatedParts for a Product in bottom table
     *
     *
     * @param url
     * @param resourceBundle
     *
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //top table
        modifyProductTable.setItems(Inventory.getAllParts());
        modifyProductIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        modifyProductNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        modifyProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("inventoryLevel"));
        modifyProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("costPerUnit"));
        //generates cells that use a currency format
        modifyProductPriceCol.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double modifyProductPriceCol, boolean empty) {
                super.updateItem(modifyProductPriceCol, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(modifyProductPriceCol));
                }
            }
        });

        //modify parts to associated table (bottom)
        associatedProductTable.setItems(associatedPartsList);
        modifyAssociatedIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        modifyAssociatedNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        modifyAssociatedInventoryCol.setCellValueFactory(new PropertyValueFactory<>("inventoryLevel"));
        modifyAssociatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("costPerUnit"));
        modifyAssociatedPriceCol.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double modifyProductPriceCol, boolean empty) {
                super.updateItem(modifyProductPriceCol, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(modifyProductPriceCol));
                }
            }
        });

    }
}