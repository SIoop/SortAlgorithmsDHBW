package de.dhbw.horb.programmieren.projekt.animation;

import de.dhbw.horb.programmieren.projekt.events.SortingEvent;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class SortAnimationTimer extends AnimationTimer {
	/**
	 * 
	 */
	private final AnimationStage animationStage;
	private final SortingEvent event;

	SortAnimationTimer(AnimationStage animationStage, SortingEvent event) {
		this.animationStage = animationStage;
		this.event = event;
	}

	@Override
	public void handle(long arg0) {
		
		//Beende Timer wenn Fenster geschlossen ist
		if(!this.animationStage.isShowing())this.stop();
		
		//Passe H�he des Canvas an das Fenster an
		this.animationStage.gc.getCanvas().setWidth(this.animationStage.getWidth());
		this.animationStage.gc.getCanvas().setHeight(this.animationStage.getHeight());
		
		//H�he und Breite f�r Berechnung des Fensters anhand der S�ulengr��e
		this.animationStage.xSize = (int) this.animationStage.gc.getCanvas().getWidth();
		this.animationStage.ySize = (int) this.animationStage.gc.getCanvas().getHeight();
		
		//Mache S�ulen kleiner, falls gr��te Zahl gr��er als verf�gbare Pixel
		this.animationStage.yAxisMultiplier = this.animationStage.ySize/(double)this.animationStage.max;
	    
		//Bestimmte jede wievielte Zahl ber�cksichtigt werden soll, falls zu viele Zahlen f�r Breite
	    if(event.getCurrentArray().length > this.animationStage.xSize ) this.animationStage.xSteps = Math.ceil((double)event.getCurrentArray().length/((double)this.animationStage.xSize));
	    else this.animationStage.xSteps = 1;
		
	    //Falls weniger S�ulen als Pixel in Breite, pr�fe ob S�ulen breiter gemacht werden k�nnen
		int barWidth;
		if(this.animationStage.xSteps==1)barWidth = (this.animationStage.xSize)/event.getCurrentArray().length;
		else barWidth = 1;
		
		//Zeichne wei�en Hintergrund
		this.animationStage.gc.setFill(Color.WHITE);
		this.animationStage.gc.fillRect(0, 0, this.animationStage.xSize, this.animationStage.ySize);
		
		//Zeichne S�ulen
		this.animationStage.gc.setFill(Color.DEEPPINK);
		for(int i = 0; i < event.getCurrentArray().length; i += this.animationStage.xSteps) {
			this.animationStage.gc.fillRect((i/this.animationStage.xSteps)*barWidth, this.animationStage.ySize-(event.getCurrentArray()[i]*this.animationStage.yAxisMultiplier), barWidth, event.getCurrentArray()[i]*this.animationStage.yAxisMultiplier);
		}
	}
}