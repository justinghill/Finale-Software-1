/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.pkg1.project;

import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author jhill
 */
public class ModifyProduct {

    private Stage stage;
    private Scene scene;
    private ObservableList<Product> product;
    private ObservableList<Part> part;
    private ObservableList<Part> test;
    private Main main;
    private Product modifyProduct;
    private TableView<Part> parts;
    private ObservableList productParts;
    private TableView<Part> partsProduct;

    ModifyProduct(Main main, Product product) {
        this.main = main;
        modifyProduct = product;
        productParts = FXCollections.observableArrayList(modifyProduct.getPart());

        //Screen Set up
        Label id = new Label("ID");
        Label name = new Label("Name");
        Label inv = new Label("Inv");
        Label price = new Label("Price");
        Label max = new Label("Max");
        Label min = new Label("Min");

        //text fields get information for the moidified product
        TextField idField = new TextField("" + getMod().getProductID());
        TextField nameField = new TextField("" + getMod().getName());
        TextField invField = new TextField("" + getMod().getInstock());
        TextField priceField = new TextField("" + getMod().getPrice());
        TextField maxField = new TextField("" + getMod().getMax());
        TextField minField = new TextField("" + getMod().getMin());

        GridPane left = new GridPane();
        // add Labels
        left.setVgap(10);
        left.setHgap(5);
        left.add(id, 2, 2);
        left.add(name, 2, 3);
        left.add(inv, 2, 4);
        left.add(price, 2, 5);
        left.add(max, 2, 6);

        // creates a HBox to house maxField and min
        HBox hbox = new HBox();
        hbox.getChildren().addAll(maxField, min);
        hbox.setSpacing(20);

        // hbox for inventory
        HBox invBox = new HBox();
        invBox.getChildren().add(invField);

        //hbox for price field
        HBox priceBox = new HBox();
        priceBox.getChildren().add(priceField);

        //add Fields
        left.add(idField, 3, 2);
        idField.setEditable(false);
        idField.setId("idField");
        left.add(nameField, 3, 3);
        left.add(invBox, 3, 4);
        left.add(priceBox, 3, 5);
        left.add(minField, 4, 6);

        //set Prefered width
        minField.setPrefWidth(80);
        maxField.setPrefWidth(80);

        id.setPrefWidth(125);
        invField.setPrefWidth(80);
        priceField.setPrefWidth(80);
        //add hbox
        left.add(hbox, 3, 6);

        // adds a HBox to the main page BorderPane
        HBox hboxTop = new HBox();
        Label titleLabel = new Label("Modify Product");
        hboxTop.setPadding(new Insets(50, 50, 50, 50));
        TextField productTF = new TextField();

        titleLabel.setId("titleLabel");

        // search part buttons
        Button searchProduct = new Button();
        searchProduct.setText("Search");
        searchProduct.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                //Search for Part Id then selects the part
                int partField = Integer.parseInt(productTF.getText());

                int i = 0;
                while (i < main.getPartList().size()) {
                    if (main.getPartList().get(i).getPartID() == partField) {
                        getPartTableTop().getSelectionModel().select(i);

                        break;
                    } else {

                        //adds one to the count
                        i++;
                    }
                }

            }
        });

        //sets up spaces for the top hbox
        Pane spacer = new Pane();
        spacer.setMinWidth(800);
        Pane spacer2 = new Pane();
        spacer2.setMinWidth(25);
        //adds title and search product to the top hbox
        hboxTop.getChildren().addAll(titleLabel, spacer, searchProduct, spacer2, productTF);

        //sets up right grid
        VBox right = new VBox();

        setPartTableTop();
        // HBox for the top grid
        HBox top = new HBox();
        Pane spacePart = new Pane();
        spacePart.setMinWidth(200);
        top.getChildren().addAll(getPartTableTop(), spacePart);

        //Hbox for the add button
        HBox addButton = new HBox();

        //adds parts to the product based on their class
        Button addProduct = new Button();
        addProduct.setText("Add");
        addProduct.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                ObservableList selection = getPartTableTop().getSelectionModel().getSelectedItems();

                if (selection.get(0) instanceof Inhouse) {
                    Inhouse type = (Inhouse) selection.get(0);

                    productParts.add(type);

                } else if (selection.get(0) instanceof Outsourced) {

                    Outsourced type = (Outsourced) selection.get(0);
                    productParts.add(type);

                }

            }
        });

        // HBox for the bottom grid
        HBox bottom = new HBox();
        setPartTable();
        bottom.getChildren().add(getPartTable());

        //delete button
        Button deleteProduct = new Button();
        deleteProduct.setText("Delete");
        deleteProduct.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {  //Sets stage for confirm screen
                Stage stage = new Stage();
                //hbox to help center
                HBox root = new HBox();
                Pane space = new Pane();
                space.setMinWidth(100);
                //top pane so it's more in the middle
                Pane topSpace = new Pane();
                topSpace.setMinHeight(100);
                //vbox to stack
                VBox connect = new VBox();
                //text
                Text action = new Text("You are about to Delete");
                Text instructions = new Text("Press Yes to continue, or No to cancel");
                //yes button to confirm
                Button yes = new Button();
                yes.setText("Yes");
                yes.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        stage.hide();
                        ///removes the item you selected
                        int selection = getPartTable().getSelectionModel().getSelectedIndex();
                        getPartTable().getItems().remove(selection);

                    }
                });
                //no button to cancel
                Button no = new Button();
                no.setText("No");
                no.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {

                        stage.hide();

                    }
                });

                //organizes the stage
                HBox buttons = new HBox();
                Pane buttonSpace = new Pane();
                buttonSpace.setMinWidth(10);
                buttons.getChildren().addAll(yes, buttonSpace, no);
                connect.getChildren().addAll(topSpace, action, instructions, buttons);
                root.getChildren().addAll(space, connect);
                Scene yesNo = new Scene(root, 500, 500);

                stage.setScene(yesNo);
                stage.show();

            }
        });

        // space for the right side of the hbox
        Pane spacerRight = new Pane();
        spacerRight.setMinWidth(400);
        //adds all the items for the add button
        addButton.getChildren().addAll(spacerRight, addProduct);

        //spacers for the right border pane
        Pane spacerAddButton = new Pane();
        spacerAddButton.setMinHeight(10);

        Pane spacerGrid = new Pane();
        spacerGrid.setMinHeight(10);

        Pane spacerDelete = new Pane();
        spacerDelete.setMinHeight(10);

        Pane spaceDeleteButton = new Pane();
        spaceDeleteButton.setMinWidth(400);

        HBox deletButton = new HBox();
        deletButton.getChildren().addAll(spaceDeleteButton, deleteProduct);
        //adds all thr right border pane, add button , gride and delete button
        right.getChildren().addAll(top, spacerAddButton, addButton, spacerGrid, bottom, spacerDelete, deletButton);

        //Sets up bototm of border pane
        HBox veryBottom = new HBox();
        // buttons
        Button save = new Button();
        save.setText("Save");
        save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                //creates the exption
                IOException e = new IOException();
                //sets up a running total for cost. to use in error message
                double cost = 0;
                //refresh top table
                getPartTableTop().refresh();
                // takes info in text fields and applies it to product
                String textNameField = "0";
                int textInvField = 0;
                double textPriceField = 0;
                int textMaxField = 0;
                int textMinField = 0;

                //if the fields are left as the default then we send an error
                try {
                    textNameField = nameField.getText().toString();

                    if (textNameField.equals("Product Name") || textNameField.equals("") || textNameField.equals("0")) {

                        throw e;
                    } else {

                    }
                } catch (IOException ex) {
                    //Logger.getLogger(AddPart.class.getName()).log(Level.SEVERE, null, ex);
                    errorScreen("Enter a Product Name");

                    return;
                }
                //test to see if a number has been entered into the inventory field then adds an error if one hasn't
                try {
                    textInvField = Integer.parseInt(invField.getText());

                    if (textInvField == 0) {

                        throw e;
                    } else {

                    }
                } catch (IOException | NumberFormatException ex) {
                    //Logger.getLogger(AddPart.class.getName()).log(Level.SEVERE, null, ex);
                    errorScreen("Enter Inventory");

                    return;
                }

                //test to see if a number has been entered into the price field then adds an error if one hasn't
                try {
                    textPriceField = Double.parseDouble(priceField.getText());

                    if (textPriceField == 0) {

                        throw e;
                    } else {

                    }
                } catch (IOException | NumberFormatException ex) {
                    //Logger.getLogger(AddPart.class.getName()).log(Level.SEVERE, null, ex);
                    errorScreen("Enter Cost");

                    return;
                }

                //test to see if a number has been entered into the max field then adds an error if one hasn't
                try {
                    textMaxField = Integer.parseInt(maxField.getText());

                    if (textMaxField == 0) {

                        throw e;
                    } else {

                    }
                } catch (IOException | NumberFormatException ex) {
                    //Logger.getLogger(AddPart.class.getName()).log(Level.SEVERE, null, ex);
                    errorScreen("Enter Max");

                    return;
                }
                //test to see if a number has been entered into the min field then adds an error if one hasn't
                try {
                    textMinField = Integer.parseInt(minField.getText());

                    if (textMinField == 0) {

                        throw e;
                    } else {

                    }
                } catch (IOException | NumberFormatException ex) {
                    //Logger.getLogger(AddPart.class.getName()).log(Level.SEVERE, null, ex);
                    errorScreen("Enter Min");

                    return;
                }

                // checks to see if the max field is greater thanhe min
                try {
                    if (textMinField > textMaxField || textMaxField < textMinField) {
                        throw e;
                    } else {

                    }
                } catch (IOException ex) {
                    //Logger.getLogger(AddPart.class.getName()).log(Level.SEVERE, null, ex);
                    errorScreen("Min can not be greater than Max");

                    return;
                }

                //runs exception to test and see if Inventory field is smaller then max, and greater than min
                try {
                    if (textInvField > textMaxField || textInvField < textMinField) {
                        throw e;
                    } else {

                    }
                } catch (IOException ex) {
                    //Logger.getLogger(AddPart.class.getName()).log(Level.SEVERE, null, ex);
                    errorScreen("Inventory must be less than Max or greater than Min");

                    return;
                }
                //test to see if observable liset is empty. If its empty an error is thrown
                try {
                    if (productParts.isEmpty()) {
                        throw e;

                    } else {

                    }
                } catch (IOException ex) {
                    //Logger.getLogger(AddPart.class.getName()).log(Level.SEVERE, null, ex);
                    errorScreen("Every Product needs a Part");

                    return;
                }

                try {
                    //count for arraylist
                    int a = 0;
                    //sets up count
                    int i = 0;

                    //creats an array list of parts that you can then use to search
                    ArrayList<Part> temp = new ArrayList();

                    while (a < productParts.size()) {
                        temp.add((Part) productParts.get(i));
                        a++;
                    }

                    //runs a loop to go through whole list. then grabs inventory and cost. Adds up total cost
                    while (i < temp.size()) {
                        int numOfParts = temp.get(i).getInstock();
                        double costOfParts = temp.get(i).getPrice();

                        cost = cost + (numOfParts * costOfParts);

                        i++;

                    }

                    if (cost > textPriceField) {

                        throw e;

                    } else {

                    }
                } catch (IOException ex) {
                    //Logger.getLogger(AddPart.class.getName()).log(Level.SEVERE, null, ex);
                    errorScreen("Price of product must be larger than $" + cost);

                    return;
                }

                modifyProduct.setName(textNameField);
                modifyProduct.setInstock(textInvField);
                modifyProduct.setPrice(textPriceField);
                modifyProduct.setMax(textMaxField);
                modifyProduct.setMin(textMinField);

                //adds product parts to the current product  
                if (productParts.size() > 0) {
                    int i = 0;
                    ArrayList<Part> temp = new ArrayList();
                    while (i < productParts.size()) {
                        temp.add((Part) productParts.get(i));
                        i++;
                    }
                    modifyProduct.setPart(temp);
                } else {
                    //ends the if else
                }
                //resets stage
                getStage().hide();
                getMain().getProductTable().refresh();
                getMain().redoMain();
            }
        });

        Button cancel = new Button();
        cancel.setText("cancel");
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //cancels the transaction

                //Sets stage for confirm screen
                Stage stage = new Stage();
                //hbox to help center
                HBox root = new HBox();
                Pane space = new Pane();
                space.setMinWidth(100);
                //top pane so it's more in the middle
                Pane topSpace = new Pane();
                topSpace.setMinHeight(100);
                //vbox to stack
                VBox connect = new VBox();
                //text
                Text action = new Text("You are about to Cancel");
                Text cancel = new Text("If you do you will lose all information");
                Text instructions = new Text("Press Yes to continue, or No to cancel");
                //yes button to confirm
                Button yes = new Button();
                yes.setText("Yes");
                yes.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        //closes out the whole screen
                        //button cancels all data input
                        getMain().getPartTable().refresh();
                        getStage().hide();
                        getMain().set(parts);

                        getMain().redoMain();
                    }
                });
                //no button to cancel
                Button no = new Button();
                no.setText("No");
                no.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
