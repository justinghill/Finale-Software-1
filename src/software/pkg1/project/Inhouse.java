/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.pkg1.project;

/**
 *
 * @author jhill
 */
public class Inhouse extends Part {

    private int machineID;

    public Inhouse(String name, int partID, double price, int instock, int min, int max, int machineID) {
        super(name, partID, price, instock, min, max);
        this.machineID = machineID;
    }

    //sets the machine id
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    //gets the machine id
    public int getMachineID() {
        return machineID;
    }
}
