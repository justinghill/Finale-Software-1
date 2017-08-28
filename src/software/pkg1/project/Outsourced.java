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
public class Outsourced extends Part {

    private String companyName;

    public Outsourced(String name, int partID, double price, int instock, int min, int max, String companyName) {
        super(name, partID, price, instock, min, max);
        this.companyName = companyName;
    }
//gets company name

    public String getCompanyName() {
        return companyName;
    }

//sets company name
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
