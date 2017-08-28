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
public abstract class Part {

    private String name;
    private int partID;
    private double price;
    private int instock;
    private int min;
    private int max;

    public Part(String name, int partID, double price, int instock, int min, int max) {
        this.name = name;
        this.partID = partID;
        this.price = price;
        this.instock = instock;
        this.min = min;
        this.max = max;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setInstock(int instock) {
        this.instock = instock;
    }

    public int getInstock() {
        return instock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMin() {
        return min;

    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void setPartID(int partID) {

        //create a part id num in javafx. then pull from that number to determine partid. make number grow
        this.partID = partID;
    }

    public int getPartID() {
        return partID;
    }
}
