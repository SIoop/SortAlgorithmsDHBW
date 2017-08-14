package de.dhbw.horb.programmieren.projekt.animation;

import de.dhbw.horb.programmieren.projekt.events.EventType;
import de.dhbw.horb.programmieren.projekt.events.SortingEvent;
import de.dhbw.horb.programmieren.projekt.events.SortingListener;
import de.dhbw.horb.programmieren.projekt.sorting.SortingService;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;

public class AnimationStage extends Stage implements SortingListener {

	SortingService con;
	private GraphicsContext gc;
	private int xSize = 1000;
	private int ySize = 500;
	double yAxisMultiplier;
	double xSteps;
	int max;
	Stage stage;
	
	public AnimationStage () {
			//con.addObserver(this);
			Canvas canvas = new Canvas(1000, 500);
			gc = canvas.getGraphicsContext2D();
			Group root = new Group();
			root.getChildren().add(canvas);
			this.setScene(new Scene(root));
	}
	
	public void handle(SortingEvent event) {
		
		if(event.getType()==EventType.SORTINGRUN) {
			Platform.runLater(new Runnable () {

				@Override
				public void run() {
					AnimationStage.this.show();
					max = event.getCurrentArray()[0];
					for (int i : event.getCurrentArray()) if(i>max)max = i;
					new AnimationTimer() {
						@Override
						public void handle(long arg0) {
							if(!isShowing())this.stop();
							
							gc.getCanvas().setWidth(AnimationStage.this.getWidth());
							gc.getCanvas().setHeight(AnimationStage.this.getHeight());
							
							xSize = (int) gc.getCanvas().getWidth();
							ySize = (int) gc.getCanvas().getHeight();
							
							
							yAxisMultiplier = ySize/(double)max;
					        
					        if(event.getCurrentArray().length > xSize ) xSteps = Math.ceil((double)event.getCurrentArray().length/((double)xSize));
					        else xSteps = 1;
							
							int barWidth;
							if(xSteps==1)barWidth = (xSize)/event.getCurrentArray().length;
							else barWidth = 1;
							gc.setFill(Color.WHITE);
							gc.fillRect(0, 0, xSize, ySize);
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
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					close();
				}
				
			});
		}
	}
}
