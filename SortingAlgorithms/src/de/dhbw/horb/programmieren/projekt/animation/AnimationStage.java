package de.dhbw.horb.programmieren.projekt.animation;

import de.dhbw.horb.programmieren.projekt.events.EventType;
import de.dhbw.horb.programmieren.projekt.events.SortingEvent;
import de.dhbw.horb.programmieren.projekt.events.SortingListener;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
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
	private GraphicsContext gc;
	/**
	 * Die Breite des Fensters der Animation.
	 */
	private int xSize = 1000;
	/**
	 * Die Höhe des Fensters der Animation.
	 */
	private int ySize = 500;
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
		
		if(event.getType()==EventType.SORTINGRUN) {
			
			//Wenn ein Durchlauf der Sortierung startet, erzeuge neuen Timer, der in bestimmten Zeitintervallen die Säulen zeichnet.
			Platform.runLater(new Runnable () {

				@Override
				public void run() {
					AnimationStage.this.show();
					
					//Bestimme Maximale Höhe einer Säule
					max = event.getCurrentArray()[0];
					for (int i : event.getCurrentArray()) if(i>max)max = i;
					
					
					new AnimationTimer() {
						@Override
						public void handle(long arg0) {
							
							//Beende Timer wenn Fenster geschlossen ist
							if(!isShowing())this.stop();
							
							//Passe Höhe des Canvas an das Fenster an
							gc.getCanvas().setWidth(AnimationStage.this.getWidth());
							gc.getCanvas().setHeight(AnimationStage.this.getHeight());
							
							//Höhe und Breite für Berechnung des Fensters anhand der Säulengröße
							xSize = (int) gc.getCanvas().getWidth();
							ySize = (int) gc.getCanvas().getHeight();
							
							//Mache Säulen kleiner, falls größte Zahl größer als verfügbare Pixel
							yAxisMultiplier = ySize/(double)max;
					        
							//Bestimmte jede wievielte Zahl berücksichtigt werden soll, falls zu viele Zahlen für Breite
					        if(event.getCurrentArray().length > xSize ) xSteps = Math.ceil((double)event.getCurrentArray().length/((double)xSize));
					        else xSteps = 1;
							
					        //Falls weniger Säulen als Pixel in Breite, prüfe ob Säulen breiter gemacht werden können
							int barWidth;
							if(xSteps==1)barWidth = (xSize)/event.getCurrentArray().length;
							else barWidth = 1;
							
							//Zeichne weißen Hintergrund
							gc.setFill(Color.WHITE);
							gc.fillRect(0, 0, xSize, ySize);
							
							//Zeichne Säulen
							gc.setFill(Color.DEEPPINK);
							for(int i = 0; i < event.getCurrentArray().length; i += xSteps) {
								gc.fillRect((i/xSteps)*barWidth, ySize-(event.getCurrentArray()[i]*yAxisMultiplier), barWidth, event.getCurrentArray()[i]*yAxisMultiplier);
							}
						}
			    	}.start();
				}
				
			});
		}
		else if (event.getType()==EventType.SORTINGENDED){
			//Fenster soll für eine Sekunde offen bleiben, wenn die Sortierung beendet wurde.
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e ) {
				
			}
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					
					close();
				}
				
			});
		}
	}
}
