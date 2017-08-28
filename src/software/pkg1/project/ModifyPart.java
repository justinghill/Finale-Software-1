/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.pkg1.project;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
public class ModifyPart {

    private Stage stage;
    private Scene scene;
    private ObservableList<Part> part;
    private ObservableList<Part> test;
    private Main main;
    private Part modifyPart;
    private Inhouse inHouse;
    private Outsourced outSourced;
    private String name;
    private String compName;

    ModifyPart(Inhouse inHouse, Main main) {

        //modify part if it is inHouse
        modifyPart = inHouse;
        this.inHouse = inHouse;
        this.main = main;
        stage = new Stage();
        scene = new Scene(modifyGrid(), 500, 350);

    }

    ModifyPart(Outsourced outSourced, Main main) {
        //modify part if it is outsource
        modifyPart = outSourced;
        this.outSourced = outSourced;
        this.main = main;
        stage = new Stage();
        scene = new Scene(modifyGrid(), 500, 350);
    }

    public GridPane modifyGrid() {

        // Adds a grid to the Add part Page
        Label modifyPart = new Label("Modify Part");
        modifyPart.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        modifyPart.setId("addPart");

        //Screen Set up
        Label id = new Label("ID");
        Label name = new Label("Name");
        Label inv = new Label("Inv");
        Label price = new Label("Price/Cost");
        Label max = new Label("Max");
        Label min = new Label("Min");
        Label companyName = new Label("Company Name");
        Label machineID = new Label("Machine ID");

        //text fields that grab the modified part's informatioin
        TextField idField = new TextField("" + getMod().getPartID());
        TextField nameField = new TextField(getMod().getName());

        TextField invField = new TextField("" + getMod().getInstock());
        TextField priceField = new TextField("" + getMod().getPrice());
        TextField maxField = new TextField("" + getMod().getMax());
        TextField minField = new TextField("" + getMod().getMin());

        TextField companyField = new TextField();
        TextField machineField = new TextField();

        //if it's an inhouse it gets maching id
        if (getMod() instanceof Inhouse) {
            machineField.setText("" + getInHouse().getMachineID());

        } else {
            //if it's outsourced 
            companyField.setText(getOutSourced().getCompanyName());

        }

        GridPane add = new GridPane();
        // add Labels
        add.setVgap(10);
        add.setHgap(5);

        //add.add(modifyPart,1,1);
        add.add(id, 2, 2);
        add.add(name, 2, 3);
        add.add(inv, 2, 4);
        add.add(price, 2, 5);
        add.add(max, 2, 6);

        // creates a HBox to house maxField and min
        HBox hbox = new HBox();
        hbox.getChildren().addAll(maxField, min);
        hbox.setSpacing(20);

        //add Fields
        add.add(idField, 3, 2);
        idField.setEditable(false);
        idField.setId("idField");
        add.add(nameField, 3, 3);
        add.add(invField, 3, 4);
        add.add(priceField, 3, 5);
        add.add(minField, 4, 6);

        //set Prefered width
        minField.setPrefWidth(80);
        maxField.setPrefWidth(80);
        companyField.setPrefWidth(100);
        machineField.setPrefWidth(100);
        id.setPrefWidth(125);

        //add hbox
        add.add(hbox, 3, 6);

        //HBox for the bottom row. This will be affected by the radio button hotbox
        HBox bottom = new HBox();

        //creates Hbox to house the radio buttons on the top row
        HBox radio = new HBox();
        RadioButton inHouseRB = new RadioButton("In-House");
        //outsourced radio button adds companyName and companyField
        RadioButton outSourcedRB = new RadioButton("Outsourced");
        //if the modified part is an inhouse the inhouse radio button is selected
        if (getMod() instanceof Inhouse) {
            inHouseRB.setSelected(true);
        }
        add.add(machineID, 2, 7);
        bottom.getChildren().add(machineField);
        //inhouse radiobutton adds machineID and machineField
        inHouseRB.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                add.getChildren().remove(companyName);
                bottom.getChildren().remove(companyField);
                add.add(machineID, 2, 7);

                bottom.getChildren().add(machineField);

            }
        });

        //if the modified part is outsourced the outsourced radio button is selected
        if (getMod() instanceof Outsourced) {
            outSourcedRB.setSelected(true);

            //sets up the screen for outsourced
            add.getChildren().remove(machineID);
            bottom.getChildren().remove(machineField);
            add.add(companyName, 2, 7);
            bottom.getChildren().add(companyField);

        }
        // addss information for outsourced
        outSourcedRB.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                add.getChildren().remove(machineID);
                bottom.getChildren().remove(machineField);
                add.add(companyName, 2, 7);
                bottom.getChildren().add(companyField);

            }
        });

        //sets up save button that collects info from screen and adds it to the part
        Button save = new Button();
        save.setText("Save");
        save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                String textNameField = nameField.getText().toString();
                int textInvField = Integer.parseInt(invField.getText());
                double textPriceField = Double.parseDouble(priceField.getText());
                int textMaxField = Integer.parseInt(maxField.getText());
                int textMinField = Integer.parseInt(minField.getText());

                IOException e = new IOException();

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

                if (inHouseRB.isSelected()) {

                    int textMachineField = Integer.parseInt(machineField.getText());

                    getInHouse().setName(textNameField);
                    getInHouse().setInstock(textInvField);
                    getInHouse().setPrice(textPriceField);
                    getInHouse().setMax(textMaxField);
                    getInHouse().setMin(textMinField);
                    getInHouse().setMachineID(textMachineField);

                } else if (outSourcedRB.isSelected()) {

                    String textCompanyField = companyField.getText();

                    getOutSourced().setName(textNameField);
                    getOutSourced().setInstock(textInvField);
                    getOutSourced().setPrice(textPriceField);
                    getOutSourced().setMax(textMaxField);
                    getOutSourced().setMin(textMinField);
                    getOutSourced().setCompanyName(textCompanyField);

                }
                getStage().hide();

                getMain().getPartTable().refresh();
                getMain().getPStage().show();

            }
        });
        //cancels all info
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
                        //closes out the whole screne
                        stage.hide();
                        getStage().hide();
                        getMain().getPartTable().refresh();
                        getMain().getPStage().show();

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

        //hbox for buttons
        HBox button = new HBox(20);
        button.getChildren().addAll(save, cancel);
        button.setAlignment(Pos.CENTER);
        add.add(button, 3, 8, 4, 8);

        //makes the screen correct for outsourced
        outSourcedRB.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                add.getChildren().remove(machineID);
                bottom.getChildren().remove(machineField);

                add.add(companyName, 2, 7);
                bottom.getChildren().add(companyField);

            }
        });

        //inhouse radio button adds machineID and machineField
        inHouseRB.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                add.getChildren().remove(companyName);
                bottom.getChildren().remove(companyField);

                add.add(machineID, 2, 7);
                bottom.getChildren().add(machineField);

            }
        });

        //sets up radio button and positions them
        ToggleGroup radioButton = new ToggleGroup();
        inHouseRB.setToggleGroup(radioButton);
        outSourcedRB.setToggleGroup(radioButton);
        radio.getChildren().addAll(inHouseRB, outSourcedRB);
        radio.setAlignment(Pos.CENTER);
        radio.setSpacing(20);
        HBox topLabel = new HBox();
        Pane topSpace = new Pane();
        topSpace.setMinWidth(100);
        topLabel.getChildren().addAll(modifyPart, topSpace, radio);
        add.add(topLabel, 1, 1, 5, 1);
        add.add(bottom, 3, 7);

        return add;
    }
    //gets the scene

    public Scene getScene() {
        return scene;
    }
    //gets stage

    public Stage getStage() {
        return stage;
    }
    //sets the observable list for parts

    public void setList(ObservableList<Part> part) {
        this.part = part;
    }

    //gets the observable list for  the arts
    public void setPartList(ObservableList<Part> part) {

        test = FXCollections.observableArrayList(part);
        this.part = test;

    }
    //get's part based on the id

    public Part getPart(int partID) {
        return part.get(partID - 1);
    }
    //sets the modify part to a part and returns the modify part
    //  public Part getModify(Part part){
    //     this.modifyPart = part;

    //      return modifyPart;
    //  }
    //sets the inhouse
    public void setInHouse(Inhouse inHouse) {
        this.inHouse = inHouse;
    }
    //gets inhouse

    public Inhouse getInHouse() {
        return inHouse;
    }
    //sets outsourced

    public void setOutSourced(Outsourced outSourced) {
        this.outSourced = outSourced;
    }
    //gets outsouced

    public Outsourced getOutSourced() {
        return outSourced;
    }
    //sets the name

    public void setName(String name) {
        this.name = name;
    }
    //gets the name 

    public String getName() {
        return name;
    }

    //gets modified part
    public Part getMod() {
        return modifyPart;
    }
    //gets the main object

    public Main getMain() {
        return main;
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
