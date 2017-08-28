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
import javafx.geometry.Pos;
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
public class Main {

    private Stage primaryStage;
    private AddPart addPart;
    private ModifyPart modifyPart;
    private AddProduct addProduct;
    private ModifyProduct modifyProduct;
    private Inventory inventory;
    private ObservableList<Part> part;
    private ObservableList<Product> product;
    private TableView<Part> parts;
    private TableView<Part> test;
    private TableView<Product> productTable;
    private int partID;
    private int productID;

    Main() {

        primaryStage = new Stage();

        inventory = new Inventory();
        part = FXCollections.observableArrayList();
        product = FXCollections.observableArrayList();
        partID = 0;
        productID = 0;
        parts = new TableView<>();

        ObservableList<Part> forProduct = part;

        addProduct = new AddProduct(getThis(), forProduct, getPartTable());
        setProductTable();
    }

    public void main() {

        // sets up the border pane
        BorderPane root = new BorderPane();

        root.setTop(addTop());
        root.setLeft(addLeftGrid());
        root.setRight(addRightGrid());
        root.setBottom(addBottom());
        setPartTableView();

        // creates the scene
        Scene scene = new Scene(root, 1200, 650);

        // NewStage.newStage(scene, "Main Screen");
        scene.getStylesheets().add(Software1Project.class.getResource("project1.css").toExternalForm());
        getPStage().setTitle("Main Screen");
        getPStage().setScene(scene);
        getPStage().show();

    }
    // Sets the mainscreen again

    public void redoMain() {
        BorderPane root = new BorderPane();

        root.setTop(addTop());
        root.setLeft(addLeftGrid());
        root.setRight(addRightGrid());
        root.setBottom(addBottom());

        // creates the scene
        Scene scene = new Scene(root, 1200, 650);

        // NewStage.newStage(scene, "Main Screen");
        scene.getStylesheets().add(Software1Project.class.getResource("project1.css").toExternalForm());
        getPStage().setTitle("Main Screen");
        getPStage().setScene(scene);
        getPStage().show();

    }

