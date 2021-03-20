package com.example.battleship.application;

import javafx.scene.Parent;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.AnchorPane;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;

import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.net.URISyntaxException;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import com.example.battleship.model.Model;
import com.example.battleship.controller.Controller;


/*
 * Class with the GUI for the application.
 * The GUI is constructed and its attributes and behavior are defined.
 */
@SuppressWarnings("unchecked")
public class View {

	
	private TableView<List<ImageView>> table;
	private TextField guessTxt;
	private Text messageTxt;
	private Button fireBtn;
	private Button resetBtn;
	private VBox rootVbox;
	private Controller controller;
	
	private static final String SHIP_ICON = "ship.png";
	private static final String MISS_ICON = "miss.png";

	
	/*
	 * Constructor. Builds the GUI for the application.
	 */
	public View() {
		
		// Text message setup
		
		messageTxt = new Text();
		messageTxt.setFill(Color.FIREBRICK);
		messageTxt.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		messageTxt.setText("");
		VBox.setMargin(messageTxt, new Insets(10.0));
		
		// Game table and row number table setup
		
		table = new TableView<List<ImageView>>();
		table.setId("gametable");
		table.setStyle("-fx-border-color: white;");
		table.setPrefWidth(800);
		table.setEditable(false);
		table.setSelectionModel(null);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		for (int i = 0; i < Model.boardSize; i++) {
			TableColumn<List<ImageView>, ImageView> column = new TableColumn<>("");
			final int colIx = i;
			column.setCellValueFactory(cellData -> 
				new SimpleObjectProperty(cellData.getValue().get(colIx)));
			column.setMaxWidth(1f * Integer.MAX_VALUE * 14);
			table.getColumns().add(column);
		}

		table.setItems(getInitialTableData());		

		HBox tablesHbox = new HBox();		
		tablesHbox.setStyle("-fx-border-color: white;");
		tablesHbox.setPrefHeight(365);
		tablesHbox.getChildren().addAll(getRowNumberTable(), table);
		

		// Column number table setup

		TableView<List<String>> colNumTable = getColumnNumberTable();
		AnchorPane colNumTableAnchor = new AnchorPane();
        AnchorPane.setLeftAnchor(colNumTable, 50.0);
        colNumTableAnchor.getChildren().add(colNumTable);

		
		// Guess entry and buttons setup
		
		guessTxt = new TextField();
		guessTxt.setStyle("-fx-font-size: 18px;");
		guessTxt.setMinHeight(30.0);
		guessTxt.setPrefColumnCount(10);
		guessTxt.setPromptText("Enter guess (e.g., B2)");
		guessTxt.setTooltip(new Tooltip("Guess is a letter (A-G) followed by number (0-6)"));
		
		fireBtn = new Button("_Fire");
		fireBtn.setStyle("-fx-font-size: 16px;");
		fireBtn.setOnAction(actionEvent -> fireButtonListener());
		fireBtn.setTooltip(new Tooltip("Enter guess and click fire"));
		
		resetBtn = new Button("_Reset");
		resetBtn.setStyle("-fx-font-size: 16px;");
		resetBtn.setOnAction(actionEvent -> resetButtonListener());
		resetBtn.setTooltip(new Tooltip("Reset the game to start again"));
		
		HBox hbox = new HBox(10);
		hbox.setStyle("-fx-border-color: white;");
		hbox.getChildren().addAll(guessTxt, fireBtn, resetBtn);
		AnchorPane toolBarAnchor = new AnchorPane();
        AnchorPane.setRightAnchor(hbox, 0.0);
        toolBarAnchor.getChildren().add(hbox);		
		VBox.setMargin(toolBarAnchor, new Insets(10.0));

		
		// Main layout vbox setup
		
		rootVbox = new VBox();
		rootVbox.setStyle("-fx-border-color: white;");		
		rootVbox.getChildren()
				.addAll(messageTxt, tablesHbox, colNumTableAnchor, toolBarAnchor);
	}

	/*
	 * The Table component used for making the column titles A, B, C, ...
	 */	
	private TableView<List<String>> getRowNumberTable() {
	
		TableView<List<String>> t = new TableView<>();
		t.setStyle("-fx-border-color: white;");
		t.setId("rownumbertable");
		t.setPrefWidth(50);
		t.setEditable(false);
		t.setSelectionModel(null);
		t.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<List<String>, String> column = new TableColumn<>("");
		final int colIx = 0;
		column.setCellValueFactory(cellData -> 
			new SimpleStringProperty(cellData.getValue().get(colIx)));
		t.getColumns().add(column);
		
		List<List<String>> initialData = new ArrayList<>();

		for (int i = 0; i < Model.boardSize; i++) {		
			List<String> row = Arrays.asList(Controller.ALPHABETS_ARRAY[i]);			
			initialData.add(row);
		}

		t.setItems(FXCollections.observableArrayList(initialData));	
		return t;
	}

