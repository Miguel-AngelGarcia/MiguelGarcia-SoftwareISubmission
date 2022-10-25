package com.example.miguelgarciasoftwareisubmission;

/**
 * Part class provided by WGU
 * Will create Part objects
 */
public class Part {
    private int partID;
    private String partName;
    private int inventoryLevel;
    private double costPerUnit;
    private int inventoryMax;
    private int inventoryMin;

    /**
     * Constructor for Part Object
     * @param partID
     * @param partName
     * @param inventoryLevel
     * @param costPerUnit
     * @param inventoryMax
     * @param inventoryMin
     */
    public Part(int partID, String partName, int inventoryLevel, double costPerUnit, int inventoryMax, int inventoryMin ) {
        this.partID = partID;
        this.partName = partName;
        this.inventoryLevel = inventoryLevel;
        this.costPerUnit = costPerUnit;
        this.inventoryMax = inventoryMax;
        this.inventoryMin = inventoryMin;
    }

    //create GETTER methods, returns object variable
    /**
     * GETTER for getPartID, returns object partID
     * @return
     *
     */
    public int getPartID() {
        return partID;
    }

    /**
     * GETTER for getPartName, returns object partName
     * @return
     */
    public String getPartName() {
        return partName;
    }

    /**
     * GETTER for getInventoryLevel, returns object inventoryLevel
     * @return
     */
    public int getInventoryLevel() {
        return inventoryLevel;
    }

    /**
     * GETTER for getCostPerUnit, returns object costPerUnit
     * @return
     */
    public double getCostPerUnit() {
        return costPerUnit;
    }

    /**
     * GETTER for getInventoryMax, returns object inventoryMax
     * @return
     */
    public int getInventoryMax() {
        return inventoryMax;
    }

    /**
     * GETTER for getInventoryMin, returns object inventoryMin
     * @return
     */
    public int getInventoryMin() {
        return inventoryMin;
    }


    //create SETTER methods, sets parameter to field
    /**
     * SETTER for Part partID, sets parameter to field
     * @param partID
     *
     */
    public void setPartID(int partID){
        this.partID = partID;
    }

    /**
     * SETTER for Part partName, sets parameter to field
     * @param partName
     */
    public void setPartName(String partName) {
        this.partName = partName;
    }

    /**
     * SETTER for Part inventoryLevel, sets parameter to field
     * @param inventoryLevel
     */
    public void setInventoryLevel(int inventoryLevel) {
        this.inventoryLevel = inventoryLevel;
    }

    /**
     * SETTER for Part costPerUnit, sets parameter to field
     * @param costPerUnit
     */
    public void setCostPerUnit(double costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    /**
     * SETTER for Part inventoryMax, sets parameter to field
     * @param inventoryMax
     */
    public void setInventoryMax(int inventoryMax) {
        this.inventoryMax = inventoryMax;
    }

    /**
     * SETTER for Part inventoryMin, sets parameter to field
     * @param inventoryMin
     */
    public void setInventoryMin(int inventoryMin) {
        this.inventoryMin = inventoryMin;
    }




}