    // adds a Hbox to the bottom of the main page BorderPane
    public HBox addBottom() {
        HBox hbox = new HBox();
        Button exit = new Button();
        exit.setText("Exit");
        exit.setId("exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                getPStage().hide();
            }
        });
        hbox.setPadding(new Insets(0, 0, 12, 1100));
        hbox.getChildren().add(exit);

        return hbox;

    }
    // adds a HBox to the main page BorderPane

    public HBox addTop() {
        HBox hbox = new HBox();
        Label titleLabel = new Label("Inventory Managment System");
        hbox.setPadding(new Insets(50, 50, 50, 50));
        hbox.getChildren().add(titleLabel);
        titleLabel.setId("titleLabel");

        return hbox;
    }

    //adds the grid on the left side
    public GridPane addLeftGrid() {

        // Add part button, moves you to a new screen
        Button addPart = new Button();
        addPart.setText("Add");
        addPart.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                AddPart part = getAddPart();
                part = new AddPart();

                part.setPartList(getPartList());
                part.setMain(getThis());

                part.getScene().getStylesheets().add(Software1Project.class.getResource("project1.css").toExternalForm());
                part.getStage().setTitle("Add Part");

                part.getStage().setScene(part.getScene());
                part.getStage().show();
                getPStage().hide();

            }
        });

        // modify part buttons, moves you to a new screen
        Button modifyPart = new Button();
        modifyPart.setText("Modify");
        modifyPart.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                ObservableList selection = getPartTable().getSelectionModel().getSelectedItems();

                //checks to see if it is inhouse out outsourced and then adds to list
                if (selection.get(0) instanceof Inhouse) {
                    Inhouse type = (Inhouse) selection.get(0);
                    ModifyPart part = new ModifyPart(type, getThis());
                    part.getScene().getStylesheets().add(Software1Project.class.getResource("project1.css").toExternalForm());
                    part.getStage().setTitle("Modify Part");
                    part.getStage().setScene(part.getScene());
                    part.getStage().show();
                    getPStage().hide();
                } else if (selection.get(0) instanceof Outsourced) {

                    Outsourced type = (Outsourced) selection.get(0);
                    ModifyPart parts = new ModifyPart(type, getThis());

                    parts.getScene().getStylesheets().add(Software1Project.class.getResource("project1.css").toExternalForm());
                    parts.getStage().setTitle("Modify Part");
                    parts.getStage().setScene(parts.getScene());
                    parts.getStage().show();
                    getPStage().hide();
                }

            }
        });

        // delete part buttons
        Button deletePart = new Button();
        deletePart.setText("Delete");
        deletePart.setOnAction(new EventHandler<ActionEvent>() {
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
                        int selection = getPartTable().getSelectionModel().getSelectedIndex();
                        IOException e = new IOException();
                        Product test = (Product) getProductTable().getItems().get(selection);

                        try {
                            if (test.getPart().isEmpty()) {

                            } else {
                                throw e;
                            }
                        } catch (IOException ex) {
                            //Logger.getLogger(AddPart.class.getName()).log(Level.SEVERE, null, ex);
                            errorScreen("You cant delet Products that have Parts");

                            return;
                        }

                        //removes a product based on the selection
                        getPartTable().getItems().remove(selection);
                        // getProductTable().refresh();

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
        TextField partTF = new TextField();
        // search part button
        Button searchPart = new Button();
        searchPart.setText("Search");
        searchPart.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                //Search for Part Id then selects the part
                int partField = Integer.parseInt(partTF.getText());

                int i = 0;
                while (i < getPartList().size()) {
                    if (getPartList().get(i).getPartID() == partField) {
                        getPartTable().getSelectionModel().select(i);

                        break;
                    } else {
                        i++;
                    }
                }

            }
        });

        // Creates label for for the parts section
        Label partLabel = new Label("Parts");

        GridPane leftGrid = new GridPane();
        // grid attributes
        leftGrid.setId("leftGrid");
        leftGrid.setPrefHeight(200);
        leftGrid.setPrefWidth(550);
        leftGrid.setVgap(10);
        leftGrid.setHgap(9);
        leftGrid.setAlignment(Pos.CENTER);
        //sets up top hbox for the label and search part
        HBox top = new HBox();
        top.setPadding(new Insets(50, 50, 0, 0));
        Pane space = new Pane();
        space.setMinWidth(150);
        Pane space2 = new Pane();
        space2.setMinWidth(10);

        top.getChildren().addAll(partLabel, space, searchPart, space2, partTF);
        //sets up bottom hbox for the save button and modify part
        HBox bottom = new HBox();
        Pane space3 = new Pane();
        space3.setMinWidth(150);
        Pane space4 = new Pane();
        space4.setMinWidth(10);
        bottom.setPadding(new Insets(50, 50, 10, 0));
        bottom.getChildren().addAll(space3, addPart, space2, modifyPart, space4, deletePart);

        //organizes the left grid
        leftGrid.add(top, 1, 1, 14, 1);
        leftGrid.add(getPartTable(), 1, 3, 14, 8);
        leftGrid.add(bottom, 12, 25);

        partLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        return leftGrid;
    }

    //creats the right side of scene
    public GridPane addRightGrid() {

        Button addProduct = new Button();
        addProduct.setText("Add");
        addProduct.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //creates an observable list and adds that to the product screne
                ObservableList<Part> will = FXCollections.observableArrayList(getPartList());
                AddProduct test = new AddProduct(getThis(), will, getPartTable());

                test.getScene().getStylesheets().add(Software1Project.class.getResource("project1.css").toExternalForm());
                test.getStage().setTitle("Add Product");
                test.getStage().setScene(test.getScene());
                test.getStage().show();
                getPStage().hide();
            }

        });

        Button modifyProduct = new Button();
        modifyProduct.setText("Modify");
        modifyProduct.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //opens up the modify prodcut screen based on your selection from the bableview
                ObservableList selection = getProductTable().getSelectionModel().getSelectedItems();

                Product type = (Product) selection.get(0);

                ModifyProduct product = new ModifyProduct(getThis(), type);

                product.getScene().getStylesheets().add(Software1Project.class.getResource("project1.css").toExternalForm());
                product.getStage().setTitle("Modify Product");
                product.getStage().setScene(product.getScene());
                product.getStage().show();
                getPStage().hide();
            }
        });

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
                        int selection = getProductTable().getSelectionModel().getSelectedIndex();
                        IOException e = new IOException();
                        Product test = (Product) getProductTable().getItems().get(selection);

                        try {
                            if (test.getPart().isEmpty()) {

                            } else {
                                throw e;
                            }
                        } catch (IOException ex) {
                            //Logger.getLogger(AddPart.class.getName()).log(Level.SEVERE, null, ex);
                            errorScreen("You cant delet Products that have Parts");

                            return;
                        }

                        //removes a product based on the selection
                        getProductTable().getItems().remove(selection);
                        getProductTable().refresh();

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

        //search text fields
        TextField productTF = new TextField();
        Button searchProduct = new Button();
        searchProduct.setText("Search");
        searchProduct.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //searches through list to find product
                int productField = Integer.parseInt(productTF.getText());

                int i = 0;
                while (i < getProductList().size()) {
                    if (getProductList().get(i).getProductID() == productField) {
                        getProductTable().getSelectionModel().select(i);

                        break;
                    } else {
                        i++;
                    }
                }

            }
        });

        // add labels
        Label productLabel = new Label("Products");

        HBox top = new HBox();
        top.setPadding(new Insets(50, 50, 0, 0));
        Pane space = new Pane();
        space.setMinWidth(100);
        Pane space2 = new Pane();
        space2.setMinWidth(10);
        //sets up top right with product search and label
        top.getChildren().addAll(productLabel, space, searchProduct, space2, productTF);

        HBox bottom = new HBox();
        Pane space3 = new Pane();
        space3.setMinWidth(150);
        Pane space4 = new Pane();
        space4.setMinWidth(10);

        //sets up bottom of grid with add and modify buttons
        bottom.setPadding(new Insets(50, 50, 10, 0));
        bottom.getChildren().addAll(space3, addProduct, space2, modifyProduct, space4, deleteProduct);

        GridPane rightGrid = new GridPane();

        rightGrid.add(top, 1, 1, 14, 1);
        rightGrid.add(getProductTable(), 1, 3, 14, 8);
        rightGrid.add(bottom, 12, 25);

        productLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        // grid attributes
        rightGrid.setId("rightGrid");
        rightGrid.setPrefHeight(200);
        rightGrid.setPrefWidth(550);
        rightGrid.setVgap(10);
        rightGrid.setHgap(9);
        rightGrid.setAlignment(Pos.CENTER);

        return rightGrid;
    }
    //gets the part table

    public TableView getPartTable() {

        return parts;
    }

    // sets the part table
    public void setPartTableView() {

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
        parts.setPrefWidth(400);
        parts.setPrefHeight(150);
       

        parts.setItems(getPartList());

    }
