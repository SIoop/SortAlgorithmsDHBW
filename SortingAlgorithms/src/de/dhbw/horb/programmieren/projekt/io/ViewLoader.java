package de.dhbw.horb.programmieren.projekt.io;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Lädt das FXML-File für das Fenster.
 * @author Alexander Lepper
 *
 */
public class ViewLoader {

	/**
	 * Der Titel der App
	 */
	private static final String APPTITLE = "Sortier Algorithmen";
	/**
	 * Der Pfad der XML Datei, welche die View darstellt
	 */
	private static final String FXMLPATH = "file:src/de/dhbw/horb/programmieren/projekt/view/Window.fxml";
	private Stage windowStage;
	
	public ViewLoader(Stage stage) {
		
		this.windowStage = stage;
	}
	

	public void mainWindow() throws IOException {
		
		//Load FXML and add it to PrimaryStage
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(new URL(FXMLPATH));
		Scene scene = new Scene(loader.load());
		windowStage.setScene(scene);		
		windowStage.setTitle(APPTITLE);
		windowStage.show();

	}
}
