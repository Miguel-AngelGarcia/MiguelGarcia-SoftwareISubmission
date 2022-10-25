package com.example.miguelgarciasoftwareisubmission;

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

import java.net.URL;
import java.text.NumberFormat;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for the main screen. Provides logic for the main screen application.
 * Features include:
 * Add Part, Modify Part
 * Add Product, Modify Product
 * Part search, Product Search
 * Part deletion, Product deletion.
 * Exit
 */
public class MainController implements Initializable {
    @FXML private TableView<Part> mainScreenPartTable;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML private TableView<Product> mainScreenProductTable;
    @FXML private TableColumn<Product, Integer> productIDColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML private TextField partSearchBox;
    @FXML private TextField productSearchBox;


    /**
     * method will exit the main screen and terminate the program
     * @param ExitButton
     *
     *
     * RUNNTIME ERROR we ran into was a bug titled 'Class not found, which occured after clicking on our 'add' buttons
     * After changing the name of the controller in this IDE, we did not change the name of the controller in SceneBuilder
     * This caused the error as the compiler was looking for the old name 'HelloController' and not
     * 'MainController' (where our classes resided)
     * After changing the controller to the correct name, we were able to open our forms after clicking on buttons
     * FUTURE ENHANCEMENT add a feature that searches 1 second after the first key is pressed
     *
     */
    public void mainScreenExitButton(ActionEvent ExitButton) {
        Stage stage = (Stage) ((Node) ExitButton.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * method opens the addPart form, making it appear like we are jumping from main to add
     * @param event
     * @throws Exception
     *
     */
    @FXML
    void AddPart(ActionEvent event) throws Exception {

        Parent addParts = FXMLLoader.load(getClass().getResource("addPart.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    /**
     * method opens the modifyPart form window
     * It will also populate that form with information on the user-selected Part.
     * throws an error if user did not select a part before clicking button
     *
     *
     * @param event
     * @throws Exception
     *
     */
    @FXML
    void modifyPart(ActionEvent event) throws Exception {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("modifyPart.fxml"));
            loader.load();

            //loads modify part controller
            ModifyPartController MPController = loader.getController();
            MPController.getPartInfo(mainScreenPartTable.getSelectionModel().getSelectedIndex(),mainScreenPartTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a part first");
            alert.show();
        }
    }

    /**
     * method take us to the addProduct screen
     * @param event
     * @throws Exception
     *
     */
    @FXML
    void addProduct(ActionEvent event) throws Exception {

        Parent addParts = FXMLLoader.load(getClass().getResource("addProduct.fxml"));
        Scene scene = new Scene(addParts);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    /**
     * method takes us to the modifyProduct screen.
     * It will also populate that form with information on the user-selected Product.
     * throws an error if user did not select a product before clicking button
     * @param event
     * @throws Exception
     *
     *
     * RUNTIME ERROR we ran into was having this file search as the controller (addProduct modifyPart, etc) for every Button
     * We ran into several 'null pointer' issues where our modify or add windows did not open.
     * We created the 'addPartContoller' and 'modifyPartController' classes to fix the issue
     */
    @FXML
    void modifyProduct(ActionEvent event) throws Exception {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("modifyProduct.fxml"));
            loader.load();

            //loads modify product controller
            ModifyProductController MPController = loader.getController();
            MPController.getProductInfo(mainScreenProductTable.getSelectionModel().getSelectedIndex(),mainScreenProductTable.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a product first");
            alert.show();
        }

    }

    /**
     * will help us format the price to two decimal places as dollar values
     * We were struggling to get the price to show as a double with 2 decimal places.
     * This solution seemed most elegant
     */
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    /**
     * method helps us populate the Part menu with parts at the beginning of the program.
     * @param url
     * @param resourceBundle
     *
     * RUNTIME ERROR
     * We needed to add fd:ids to the columns and tables in the FXML file of the main window.
     * Obtained these "idenifiers" ("partID" in PropertyValueFactory) from the Part class.
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainScreenPartTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("partID"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryLevel"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("costPerUnit"));
        //generates cell that use a currency formatter
        partPriceColumn.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double partPriceColumn, boolean empty) {
                super.updateItem(partPriceColumn, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(partPriceColumn));
                }
            }
        });

        mainScreenProductTable.setItems(Inventory.getAllProducts());
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryLevel"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("costPerUnit"));
        productPriceColumn.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double productPriceColumn, boolean empty) {
                super.updateItem(productPriceColumn, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currencyFormat.format(productPriceColumn));
                }
            }
        });


    }


    /**
     * method allows us to search parts via the text field.
     * @param event
     *
     * FUTURE ENHANCEMENT add a feature that searches 1 second after the first key is pressed
     */
    @FXML
    void mainScreenPartSearch(ActionEvent event) {
        String searchText = partSearchBox.getText();
        ObservableList<Part> results = Inventory.lookupPart(searchText);
        try {
            while (results.size() == 0 ) {
                int partID = Integer.parseInt(searchText);
                results.add(Inventory.lookupPart(partID));
            }
            mainScreenPartTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noPartSearchBar = new Alert(Alert.AlertType.ERROR);
            noPartSearchBar.setTitle("Error Message");
            noPartSearchBar.setContentText("Part not found");
            noPartSearchBar.showAndWait();
        }
    }

    /**
     * method allows us to search products via the text field.
     * @param event
     *
     */
    @FXML
    void mainScreenProductSearch(ActionEvent event) {
        String searchText = productSearchBox.getText();
        ObservableList<Product> results = Inventory.lookupProduct(searchText);
        try {
            while (results.size() == 0 ) {
                int productID = Integer.parseInt(searchText);
                results.add(Inventory.lookupProduct(productID));
            }
            mainScreenProductTable.setItems(results);
        } catch (NumberFormatException e) {
            Alert noProductSearchBar = new Alert(Alert.AlertType.ERROR);
            noProductSearchBar.setTitle("Error Message");
            noProductSearchBar.setContentText("Product not found");
            noProductSearchBar.showAndWait();
        }
    }

    /**
     * method helps us delete parts from the main screen part table
     * User will select a part, hit delete and the part with be gone from the table
     * @param event
     *
     */
    @FXML
    void mainScreenDeletePartButton(ActionEvent event) {
        Part selectedPart = mainScreenPartTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you want to delete selected part?");
        Optional<ButtonType> result = alert.showAndWait();

        String result_string = String.valueOf(selectedPart);
        //checks if a part is selected, user can click delete when in part_search_box
        if (result_string == "null" && result.get() == ButtonType.OK ) {
            Alert noPartSearchBar = new Alert(Alert.AlertType.ERROR);
            noPartSearchBar.setTitle("Error Message");
            noPartSearchBar.setContentText("No Part Selected");
            noPartSearchBar.showAndWait();
        }

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
        }
    }


    /**
     * method will delete selected product from main screen product table
     * Product will not delete if a Product has associatedParts assigned to it. Error will be thrown
     * Will throw an error message if a Product is not selected.
     *
     * @param event
     *
     */
    @FXML
    void mainScreenDeleteProductButton(ActionEvent event) {
        Product selectedProduct = mainScreenProductTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you want to delete selected product?");
        Optional<ButtonType> result = alert.showAndWait();

        String result_string = String.valueOf(selectedProduct);

        //checks if a part is selected, user can click delete when in product_search_box
        if (result_string == "null"&& result.get() == ButtonType.OK ) {
            Alert noProductSearchBar = new Alert(Alert.AlertType.ERROR);
            noProductSearchBar.setTitle("Error Message");
            noProductSearchBar.setContentText("No Product Selected");
            noProductSearchBar.showAndWait();
            return;
        }

        if (result.isPresent() && result.get() == ButtonType.CANCEL) {
            return;
        }

        int associatedPartChecker = selectedProduct.getAllAssociatedParts().size();
        /**
         * System.out.println("number:" + checker);
         * this helped us code an error message for a user deleting a product with an associated part
         * if an associated part exists for a product, it will not be deleted
         */

        if (associatedPartChecker > 0) {
            Alert assocPartPresent = new Alert(Alert.AlertType.ERROR);
            assocPartPresent.setTitle("Error Message");
            assocPartPresent.setHeaderText("Cannot delete product with associated parts");
            assocPartPresent.setContentText("Please remove associated parts before deleting");
            assocPartPresent.showAndWait();
            return;
        }

       if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deleteProduct(selectedProduct);
        }
    }

}