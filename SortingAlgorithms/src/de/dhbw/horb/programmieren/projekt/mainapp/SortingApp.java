package de.dhbw.horb.programmieren.projekt.mainapp;

import java.io.IOException;

import de.dhbw.horb.programmieren.projekt.io.ViewLoader;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * SortingApp dient als Einstiegspunkt für die Anwendung.
 * 
 * @author Alexander Lepper
 */
public class SortingApp extends Application {
	

	@Override
	public void start(Stage primaryStage) {
	
		//Try to load Window
		try {
			
			new ViewLoader(primaryStage).mainWindow();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	
}
