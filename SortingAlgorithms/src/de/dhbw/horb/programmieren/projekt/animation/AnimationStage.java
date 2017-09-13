package de.dhbw.horb.programmieren.projekt.animation;

import de.dhbw.horb.programmieren.projekt.events.SortingEvent;
import de.dhbw.horb.programmieren.projekt.events.SortingListener;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * AnimationStage stellt das Fenster der Animation dar.
 * Bei jeder Änderung am zu sortierenden Array zeichnet sie das Array neu.
 * @author Alexander Lepper
 *
 */
public class AnimationStage extends Stage implements SortingListener {

	/**
	 * Der Graphic Context stellt die Funktionen zum Zeichnen der Säulen, welche die Zahlen repräsentieren, bereit.
	 */
	GraphicsContext gc;
	/**
	 * Die Breite des Fensters der Animation.
	 */
	int xSize = 1000;
	/**
	 * Die Höhe des Fensters der Animation.
	 */
	int ySize = 500;
	/**
	 * Der Faktor zur Anpassung der Säulengröße, falls die höchste Zahl größer als die Anzahl der verfügbaren Pixel ist.
	 */
	double yAxisMultiplier;
	/**
	 * Die Schrittweite der berücksichtigten Zahlen, falls es zu viele Zahlen sind um in dem Fenster dargestellt zu werden.
	 */
	double xSteps;
	/**
	 * Die höchste zu sortierende Zahl
	 */
	int max;
	Stage stage;
	
	public AnimationStage () {
		
			Canvas canvas = new Canvas(xSize, ySize);
			gc = canvas.getGraphicsContext2D();
			Group root = new Group();
			root.getChildren().add(canvas);
			this.setScene(new Scene(root));
	}
	
	/**
	 * Diese Methode wird bei jeder Änderung am Array aufgerufen und zeichnet die Oberfläche neu.
	 * Hierbei werden auch Änderungen an der Größe des Fensters beachtet.
	 */
	public void handle(SortingEvent event) {
		
		switch (event.getType()) {
		
		case SORTINGRUN:
			
			//Wenn ein Durchlauf der Sortierung startet, starte einen neuen Timer, der die Ansicht updatet
			Platform.runLater(() -> {
				
				AnimationStage.this.show();
				
				//Bestimme maximale Höhe einer Säule
				max = event.getCurrentArray()[0];
				for (int i : event.getCurrentArray()) if(i>max)max = i;
				
				
				new SortAnimationTimer(AnimationStage.this, event).start();
			});
			break;
				
		case SORTINGENDED:
			
			//Wenn Sortierung beendet ist, warte 1 Sekunde und schließe das Fenster dann
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e ) {
				e.printStackTrace();
			}
			Platform.runLater(() -> {close();});
			break;
		default:
			break;
		}
	}
}
