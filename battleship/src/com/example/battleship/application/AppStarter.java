package com.example.battleship.application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;


/*
 * Starts the application and displays the GUI.
 */
public class AppStarter extends Application{

	public static void main(String[] args) {

		Application.launch(args);

	}

	/*
	 * The main entry point for all JavaFX applications. This 
	 * method is called on the JavaFX Application Thread.
	 * Initializes the GameWindow.
	 */
    @Override
    public void start(Stage primaryStage) {
    	
		View gameWindow = new View();
		Parent mainView = gameWindow.getView();
		mainView.setStyle("-fx-background-color: white;");
		Scene scene = new Scene(mainView);
		scene.getStylesheets()
				.add(getClass().getResource("stylesheet.css").toExternalForm());
        primaryStage.setScene(scene); 
        primaryStage.setResizable(false);
        primaryStage.setTitle("Battleship");
        primaryStage.show();
		
		gameWindow.displayMessage("Welcome to the game!");
		gameWindow.init();
		mainView.requestFocus();  	
    }

}
