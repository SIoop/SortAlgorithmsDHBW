package de.dhbw.horb.programmieren.projekt.sortingview;

import de.dhbw.horb.programmieren.projekt.sortcontroller.SortController;
import de.dhbw.horb.programmieren.projekt.sortcontroller.SortObserver;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;

public class AnimationStage extends Stage implements SortObserver {

	SortController con;
	private GraphicsContext gc;
	private int xSize = 1000;
	private int ySize = 500;
	double yAxisMultiplier;
	double xSteps;
	int max;
	Stage stage;
	
	public AnimationStage (SortController con) {
		this.con = con;
		con.addObserver(this);
		Canvas canvas = new Canvas(1000, 500);
        gc = canvas.getGraphicsContext2D();
        Group root = new Group();
        root.getChildren().add(canvas);
        this.setScene(new Scene(root));
	}

	@Override
	public void sortIsDone(int[] arr, long time) {

		try {
			Thread.sleep(1000);
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					close();
				}
				
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void arrayIsGenerated(int[] arr) {
		Platform.runLater(new Runnable () {

			@Override
			public void run() {
				AnimationStage.this.show();
				max = con.getCurrentArr()[0];
				for (int i : con.getCurrentArr()) if(i>max)max = i;
				new AnimationTimer() {
					@Override
					public void handle(long arg0) {
						if(!isShowing())this.stop();
						
						gc.getCanvas().setWidth(AnimationStage.this.getWidth());
						gc.getCanvas().setHeight(AnimationStage.this.getHeight());
						
						xSize = (int) gc.getCanvas().getWidth();
						ySize = (int) gc.getCanvas().getHeight();
						
						
						yAxisMultiplier = ySize/(double)max;
				        
				        if(con.getCurrentArr().length > xSize ) xSteps = Math.ceil((double)con.getCurrentArr().length/((double)xSize));
				        else xSteps = 1;
						
						int barWidth;
						if(xSteps==1)barWidth = (xSize)/con.getCurrentArr().length;
						else barWidth = 1;
						gc.setFill(Color.WHITE);
						gc.fillRect(0, 0, xSize, ySize);
						gc.setFill(Color.DEEPPINK);
						for(int i = 0; i < con.getCurrentArr().length; i += xSteps) {
							gc.fillRect((i/xSteps)*barWidth, ySize-(con.getCurrentArr()[i]*yAxisMultiplier), barWidth, con.getCurrentArr()[i]*yAxisMultiplier);
						}
					}
		    	}.start();
			}
			
		});
	}
}
