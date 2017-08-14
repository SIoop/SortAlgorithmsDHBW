package de.dhbw.horb.programmieren.projekt.controller;

import java.io.File;
import java.util.Arrays;

import de.dhbw.horb.programmieren.projekt.controller.InputChecker.IncorrectInputException;
import de.dhbw.horb.programmieren.projekt.events.EventType;
import de.dhbw.horb.programmieren.projekt.events.SortingEvent;
import de.dhbw.horb.programmieren.projekt.events.SortingListener;
import de.dhbw.horb.programmieren.projekt.sorting.SortingThread;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class RootPaneController implements SortingListener {

	InputMode inputMode = InputMode.UNDEFINED;
	Algorithm algorithm = Algorithm.UNDEFINED;
	
	private static final String FILECHOOSERTITLE = "Wähle Eingabe Datei";
	private static final String LINESEPERATOR = "------------------------------------------------------------------------------------\n";
	
	@FXML
	TextArea console;
	
	@FXML
	RadioButton rbtnRandom;
	
	@FXML
	RadioButton rbtnManual;
	
	@FXML
	RadioButton rbtnFile;
	
	@FXML
	RadioButton rbtnQuickSort;
	
	@FXML
	RadioButton rbtnMergeSort;
	
	@FXML
	CheckBox chkAnimation;
	
	@FXML
	CheckBox chkConsoleOutput;
	
	@FXML
	TextField tfLowerLimit;
	
	@FXML
	TextField tfUpperLimit;
	
	@FXML
	TextField tfAmount;
	
	@FXML
	TextField tfManual;
	
	@FXML
	TextField tfFile;
	
	@FXML
	TextField tfThreads;
	
	@FXML
	TextField tfDelay;
	
	@FXML
	TextField tfRuns;
	
	@FXML
	Button btnBrowse;
	
	@FXML
	Button btnStart;
	
	@FXML
	Button btnCancel;
	
	@FXML
	protected void startButtonPressed() {
		
		InputChecker checker = new InputChecker(inputMode, tfLowerLimit.getText(), tfUpperLimit.getText(), tfAmount.getText(), tfManual.getText(), tfFile.getText(), tfThreads.getText(), tfDelay.getText(), tfRuns.getText());
		try {
			checker.check();
			SortingThread starter = new SortingThread(tfLowerLimit.getText(), tfUpperLimit.getText(), tfAmount.getText(), tfManual.getText(), tfFile.getText(), algorithm, Integer.parseInt(tfThreads.getText()), Integer.parseInt(tfDelay.getText()), Integer.parseInt(tfRuns.getText()), inputMode);
			starter.addSortListener(this);
			Thread thread = new Thread(starter);
			thread.setDaemon(true);
			thread.start();
			Platform.runLater(new Runnable () {

				@Override
				public void run() {
					btnStart.setDisable(true);
				}
				
			});
		} catch (IncorrectInputException e) {
			writeToConsole(e.getMessage());
		}
		
	}
	
	@FXML
	protected void cancelButtonPressed() {
		
	}
	
	@FXML
	protected void browseButtonPressed() {
		
		//Open File Chooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(FILECHOOSERTITLE);
		fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT", "*.txt")
            );
		File selected = fileChooser.showOpenDialog(null);
		if(selected != null && selected.exists())tfFile.setText(selected.getAbsolutePath());
	}
	
	@FXML
	protected void randomSelected () {
		inputMode = InputMode.RANDOM;
		
		//Enable/Disable Controls
		tfLowerLimit.setDisable(false);
		tfUpperLimit.setDisable(false);
		tfAmount.setDisable(false);
		tfManual.setDisable(true);
		tfFile.setDisable(true);
		btnBrowse.setDisable(true);
	}
	
	@FXML
	protected void manualSelected () {
		inputMode = InputMode.MANUAL;
		
		//Enable/Disable Controls
		tfLowerLimit.setDisable(true);
		tfUpperLimit.setDisable(true);
		tfAmount.setDisable(true);
		tfManual.setDisable(false);
		tfFile.setDisable(true);
		btnBrowse.setDisable(true);
	}
	
	@FXML
	protected void fileSelected () {
		inputMode = InputMode.FILE;
		
		//Enable/Disable Controls
		tfLowerLimit.setDisable(true);
		tfUpperLimit.setDisable(true);
		tfAmount.setDisable(true);
		tfManual.setDisable(true);
		tfFile.setDisable(false);
		btnBrowse.setDisable(false);
	}
	
	@FXML
	protected void quickSelected () {
		algorithm = Algorithm.QUICKSORT;
	}
	
	@FXML
	protected void mergeSelected () {
		algorithm = Algorithm.MERGESORT;
	}
	
	protected void writeToConsole (String write) {
		Platform.runLater(new Runnable () {

			@Override
			public void run() {
				console.appendText(write);
			}
			
		});
	}

	@Override
	public void handle(SortingEvent event) {
		
		if(event.getType().equals(EventType.SORTINGENDED)) {
			writeToConsole("Sortierung wurde nach " + event.getTime() + " Nanosekunden beendet!\n" + LINESEPERATOR);
			if(chkConsoleOutput.isSelected() && event.getCurrentArray().length <= 1000000) {
				writeToConsole("Array: " + Arrays.toString(event.getCurrentArray()) + "\n" + LINESEPERATOR);
			} else if (event.getCurrentArray().length > 1000000) {
				writeToConsole("Ausgabe eines Arrays nur bis zur Größe von 1.000.000 möglich!");
			}
			Platform.runLater(new Runnable () {

				@Override
				public void run() {
					btnStart.setDisable(false);
				}
				
			});
		} else if (event.getType().equals(EventType.ARRAYGENERATED)) {
			writeToConsole("Array wurde generiert! Algorithmus wird gestartet...\n");
		} else if (event.getType().equals(EventType.SORTINGSTARTED)) {
			writeToConsole(event.getMessage());
		}
	}
}
