package de.dhbw.horb.programmieren.projekt;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * SortingApp dient als Einstiegspunkt für die Anwendung.
 * 
 * @author Nick Lamparter
 */
public class SortingApp extends Application {
	
	/**
	 * Der Titel der App
	 */
	private static final String APPTITLE = "Sortier Algorithmen";

	@Override
	public void start(Stage primaryStage) {
	
		//Try to load Window
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(SortingApp.class.getResource("view/Window.fxml"));
			Scene scene = new Scene(loader.load());
			primaryStage.setScene(scene);		
			primaryStage.setTitle(APPTITLE);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	
}
