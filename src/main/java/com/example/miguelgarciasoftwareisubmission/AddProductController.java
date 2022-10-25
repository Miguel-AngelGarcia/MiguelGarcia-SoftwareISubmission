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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

/**
 * Controller for the AddProduct Form. Will provide the logic for the application form/screen.
 * Will take the user-selected Product and save if 'Save' button is pressed.
 *
 * RUNTIME ERROR we ran into, we forgot to have the AddProductController class implement Initializable
 * The top addProducts table did not populate
 */
public class AddProductController implements Initializable{
    @FXML
    private TextField addProductName;
    @FXML
    private TextField addInvLvl;
    @FXML
    private TextField addInvMax;
    @FXML
    private TextField addPrice;
    @FXML
    private TextField addInvMin;

    @FXML private TableView<Part> addProductTable;
    @FXML private TableColumn<Part, Integer> addProductIDCol;
    @FXML private TableColumn<Part, String> addProductNameCol;
    @FXML private TableColumn<Part, Integer> addProductInventoryCol;
    @FXML private TableColumn<Part, Double> addProductPriceCol;
    @FXML private TableView<Part> associatedProductTable;
    @FXML private TableColumn<Part, Integer> addAssociatedIDCol;
    @FXML private TableColumn<Part, String> addAssociatedNameCol;
    @FXML private TableColumn<Part, Integer> addAssociatedInventoryCol;
    @FXML private TableColumn<Part, Double> addAssociatedPriceCol;
    @FXML private TextField addProductSearchBox;

    /**
     * observableList holds the associatedPartList for a Product
     */
    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    /**
     * method adds selected parts in top table to the bottom table (associatedParts for a Product) if user
     * presses 'Add' button
     * throws error is part is not selected by user
     *
     * RUNTIME ERROR
     * Can not retrieve property 'productName' in PropertyValueFactory: javafx.scene.control.cell.PropertyValueFactory@45f38a17
     * with provided class type: class com.example.testfx.Outsourced
     *
     * Below, we have PropertValueFactory as 'productID' instead of 'PartID'
     * associatedProductTable.setItems(associatedPartsList);
     * addAssociatedIDCol.setCellValueFactory(new PropertyValueFactory<>("[partID"]));
     */
    @FXML
    void addProductAdd(ActionEvent event) {
        Part selectedPart = addProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Select part from list");
            alert.showAndWait();
            return;
        }
        else if (!associatedPartsList.contains(selectedPart))
        {
            associatedPartsList.add(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }

    /**
     * method will take the information filled out in the addProduct form and save it as a new product
     * Will throw exceptions if Max < Min OR if Inventory is not within the max and min.
     * Will throw error if any fields are incorrectly filled.
     *
     * @param event
     * @throws Exception
     *
     */
    @FXML
    void addProductSaveButton(ActionEvent event) throws Exception {
        try {
            //// Will try to count parts. Get the largest ID, then +1 to get newest partID for new product
            int numID = 0;
            ObservableList<Product> allProducts = Inventory.getAllProducts();
            for (int i = 0, allProductsSize = allProducts.size(); i < allProductsSize; i++) {
                Product product = allProducts.get(i);
                if (product.getProductID() > numID)
                    numID = product.getProductID();
                numID += 1;

            }

            String productName = addProductName.getText();
            int invLVL = Integer.parseInt(addInvLvl.getText());
            double price = Double.parseDouble(addPrice.getText());
            int min = Integer.parseInt(addInvMin.getText());
            int max = Integer.parseInt(addInvMax.getText());

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

            Product product = new Product(numID, productName, invLVL, price, max, min);

            for (Part part: associatedPartsList) {
                /*DEBUG HELP
                System.out.println("part: " + part);
                System.out.println("APL: " + associatedPartsList);
                 */
                if (part != associatedPartsList)
                    product.addAssociatedParts(part);
            }

            Inventory.getAllProducts().add(product);

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
     * method cancels the process of adding a new product and closes the window, returning to main window
     * @param event
     * @throws IOException
     *
     */
    @FXML
    public void addProductCancelButton (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
        Stage MainScreenReturn = (Stage)((Node)event.getSource()).getScene().getWindow();
        MainScreenReturn.setScene(scene);
        MainScreenReturn.show();

    }

    /**
     * will remove user-selected associated part from table (bottom table)
     * this removes the associatedPart from the Product
     * will throw error if part is not selected
     *
     * @param event
     */
    @FXML
    void removeAssociatedPartButton(ActionEvent event) {
        Part selectedPart = associatedProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert removeAssociatedPart = new Alert(Alert.AlertType.WARNING);
            removeAssociatedPart.setTitle("Input Error");
            removeAssociatedPart.setContentText("Select part from list");
            removeAssociatedPart.showAndWait();
            return;
        }
        else if (associatedPartsList.contains(selectedPart))
        {
            associatedPartsList.remove(selectedPart);
            associatedProductTable.setItems(associatedPartsList);
        }
    }

    /**
     * will take user text and search for parts in top table via partID or partName
     * will throw error message if part is not found
     *
     * @param event
     *
     */
    @FXML
    void addProductPartSearch(ActionEvent event) {
        String searchText = addProductSearchBox.getText();
        ObservableList<Part> results = Inventory.lookupPart(searchText);
        try {
            while (results.size() == 0 ) {
                int partID = Integer.parseInt(searchText);
                results.add(Inventory.lookupPart(partID));
            }
            addProductTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noPartFoundSearchBar = new Alert(Alert.AlertType.ERROR);
            noPartFoundSearchBar.setTitle("Error Message");
            noPartFoundSearchBar.setContentText("Part not found");
            noPartFoundSearchBar.showAndWait();
        }
    }

    /**
     * will help us format the price to two decimal places as dollar values
     */
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    /**
     * this helps us populate top and bottom table
     * Will add all Parts in inventory for top table
     * Will add all associatedParts for a Product in bottom table
     * bottom table should be empty when opening the form
     *
     * @param url
     * @param resourceBundle
     *
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //parts to choose from
        addProductTable.setItems(Inventory.getAllParts());
        addProductIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addProductNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        addProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("inventoryLevel"));
        addProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("costPerUnit"));
        //generates cells that use a currency format
        addProductPriceCol.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double addProductPriceCol, boolean empty) {
                super.updateItem(addProductPriceCol, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(addProductPriceCol));
                }
            }
        });


        //add parts to associated table (bottom)
        associatedProductTable.setItems(associatedPartsList);
        addAssociatedIDCol.setCellValueFactory(new PropertyValueFactory<>("partID"));
        addAssociatedNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        addAssociatedInventoryCol.setCellValueFactory(new PropertyValueFactory<>("inventoryLevel"));
        addAssociatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("costPerUnit"));
        addAssociatedPriceCol.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double addAssociatedPriceCol, boolean empty) {
                super.updateItem(addAssociatedPriceCol, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(addAssociatedPriceCol));
                }
            }
        });

    }
}


