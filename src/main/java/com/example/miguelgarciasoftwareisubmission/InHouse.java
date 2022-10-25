package com.example.miguelgarciasoftwareisubmission;

/**
 * inHouse class provided by WGU
 * will create inHouse Part objects
 */
public class InHouse extends Part {
    private int machineID;

    /**
     * Contructor for InHouse Part object
     * will help create InHouse Part
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
     * takes previous part info, and creates inHouse Object with inherited parameters
     */
    public InHouse(int id, String name, int invLVL, double cost, int max, int min, int machineID) {
        super(id, name, invLVL, cost, max, min); //get everything except machine ID from Part
        this.machineID = machineID;
    }

    /**
     * SETTER for machineID
     * @param machineID
     *
     */
    public void setMachineID (int machineID) {
        this.machineID = machineID;
    }

    /**
     * GETTER for machineID
     * @return
     *
     */
    public int getMachineID () {
        return machineID;
    }


}
