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
		
		//Passe Höhe des Canvas an das Fenster an
		this.animationStage.gc.getCanvas().setWidth(this.animationStage.getWidth());
		this.animationStage.gc.getCanvas().setHeight(this.animationStage.getHeight());
		
		//Höhe und Breite für Berechnung des Fensters anhand der Säulengröße
		this.animationStage.xSize = (int) this.animationStage.gc.getCanvas().getWidth();
		this.animationStage.ySize = (int) this.animationStage.gc.getCanvas().getHeight();
		
		//Mache Säulen kleiner, falls größte Zahl größer als verfügbare Pixel
		this.animationStage.yAxisMultiplier = this.animationStage.ySize/(double)this.animationStage.max;
	    
		//Bestimmte jede wievielte Zahl berücksichtigt werden soll, falls zu viele Zahlen für Breite
	    if(event.getCurrentArray().length > this.animationStage.xSize ) this.animationStage.xSteps = Math.ceil((double)event.getCurrentArray().length/((double)this.animationStage.xSize));
	    else this.animationStage.xSteps = 1;
		
	    //Falls weniger Säulen als Pixel in Breite, prüfe ob Säulen breiter gemacht werden können
		int barWidth;
		if(this.animationStage.xSteps==1)barWidth = (this.animationStage.xSize)/event.getCurrentArray().length;
		else barWidth = 1;
		
		//Zeichne weißen Hintergrund
		this.animationStage.gc.setFill(Color.WHITE);
		this.animationStage.gc.fillRect(0, 0, this.animationStage.xSize, this.animationStage.ySize);
		
		//Zeichne Säulen
		this.animationStage.gc.setFill(Color.DEEPPINK);
		for(int i = 0; i < event.getCurrentArray().length; i += this.animationStage.xSteps) {
			this.animationStage.gc.fillRect((i/this.animationStage.xSteps)*barWidth, this.animationStage.ySize-(event.getCurrentArray()[i]*this.animationStage.yAxisMultiplier), barWidth, event.getCurrentArray()[i]*this.animationStage.yAxisMultiplier);
		}
	}
}