//sets the product table

    public void setProductTable() {

        TableView<Product> product = new TableView<>();
        TableColumn<Product, String> productID = new TableColumn<>("Product ID");
        productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        TableColumn<Product, String> productName = new TableColumn<>("Product Name");
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Product, String> inventory = new TableColumn<>("Inventory Level");
        inventory.setCellValueFactory(new PropertyValueFactory<>("instock"));
        TableColumn<Product, String> price = new TableColumn<>("Price per Unit");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        product.getColumns().addAll(productID, productName, inventory, price);
        productID.setPrefWidth(120);
        productName.setPrefWidth(120);
        inventory.setPrefWidth(120);
        price.setPrefWidth(120);
        product.setPrefWidth(400);
        product.setPrefHeight(150);
      

        product.setItems(getProductList());
        productTable = product;

    }
//gets teh product table

    public TableView getProductTable() {
        return productTable;
    }
    //returns primaryStage

    public Stage getPStage() {
        return primaryStage;
    }

    //returns the add part object
    public AddPart getAddPart() {
        return addPart;
    }
    //returns the modify part object

    public ModifyPart getModifyPart() {
        return modifyPart;
    }

    //returns add product
    public AddProduct getAddProduct() {
        return addProduct;
    }

    //gets the modify product
    public ModifyProduct getModifyProduct() {
        return modifyProduct;
    }

    //gets this object
    public Main getMain() {
        return this;
    }
    // adds part 

    public void addPart(Part part) {

        this.part.add(part);

    }

    //gets observagle list part
    public ObservableList<Part> getPartList() {

        return part;
    }

    //adds product
    public void addProduct(Product product) {

        this.product.add(product);

    }

    //gets observable list product
    public ObservableList<Product> getProductList() {

        return product;
    }

    //adds one to the partId count
    public void addPartID() {
        partID++;
    }

    //get's part id
    public int getPartID() {
        return partID;
    }

    //adds one to the product id count
    public void addProductID() {
        productID++;
    }

    //gets product id
    public int getProductID() {
        return productID;
    }

    //gets this object
    public Main getThis() {
        return this;
    }

    //searches for a part from the list
    public Part getPartFromList(int partID) {
        return part.get(-1);
    }

    //sets the addProduct to a list
    public void setAddProduct(AddProduct product) {
        addProduct = product;
    }

    //sets a tableview
    public void set(TableView<Part> table) {
        parts = table;
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

    public void confirmScreen(String confirm) {
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
        Text action = new Text("You are about to " + confirm);
        Text instructions = new Text("Press Yes to continue, or No to cancel");
        //yes button to confirm
        Button yes = new Button();
        yes.setText("Yes");
        yes.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                stage.hide();

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
}
