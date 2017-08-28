package software.pkg1.project;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jhill
 */
public class Product {

    private ArrayList<Part> part;
    private int productID;
    private String name;
    private double price;
    private int instock;
    private int min;
    private int max;

    public Product(String name, double price, int instock, int min, int max, int productID) {
        this.name = name;
        this.price = price;
        this.instock = instock;
        this.min = min;
        this.max = max;
        this.productID = productID;
        part = new ArrayList<>();

    }

    //sets name
    public void setName(String name) {
        this.name = name;
    }
    //gets nane

    public String getName() {
        return name;
    }

    //sets price
    public void setPrice(double price) {
        this.price = price;
    }

    //gets proce
    public double getPrice() {
        return price;
    }

    //sets inventory
    public void setInstock(int stock) {
        instock = stock;
    }
    //gets inventory

    public int getInstock() {
        return instock;
    }
    //sets min

    public void setMin(int min) {
        this.min = min;
    }
    //gets min

    public int getMin() {
        return min;
    }
    //sets sets max

    public void setMax(int max) {
        this.max = max;
    }
    //gets max

    public int getMax() {
        return max;
    }
    //adds part to the arraylist

    public void addPart(Part part) {

        this.part.add(part);
    }
    //removes part

    public boolean removePart(int partID) {

        part.add(partID - 1, null);
        return true;
    }
    //looks up part

    public Part lookupPart(int partID) {
        return part.get(partID - 1);

    }
    //updates part

    public void updatePart(int partID) {

    }
    //sets product id

    public void setProductID(int productID) {
        this.productID = productID;
    }
    //gets product id

    public int getProductID() {
        return productID;
    }

    //gets arraylist
    public ArrayList getPart() {
        return part;
    }
    //sets the arraylist

    public void setPart(ArrayList parts) {
        this.part = parts;
    }
}
