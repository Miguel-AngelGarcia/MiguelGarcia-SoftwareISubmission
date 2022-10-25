package com.example.miguelgarciasoftwareisubmission;

/**
 * outSourced class provided by WGU
 * will create outsourced Part objects
 */
public class Outsourced extends Part {
    private String companyName;
    /**
     * Contructor for outsourced Part object
     * will help create outsourced Part
     * @param id
     * @param name
     * @param invLVL
     * @param cost
     * @param max
     * @param min
     * @param machineID
     */

    /**
     * Part(int partID, String partName, int inventoryLevel, double costPerUnit, int inventoryMax, int inventoryMin )
     * Struggled with going back and forth to match the parameters in correct order. So we put Part above
     *
     * takes previous part info, and creates outSourced Object with inherited parameters
     */
    public Outsourced(int id, String name, int invLVL, double cost, int max, int min, String companyName) {
        super(id, name, invLVL, cost, max, min); //get everything except companyName from Part
        this.companyName = companyName;
    }

    /**
     * SETTER for companyName
     * @param companyName
     *
     */
    public void setCompanyName (String companyName) {
        this.companyName = companyName;
    }

    /**
     * GETTER for companyName
     * @return
     *
     */
    public String getCompanyName () {
        return companyName;
    }


}