//takes you back to the curren strcreen
                        stage.hide();

                    }
                });

                //organizes the stage
                HBox buttons = new HBox();
                Pane buttonSpace = new Pane();
                buttonSpace.setMinWidth(10);
                buttons.getChildren().addAll(yes, buttonSpace, no);
                connect.getChildren().addAll(topSpace, action, cancel, instructions, buttons);
                root.getChildren().addAll(space, connect);
                Scene yesNo = new Scene(root, 500, 500);

                stage.setScene(yesNo);
                stage.show();

            }
        });

        // sets spacer for the bottom of the borderpane
        Pane spacerBottom = new Pane();
        spacerBottom.setMinWidth(20);
        veryBottom.setPadding(new Insets(0, 0, 15, 1100));

        //adds save button
        veryBottom.getChildren().addAll(save, spacerBottom, cancel);

        //euts up borderpane
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(left);
        borderPane.setTop(hboxTop);
        borderPane.setRight(right);
        borderPane.setBottom(veryBottom);

        stage = new Stage();
        scene = new Scene(borderPane, 1500, 700);

    }

    //gets the scene
    public Scene getScene() {
        return scene;
    }
    //sets the scene

    public Stage getStage() {
        return stage;
    }

    //sets the top part table
    public void setPartTableTop() {
        parts = getMain().getPartTable();

        parts.setPrefWidth(500);
        parts.setPrefHeight(150);

    }

    //gets the top part table
    public TableView getPartTableTop() {

        return parts;
    }
    //sets the part table for the product

    public void setPartTable() {

        TableView<Part> parts = new TableView<>();

        TableColumn<Part, String> partID = new TableColumn<>("Part ID");
        partID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        TableColumn<Part, String> partName = new TableColumn<>("Part Name");
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Part, String> inventory = new TableColumn<>("Inventory Level");
        inventory.setCellValueFactory(new PropertyValueFactory<>("instock"));
        TableColumn<Part, String> price = new TableColumn<>("Price/Cost per Unit");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        parts.getColumns().addAll(partID, partName, inventory, price);
        partID.setPrefWidth(110);
        partName.setPrefWidth(110);
        inventory.setPrefWidth(110);
        price.setPrefWidth(150);
        parts.setPrefWidth(500);
        parts.setPrefHeight(150);

        parts.setItems(productParts);
        partsProduct = parts;

    }
    //gets the part table for the pruduct

    public TableView getPartTable() {
        return partsProduct;
    }
    //adds a product to the product list

    public void addProdcut(Product product) {

        this.product.add(product);

    }

    //sets the part list
    public void setPartList(ObservableList<Part> part) {

        test = FXCollections.observableArrayList(part);
        this.part = test;

    }
    //gets observagle list part

    public ObservableList<Product> getProductList() {

        return product;
    }
    // sets the observalbe list for the product

    public void setList(ObservableList<Product> product) {
        this.product = product;
    }

    //gets observagle list part
    public ObservableList<Part> getPartList() {

        return part;
    }
    //gets the main object

    public Main getMain() {
        return main;
    }
    //gets the modified product

    public Product getMod() {
        return modifyProduct;
    }

    //sets scene for error message
    public void errorScreen(String errorMessage) {
        StackPane root = new StackPane();
        Text error = new Text(errorMessage);
        error.setFill(Color.RED);
        root.getChildren().add(error);
        Scene message = new Scene(root, 500, 500);
        Stage stage = new Stage();
        stage.setScene(message);
        stage.show();
    }

}
