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
 * Bei jeder �nderung am zu sortierenden Array zeichnet sie das Array neu.
 * @author Alexander Lepper
 *
 */
public class AnimationStage extends Stage implements SortingListener {

	/**
	 * Der Graphic Context stellt die Funktionen zum Zeichnen der S�ulen, welche die Zahlen repr�sentieren, bereit.
	 */
	private GraphicsContext gc;
	/**
	 * Die Breite des Fensters der Animation.
	 */
	private int xSize = 1000;
	/**
	 * Die H�he des Fensters der Animation.
	 */
	private int ySize = 500;
	/**
	 * Der Faktor zur Anpassung der S�ulengr��e, falls die h�chste Zahl gr��er als die Anzahl der verf�gbaren Pixel ist.
	 */
	double yAxisMultiplier;
	/**
	 * Die Schrittweite der ber�cksichtigten Zahlen, falls es zu viele Zahlen sind um in dem Fenster dargestellt zu werden.
	 */
	double xSteps;
	/**
	 * Die h�chste zu sortierende Zahl
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
	 * Diese Methode wird bei jeder �nderung am Array aufgerufen und zeichnet die Oberfl�che neu.
	 * Hierbei werden auch �nderungen an der Gr��e des Fensters beachtet.
	 */
	public void handle(SortingEvent event) {
		
		if(event.getType()==EventType.SORTINGRUN) {
			
			//Wenn ein Durchlauf der Sortierung startet, erzeuge neuen Timer, der in bestimmten Zeitintervallen die S�ulen zeichnet.
			Platform.runLater(new Runnable () {

				@Override
				public void run() {
					AnimationStage.this.show();
					
					//Bestimme Maximale H�he einer S�ule
					max = event.getCurrentArray()[0];
					for (int i : event.getCurrentArray()) if(i>max)max = i;
					
					
					new AnimationTimer() {
						@Override
						public void handle(long arg0) {
							
							//Beende Timer wenn Fenster geschlossen ist
							if(!isShowing())this.stop();
							
							//Passe H�he des Canvas an das Fenster an
							gc.getCanvas().setWidth(AnimationStage.this.getWidth());
							gc.getCanvas().setHeight(AnimationStage.this.getHeight());
							
							//H�he und Breite f�r Berechnung des Fensters anhand der S�ulengr��e
							xSize = (int) gc.getCanvas().getWidth();
							ySize = (int) gc.getCanvas().getHeight();
							
							//Mache S�ulen kleiner, falls gr��te Zahl gr��er als verf�gbare Pixel
							yAxisMultiplier = ySize/(double)max;
					        
							//Bestimmte jede wievielte Zahl ber�cksichtigt werden soll, falls zu viele Zahlen f�r Breite
					        if(event.getCurrentArray().length > xSize ) xSteps = Math.ceil((double)event.getCurrentArray().length/((double)xSize));
					        else xSteps = 1;
							
					        //Falls weniger S�ulen als Pixel in Breite, pr�fe ob S�ulen breiter gemacht werden k�nnen
							int barWidth;
							if(xSteps==1)barWidth = (xSize)/event.getCurrentArray().length;
							else barWidth = 1;
							
							//Zeichne wei�en Hintergrund
							gc.setFill(Color.WHITE);
							gc.fillRect(0, 0, xSize, ySize);
							
							//Zeichne S�ulen
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
			//Fenster soll f�r eine Sekunde offen bleiben, wenn die Sortierung beendet wurde.
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
