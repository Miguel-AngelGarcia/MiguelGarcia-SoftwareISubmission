package com.example.miguelgarciasoftwareisubmission;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * Inventory class provided by WGU
 * Will serve as a place to store Part and Product information
 */
public class Inventory {

    /**
     * list of allParts in an inventory
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * list of allProducts in an inventory
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * method adds newPart to inventory
     * @param newPart
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    /**
     * method gets all parts
     * @return
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    /**
     * method allows us to updated selectedPart with updatedPart and place in the former's index
     * @param index
     * @param updatedPart
     */
    public static void updatePart(int index, Part updatedPart) {

        allParts.set(index, updatedPart);
    }

    /**
     * method will help us look up a Part via partName
     * @param partName
     * @return
     *
     * added '.toLowerCase()' method to make search case-insensitive
     *
     * FUTURE ENHANCEMENT In the future, let's use regex to search more effectively + creatively
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> PartName = FXCollections.observableArrayList();

        for (Part part: allParts) {
            //regex pattern
            String fromPart = part.getPartName();

            if (fromPart.toLowerCase().contains(partName.toLowerCase())) {
                PartName.add(part);
            }
        }
        return PartName;
    }

    /**
     * method will help us look for Part via partID
     * @param partID
     *
     * @return
     */
    public static Part lookupPart(int partID) {
        for(Part part: Inventory.getAllParts()) {
            while (part.getPartID() == partID) {
                return part;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No item found");
        alert.show();
        return null;
    }

    /**
     * method will delete selected part
     * @param selectedPart
     *
     * @return
     */
    public static boolean deletePart (Part selectedPart) {
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * method will add created product to inventory
     * @param newProduct
     *
     */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }

    /**
     * GETTER for all products
     * @return
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * will update product in inventory from selected-product index and will replace selected part with updated part
     * @param index
     * @param updatedProduct
     *
     */
    public static void updateProduct(int index, Product updatedProduct) {

        allProducts.set(index, updatedProduct);
    }

    /**
     * method will look up products via string entered by user
     * @param productName
     *
     * @return
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> ProductName = FXCollections.observableArrayList();

        for (Product product: allProducts) {
            //regex pattern
            String fromProduct = product.getProductName();

            if (fromProduct.toLowerCase().contains(productName.toLowerCase())) {
                ProductName.add(product);
            }
        }
        return ProductName;
    }


    /**
     * method will lookup products with productID
     * @param productID
     *
     * @return
     */
    public static Product lookupProduct(int productID) {
        for(Product product: Inventory.getAllProducts()){
            while (product.getProductID() == productID)
                return product;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No item found");
        alert.show();
        return null;
    }

    /**
     * method will delete user-selected product from inventory
     * @param selectedProduct
     *
     * @return
     *
     */
    public static boolean deleteProduct (Product selectedProduct) {
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }



}