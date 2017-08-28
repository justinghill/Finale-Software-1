package software.pkg1.project;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jhill
 */
public class AddProduct {

    private Stage stage;
    private Scene scene;
    private ObservableList<Product> product;
    private ObservableList<Part> part;
    private ObservableList<Part> test;
    private Main main;
    private TableView<Part> parts;
    private BorderPane add;
    private ObservableList<Part> temp;
    private TableView<Part> productParts;

    AddProduct(Main main, ObservableList part, TableView parts) {
        this.main = main;
        add = new BorderPane();
        temp = FXCollections.observableArrayList();
        //Screen Set up
        Label id = new Label("ID");
        Label name = new Label("Name");
        Label inv = new Label("Inv");
        Label price = new Label("Price");
        Label max = new Label("Max");
        Label min = new Label("Min");

        //text fields for entering info
        TextField idField = new TextField("Auto Gen - Disabled");
        TextField nameField = new TextField("Product Name");
        TextField invField = new TextField("Inv");
        TextField priceField = new TextField("Price");
        TextField maxField = new TextField("Max");
        TextField minField = new TextField("Min");

        GridPane grid = new GridPane();
        // add Labels
        grid.setVgap(10);
        grid.setHgap(5);
        grid.add(id, 2, 2);
        grid.add(name, 2, 3);
        grid.add(inv, 2, 4);
        grid.add(price, 2, 5);
        grid.add(max, 2, 6);

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
        grid.add(idField, 3, 2);
        idField.setEditable(false);
        idField.setId("idField");
        grid.add(nameField, 3, 3);
        grid.add(invBox, 3, 4);
        grid.add(priceBox, 3, 5);
        grid.add(minField, 4, 6);

        //set Prefered width
        minField.setPrefWidth(80);
        maxField.setPrefWidth(80);

        id.setPrefWidth(125);
        invField.setPrefWidth(80);
        priceField.setPrefWidth(80);
        //add hbox
        grid.add(hbox, 3, 6);
        //hbox for the top of the page
        HBox topHBox = new HBox();
        Label titleLabel = new Label("Add Product");
        topHBox.setPadding(new Insets(50, 50, 50, 50));
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
                        i++;
                    }
                }

            }
        });
        //spacers for the hbox
        Pane spacer = new Pane();
        spacer.setMinWidth(800);
        Pane spacer2 = new Pane();
        spacer2.setMinWidth(25);
        //adds info to top hbox
        topHBox.getChildren().addAll(titleLabel, spacer, searchProduct, spacer2, productTF);
        //creates a vbox
        VBox right = new VBox();

        // HBox for the top grid
        HBox top = new HBox();
        Pane spacePart = new Pane();
        spacePart.setMinWidth(200);
        //sets the tableinfo for the top of the page
        setPartTableTop();
        //adds all the parts for top grid
        top.getChildren().addAll(getPartTableTop(), spacePart);

        //Hbox for the add button
        HBox addButton = new HBox();

        //adds parts to the product
        Button addProduct = new Button();
        addProduct.setText("Add");
        addProduct.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                ObservableList selection = getPartTableTop().getSelectionModel().getSelectedItems();
                //looks to see if the selected item is inhouse our outsourced and adds accordingly
                if (selection.get(0) instanceof Inhouse) {
                    Inhouse type = (Inhouse) selection.get(0);

                    temp.add(type);

                } else if (selection.get(0) instanceof Outsourced) {

                    Outsourced type = (Outsourced) selection.get(0);
                    temp.add(type);

                }

                setPartTable();
                getPartTable().refresh();

            }
        });

        // HBox for the bottom grid
        HBox bottom = new HBox();

        //make sure to set the table Dipshit
        setPartTable();
        bottom.getChildren().add(getPartTable());

        //delete button
        Button deleteProduct = new Button();
        deleteProduct.setText("Delete");
        deleteProduct.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

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
                Text action = new Text("You are about to Delete");
                Text instructions = new Text("Press Yes to continue, or No to cancel");
                //yes button to confirm
                Button yes = new Button();
                yes.setText("Yes");
                yes.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        stage.hide();
                        //deletes bottom item from tableview
                        int size = getPartTable().getItems().size();
                        getPartTable().getItems().remove(size - 1);

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

        //creates spacer to distance button from the left side
        Pane spacer1 = new Pane();
        spacer1.setMinWidth(400);
        //adds info to addbutton
        addButton.getChildren().addAll(spacer1, addProduct);
        //spcer for add button and the top of the page
        Pane spacerAddButton = new Pane();
        spacerAddButton.setMinHeight(10);
        //spacer for the grid and bottom grid
        Pane spacerGrid = new Pane();
        spacerGrid.setMinHeight(10);
        //spacer for the delet button and bottom grid
        Pane spacerDelete = new Pane();
        spacerDelete.setMinHeight(10);
        //spacer for the delete button and the buttons at the bottom of the page
        Pane spaceDeleteButton = new Pane();
        spaceDeleteButton.setMinWidth(400);
        //hbox for the delete button
        HBox deletButton = new HBox();
        deletButton.getChildren().addAll(spaceDeleteButton, deleteProduct);
        //adds all info to right vbox
        right.getChildren().addAll(top, spacerAddButton, addButton, spacerGrid, bottom, spacerDelete, deletButton);

        HBox veryBottom = new HBox();
        // buttons

        // Save button
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
                    if (temp.isEmpty()) {
                        throw e;

                    } else {

                    }
                } catch (IOException ex) {
                    //Logger.getLogger(AddPart.class.getName()).log(Level.SEVERE, null, ex);
                    errorScreen("Every Product needs a Part");

                    return;
                }

                try {
                    //sets up count
                    int i = 0;
                    //keeps a running total of cost
                    //double cost = 0;

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

                getMain().addProductID();
                int productID = getMain().getProductID();

                Product product = new Product(textNameField, textPriceField, textInvField, textMinField, textMaxField, productID);

                int i = 0;

                while (i < temp.size()) {

                    product.addPart(temp.get(i));
                    i++;
                }

                getMain().addProduct(product);
                getStage().hide();
                getMain().getPartTable().refresh();
                getMain().redoMain();

            }
        });
        //creates new cancel button 
        Button cancel = new Button();
        cancel.setText("cancel");
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

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
                        stage.hide();
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

        //adds all the info the borderPane
        Pane spacer4 = new Pane();
        spacer4.setMinWidth(20);
        veryBottom.setPadding(new Insets(0, 0, 15, 1100));
        veryBottom.getChildren().addAll(save, spacer4, cancel);
        add.setLeft(grid);
        add.setTop(topHBox);
        add.setRight(right);
        add.setBottom(veryBottom);
        stage = new Stage();
        scene = new Scene(add, 1500, 700);

    }

    //gets scene
    public Scene getScene() {
        return scene;
    }

    //sets stage
    public Stage getStage() {
        return stage;
    }

    //sets the view table for the top part of 
    public void setPartTableTop() {
        parts = getMain().getPartTable();

        parts.setPrefWidth(500);
        parts.setPrefHeight(150);

    }

    //gets the top table
    public TableView getPartTableTop() {

        return parts;
    }

    //sets the tableview for the bottom
    public void setPartTable() {

        TableView<Part> pParts = new TableView<>();
        TableColumn<Part, String> partID = new TableColumn<>("Part ID");
        partID.setCellValueFactory(new PropertyValueFactory<>("partID"));
        TableColumn<Part, String> partName = new TableColumn<>("Part Name");
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Part, String> inventory = new TableColumn<>("Inventory Level");
        inventory.setCellValueFactory(new PropertyValueFactory<>("instock"));
        TableColumn<Part, String> price = new TableColumn<>("Price/Cost per Unit");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        pParts.getColumns().addAll(partID, partName, inventory, price);
        partID.setPrefWidth(110);
        partName.setPrefWidth(110);
        inventory.setPrefWidth(110);
        price.setPrefWidth(150);
        pParts.setPrefWidth(500);
        pParts.setPrefHeight(150);

        pParts.setItems(temp);
        productParts = pParts;

    }

    //gets teh part table
    public TableView getPartTable() {
        return productParts;
    }

    //adds product to the product on the main page
    public void addProdcut(Product product) {

        this.product.add(product);

    }

    //sets the partlist
    public void setPartList(ObservableList<Part> part) {

        this.part = part;

    }
    //gets observagle list part

    public ObservableList<Product> getProductList() {

        return product;
    }

    //adds a product to the product list on the main page
    public void setList(ObservableList<Product> product) {
        this.product = product;
    }

    //gets observagle list part
    public ObservableList<Part> getPartList() {

        return part;
    }

    //pulls the main object into this object
    public void setMain(Main main) {
        this.main = main;
    }

    //gets main
    public Main getMain() {
        return main;
    }

    //returns the parts from the part list
    public TableView<Part> getPart() {

        return parts;
    }

    //sets the table view for parts
    public void setView(TableView<Part> parts) {
        this.parts = parts;

    }

    //returns part id
    public int getPartID() {
        return main.getPartID();
    }

    //sets observable list for tests
    public void test(ObservableList<Part> part) {

        test = part;
    }

    //gets the observable list for test
    public ObservableList<Part> getTest() {
        return test;
    }

    //adds parts to our temporary list
    public void addTemp(Part part) {
        temp.add(part);
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
