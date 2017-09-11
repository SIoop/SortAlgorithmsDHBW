package de.dhbw.horb.programmieren.projekt.sortingview;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * 
 * @author itmetzr
 *
 *Diese Klasse erzeugt die App. Sie enthält die main-Methode.
 */
public class SortingApp extends Application {
	
	private Scene scene;
	GUI form;

	@Override
	public void start(Stage primaryStage) {
		try {
			
			this.form = new GUI();
			this.scene = new Scene(form);

			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			scene.setOnDragOver(new ViewDragController(this));
			scene.setOnDragDropped(new ViewDragController(this));
			primaryStage.setTitle("Multithreaded Sorting App");
			primaryStage.setScene(scene);
			primaryStage.setResizable(true);
			primaryStage.show();
			form.getRbtnManuallyInput().setSelected(true);
			form.getRbtnQuickSort().setSelected(true);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public GUI getForm() {
		return form;
	}
	
}
