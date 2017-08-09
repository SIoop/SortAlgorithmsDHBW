package de.dhbw.horb.programmieren.projekt.sortingview;

import java.util.Arrays;

import de.dhbw.horb.programmieren.projekt.sortcontroller.UserInput.IncorrectInputException;
import de.dhbw.horb.programmieren.projekt.sortcontroller.SortController;
import de.dhbw.horb.programmieren.projekt.sortcontroller.SortObserver;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Toggle;

public class ViewController implements ChangeListener<Toggle>, SortObserver, EventHandler<ActionEvent>{
	
	private GUI form;
	private int[] result;
	SortController con = null;
	String seperateLine ="----------------------------- \n";
	Thread sorterThread;

	public ViewController(GUI form) {
		super();
		this.form = form;
	}

	@Override
	public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
		
		String inputMode = "";
		if (newValue.equals(form.getRbtnManuallyInput())) inputMode = "manual";
		else if (newValue.equals(form.getRbtnFileInput())) inputMode = "file";
		else if (newValue.equals(form.getRbtnRandomInput())) inputMode = "random";
		disableItems(inputMode);
	}
	
	public void disableItems(String inputMode) {
		
		boolean a = true;
		boolean b = true;
		boolean c = true;
		if (inputMode.equals("manual")) a = false;
		else if (inputMode.equals("file")) b = false;
		else if (inputMode.equals("random")) c = false;
		form.getTxfManuallyInput().setDisable(a);
		form.getTxfFileInput().setDisable(b);
		form.getBtnBrowseInputFile().setDisable(b);
		form.getTxfLowerLimit().setDisable(c);
		form.getTxfUpperLimit().setDisable(c);
		form.getTxfChooseNumbers().setDisable(c);
	}

	@Override
	public void sortIsDone(int[] arr, long time) {
		
		Double timeInMs = time/1000000.0;
		this.result = arr;
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	form.getConsole().appendText("Sortierung beendet. Benötigte Zeit beträgt " + timeInMs + " ms." + "\n");
        		if(form.getChboxConsoleOutput().isSelected()) form.getConsole().appendText("Ergebnis: " + Arrays.toString(result) + "\n");
        		form.getConsole().appendText(seperateLine);
        		form.getBtnStart().setDisable(false);
            }
        });
		con = null;
		
	}

	@Override
	public void handle(ActionEvent arg0) {
		
		if(arg0.getSource() == form.getBtnCancel()) {
			if (con!=null) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						form.getBtnCancel().setDisable(true);
						form.getConsole().appendText("Sortierung abgebrochen." + "\n");
					}
				});
				con.cancelSorting();
				return;
			}
		}
		else {
			
			try {
				con = new SortController(form.getUserInputOptions());
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						form.getBtnStart().setDisable(true);
						form.getBtnCancel().setDisable(false);
						form.getConsole().appendText("Generiere Array. Bitte warten..." + "\n");
					}
				});
				con.addObserver(this);
				Thread sorterThread = new Thread (new Runnable() {
					
					@Override
					public void run() {
						con.start();			
					}
					
				});
				sorterThread.setDaemon(true);
				sorterThread.setName("Sorter-Thread");
				sorterThread.start();
				if (form.getUserInputOptions().isAnimation()) {
					
					new AnimationStage(con);
				}
			} catch (IncorrectInputException e) {
				form.getConsole().setStyle("-fx-fill: RED;-fx-font-weight:bold;");
				form.getConsole().appendText("Ungültige Eingabe!" + "\n");
				form.getConsole().appendText(e.getMessage() + seperateLine);
				form.getConsole().setStyle("-fx-fill: #4F8A10;-fx-font-weight:bold;");
				form.setExceptionMessage("");
			}
		}
	}

	@Override
	public void arrayIsGenerated(int[] arr) {
		
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
                form.getConsole().appendText("Algorithmus wurde gestartet..." + "\n");
            }
        });
	}
	
}