	/*
	 * The Table component used for making the column titles 0, 1, 2, ...
	 */	
	private TableView<List<String>> getColumnNumberTable() {
	
		TableView<List<String>> t = new TableView<>();		
		t.setStyle("-fx-border-color: white;");
		t.setId("columnnumbertable");
		t.setPrefWidth(800);
		t.setPrefHeight(50);
		t.setEditable(false);
		t.setSelectionModel(null);
		t.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		for (int i = 0; i < Model.boardSize; i++) {
			TableColumn<List<String>, String> column = new TableColumn<>("");
			final int colIx = i;
			column.setCellValueFactory(cellData -> 
				new SimpleStringProperty(cellData.getValue().get(colIx)));
			column.setMaxWidth(1f * Integer.MAX_VALUE * 14);
			t.getColumns().add(column);
		}
		
		List<List<String>> initialData = new ArrayList<>();

		for (int i = 0; i < 1; i++) {		
			List<String> row = new ArrayList<>();			
			for (int j = 0; j < Model.boardSize; j++) {
				row.add(Integer.toString(j));
			}
			initialData.add(row);
		}

		t.setItems(FXCollections.observableArrayList(initialData));
		return t;
	}	
	
	/*
	 * Initializes the controller.
	 */
    public void init() {
    	
		controller = new Controller(this);
	}
	
	/*
	 * Returns the view to the primary stage to build the GUI for this application.
	 */
	public Parent getView() {
		
		return rootVbox;
    }

	/*
	 * Listener for the fire button press action.
	 * This initiates the actions to update the table with hit or miss
	 * for an input guess.
	 */
	private void fireButtonListener() {
		
		controller.processGuess(guessTxt.getText());
	}
	
	
	/*
	 * The reset menu item's action listener.
	 * Resets the game and its model data.
	 * Initializes the table and the table model for another game.
	 */
	private void resetButtonListener() {

		init();
		table.setItems(getInitialTableData());
		table.refresh();
		displayMessage("Game is reset, start new game");
		guessTxt.requestFocus();
		guessTxt.setText("");
		fireBtn.setDisable(false);
	}
	
	/*
	 * Displays the alert dialog with a provided message.
	 */
	public void alertDialog(String msg) {
	
		Alert alert = new Alert(AlertType.NONE);
		alert.setTitle("Battleship");
		alert.getDialogPane().getButtonTypes().add(ButtonType.OK);
		alert.setContentText(msg);
		alert.show();
		
		guessTxt.selectAll();
		guessTxt.requestFocus();
	}

	/*
	 * Displays a text message about the game actions.
	 */
	public void displayMessage(String msg) {
	
		messageTxt.setText(msg);
		guessTxt.requestFocus();
		guessTxt.selectAll();
	}

	/*
	 * Updates the table cell with a ship image or miss image based upon the fired
	 * guess of hit or miss.
	 */	
	public void displayHitOrMiss(String guess, String hitOrMiss) {
	
		int row = Integer.valueOf(guess.substring(0, 1));
		int col = Integer.valueOf(guess.substring(1));

		String imageStr;
		
		switch(hitOrMiss) {
			case "HIT": imageStr = SHIP_ICON;
						break;
			case "MISS": imageStr = MISS_ICON;
						break;
			default: 
				throw new IllegalArgumentException("Cell can only have Hit or Miss: " +
													hitOrMiss);
		}
		
		ImageView imageView = new ImageView();
		
		try {		
			imageView.setImage(new Image(getClass().getResource(imageStr).toURI().toString()));
		} 
		catch (URISyntaxException e) {
			
			System.out.println("# An error occurred regarding the image file used with hit or miss.");
			System.out.println("# Verify the image files can be properly accesssed.");
			throw new RuntimeException(e);
		}
				
		ObservableList<List<ImageView>> tableData = table.getItems();
		List<ImageView> rowData = tableData.get(row);
		rowData.set(col, imageView);		
		tableData.set(row, rowData);
		table.setItems(tableData);
		table.refresh();
	}
	
	/*
	 * Actions after all the ships are sunk (game is complete).
	 */	
	public void gameOver() {
	
		fireBtn.setDisable(true);
		resetBtn.requestFocus();
	}
	
	/*
	 * Builds and returns the initial table data.
	 */
	private ObservableList<List<ImageView>> getInitialTableData() {

		List<List<ImageView>> initialData = new ArrayList<>();

		for (int i = 0; i < Model.boardSize; i++) {		
			List<ImageView> row = new ArrayList<>();			
			for (int j = 0; j < Model.boardSize; j++) {
				row.add(new ImageView());
			}
			initialData.add(row);
		}

		return FXCollections.observableArrayList(initialData);
	}	
	
}
