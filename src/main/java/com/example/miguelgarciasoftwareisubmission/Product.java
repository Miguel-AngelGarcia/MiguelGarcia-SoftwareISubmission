package com.example.miguelgarciasoftwareisubmission;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class provided by WGU
 * Will create Product objects that can have associatedParts linked
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String productName;
    private int inventoryLevel;
    private double costPerUnit;
    private int inventoryMax;
    private int inventoryMin;

    /**
     * Constructor for Product Object
     * @param productID
     * @param productName
     * @param inventoryLevel
     * @param costPerUnit
     * @param inventoryMax
     * @param inventoryMin
     */
    public Product(int productID, String productName, int inventoryLevel, double costPerUnit, int inventoryMax, int inventoryMin) {
        this.productID = productID;
        this.productName = productName;
        this.inventoryLevel = inventoryLevel;
        this.costPerUnit = costPerUnit;
        this.inventoryMax = inventoryMax;
        this.inventoryMin = inventoryMin;
    }

    /**
     * method gets all associated parts for a selected product.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**
     * method Takes part as parameter and adds it to the associatedParts list for a Product
     */
    public void addAssociatedParts(Part part) {
        associatedParts.add(part);
    }

    /**
     * method takes part as a parameter and removes it from associatedParts list for a Product
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * GETTER for getProductID, returns object productID
     * @return
     *
     */
    public int getProductID() {
        return productID;
    }

    /**
     * GETTER for getProductName, returns object productName
     * @return
     *
     */
    public String getProductName() {
        return productName;
    }

    /**
     * GETTER for getInventoryLevel, returns object inventoryLevel
     * @return
     *
     */
    public int getInventoryLevel() {
        return inventoryLevel;
    }

    /**
     * GETTER for getCostPerUnit, returns object costPerUnit
     * @return
     *
     */
    public double getCostPerUnit() {
        return costPerUnit;
    }

    /**
     * GETTER for getInventoryMax, returns object inventoryMax
     * @return
     *
     */
    public int getInventoryMax() {
        return inventoryMax;
    }

    /**
     * GETTER for getInventoryMin, returns object inventoryMin
     * @return
     *
     */
    public int getInventoryMin() {
        return inventoryMin;
    }


    /**
     * SETTER for Product productID, sets parameter to field
     * @param productID
     *
     */
    public void setProductID(int productID){
        this.productID = productID;
    }

    /**
     * SETTER for Product productName, sets parameter to field
     * @param productName
     *
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * SETTER for Product inventoryLevel, sets parameter to field
     * @param inventoryLevel
     *
     */
    public void setInventoryLevel(int inventoryLevel) {
        this.inventoryLevel = inventoryLevel;
    }

    /**
     * SETTER for Product costPerUnit, sets parameter to field
     * @param costPerUnit
     *
     */
    public void setCostPerUnit(double costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    /**
     * SETTER for Product inventoryMax, sets parameter to field
     * @param inventoryMax
     *
     */
    public void setInventoryMax(int inventoryMax) {
        this.inventoryMax = inventoryMax;
    }

    /**
     * SETTER for Product inventoryMin, sets parameter to field
     * @param inventoryMin
     *
     */
    public void setInventoryMin(int inventoryMin) {
        this.inventoryMin = inventoryMin;
    }




}
