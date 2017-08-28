/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.pkg1.project;

import java.util.ArrayList;

/**
 *
 * @author jhill
 */
public class Inventory {

    private ArrayList<Product> products;

    public Inventory() {

        products = new ArrayList<>();
    }

    //adds a product to the inventory list, and upps the count by one
    public void addProduct(Product product) {
        int partNum = products.size() + 1;
        product.setProductID(partNum);
        products.add(product);

    }

    //deletes a product from the list
    public boolean removeProduct(int productNumber) {
        products.add(productNumber - 1, null);
        return true;
    }

    // looks up product
    public Product lookupProduct(int productNumber) {
        return products.get(productNumber - 1);

    }

    //updates product by product number
    public void updateProduct(int productNumber) {
        Product test = products.get(productNumber - 1);
    }
}
