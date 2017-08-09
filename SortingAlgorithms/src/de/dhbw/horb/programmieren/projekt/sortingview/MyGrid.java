package de.dhbw.horb.programmieren.projekt.sortingview;

import java.io.File;

import de.dhbw.horb.programmieren.projekt.sortcontroller.Options;
import de.dhbw.horb.programmieren.projekt.sortcontroller.Options.IncorrectInputException;
import de.dhbw.horb.programmieren.projekt.sortcontroller.Options.InputType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;

public class MyGrid extends GridPane {

	private Label lInputType = new Label("Art der Eingabe wählen:");
	private ToggleGroup tgrInputChooser = new ToggleGroup();
	private RadioButton rbtnManuallyInput = new RadioButton("Manuell eingeben");
	private TextField txfManuallyInput = new TextField();
	private RadioButton rbtnFileInput = new RadioButton("Datei auslesen");
	private TextField txfFileInput = new TextField();
	private Button btnBrowseInputFile = new Button("Browse");
	private RadioButton rbtnRandomInput = new RadioButton("Zufällige Zahlen zwischen");
	private Label lChooseLowerLimit = new Label("Zufallszahlen zwischen ");
	private Label lChooseNumbers = new Label("Anzahl: ");
	private TextField txfChooseNumbers = new TextField();
	private TextField txfLowerLimit = new TextField();
	private Label lUnd = new Label(" und ");
	private TextField txfUpperLimit = new TextField();
	private Separator separator = new Separator();

	private Label lSortType = new Label("Algorithmus wählen:");
	private ToggleGroup tgrSortChooser = new ToggleGroup();
	private RadioButton rbtnQuickSort = new RadioButton("QuickSort");
	private RadioButton rbtnMergeSort = new RadioButton("MergeSort");
	private Label lChooseThreadNumber = new Label("Anzahl der Threads auswählen:");
	private Label lThreadNumber = new Label("Anzahl der Threads:");
	private TextField txfThreadNumber = new TextField();
	
	private TitledPane moreOptions = new TitledPane();
	private GridPane content = new GridPane();
	private CheckBox chboxAnimation = new CheckBox("Animation");
	private Label lDelay = new Label("Verzögerung in Millisekunden: ");
	private TextField txfDelay = new TextField();
	private CheckBox chboxConsoleOutput = new CheckBox("Ausgabe in Konsole");
	private Label lRuns = new Label("Anzahl Durchläufe: ");
	private TextField txfRuns = new TextField();
	
	private ViewController controller;
	
	private Button btnStart = new Button("START");
	
	private TextArea console = new TextArea();
	private String exceptionMessage = "";
	

	public MyGrid() {
		super();
		
		
		lInputType.getStyleClass().add("heading");
		lSortType.getStyleClass().add("heading");
		btnStart.setId("btnStart");
		this.setAlignment(Pos.CENTER_LEFT);
		this.setMaxWidth(Double.MAX_VALUE);
		this.setHgap(10);
		this.setVgap(10);
		
		
		
		rbtnManuallyInput.setToggleGroup(tgrInputChooser);
		rbtnFileInput.setToggleGroup(tgrInputChooser);
		rbtnRandomInput.setToggleGroup(tgrInputChooser);
		this.controller = new ViewController(this);
		tgrInputChooser.selectedToggleProperty().addListener(controller);
		
		btnStart.setOnAction(controller);
		
		rbtnQuickSort.setToggleGroup(tgrSortChooser);
		rbtnMergeSort.setToggleGroup(tgrSortChooser);
		
		lUnd.setMaxWidth(Double.MAX_VALUE);
		lUnd.setAlignment(Pos.CENTER);
		
		lChooseNumbers.setMaxWidth(Double.MAX_VALUE);
		lChooseNumbers.setAlignment(Pos.CENTER_RIGHT);
		
		btnStart.setMaxWidth(Double.MAX_VALUE);
		btnStart.setMaxHeight(Double.MAX_VALUE);
		
		this.setPadding(new Insets(15,15,15,15));
		
		this.add(lInputType, 0, 0);
		this.add(rbtnManuallyInput, 0, 1);
		this.add(txfManuallyInput, 1, 1, 5, 1);
		this.add(rbtnFileInput, 0, 2);
		this.add(txfFileInput, 1, 2, 4, 1);
		this.add(btnBrowseInputFile, 5, 2);
		this.add(rbtnRandomInput, 0, 3);
		this.add(txfLowerLimit, 1, 3);
		this.add(lUnd, 2, 3);
		this.add(txfUpperLimit, 3, 3, 1, 1);
		this.add(lChooseNumbers, 4, 3);
		this.add(txfChooseNumbers, 5, 3);
		
		separator.setOrientation(Orientation.HORIZONTAL);
		MyGrid.setConstraints(separator, 0, 4);
		MyGrid.setColumnSpan(separator, 6);
		this.getChildren().add(separator);
		
		this.add(lSortType, 0, 5);
		this.add(rbtnQuickSort, 0, 6);
		this.add(rbtnMergeSort, 0, 7);
		this.add(lThreadNumber, 0, 8);
		this.add(txfThreadNumber, 1, 8);
		
		lRuns.setMaxWidth(Double.MAX_VALUE);
		lRuns.setAlignment(Pos.CENTER_RIGHT);
		
		txfDelay.setMaxWidth(80);
		txfRuns.setMaxWidth(80);
		
		content.add(chboxAnimation, 0, 0);
		content.add(lDelay, 1, 0);
		content.add(txfDelay, 2, 0);
		content.add(chboxConsoleOutput, 0, 1);
		content.add(lRuns, 1, 1);
		content.add(txfRuns, 2, 1);
		moreOptions.setText("Weitere Optionen");
		moreOptions.setContent(content);
		moreOptions.setExpanded(false);
		this.add(moreOptions, 0, 9, 6, 1);
		
		this.add(btnStart, 0, 12, 1, 1);
		ImageView imgview = new ImageView(new Image("File:logo.png"));
		imgview.autosize();
		imgview.setFitHeight(130);
		imgview.setFitWidth(260);
		this.add(imgview, 2, 5,4,4);
		
		this.add(console, 0, 15, 6, 1);

		ColumnConstraints col0 = new ColumnConstraints();
		ColumnConstraints col1 = new ColumnConstraints();
		ColumnConstraints col2 = new ColumnConstraints();
		ColumnConstraints col3 = new ColumnConstraints();
		ColumnConstraints col4 = new ColumnConstraints();
		ColumnConstraints col5 = new ColumnConstraints();
		col1.setPrefWidth(50);
		col3.setPrefWidth(50);
		col5.setPrefWidth(50);
		col1.setHgrow(Priority.ALWAYS);
		col2.setHgrow(Priority.ALWAYS);
		col3.setHgrow(Priority.ALWAYS);
		col4.setHgrow(Priority.ALWAYS);
		col5.setHgrow(Priority.ALWAYS);
		this.getColumnConstraints().addAll(col0, col1, col2, col3, col4, col5);
		
		RowConstraints row0 = new RowConstraints();
		RowConstraints row15 = new RowConstraints();
		row15.setVgrow(Priority.ALWAYS);
		this.getRowConstraints().addAll(row0, row0, row0, row0, row0, row0, row0, row0, row0, row0, row0, row0, row0, row0, row0, row15);
		txfRuns.setText("1");
		txfThreadNumber.setText("1");
		txfDelay.setText("0");
		
		console.setEditable(false);
		
		txfChooseNumbers.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
	                txfChooseNumbers.setText(newValue.replaceAll("[^\\d]", ""));
	            }
				if(newValue.length() > 10)
	                txfChooseNumbers.setText(oldValue);
			}
			
		});
		txfLowerLimit.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
	                txfLowerLimit.setText(newValue.replaceAll("[^\\d]", ""));
	            }
				if(newValue.length() > 10)
	                txfLowerLimit.setText(oldValue);
			}
			
		});
		txfUpperLimit.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
	                txfUpperLimit.setText(newValue.replaceAll("[^\\d]", ""));
	            }
				if(newValue.length() > 10)
	                txfUpperLimit.setText(oldValue);
			}
			
		});
		txfThreadNumber.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
	                txfThreadNumber.setText(newValue.replaceAll("[^\\d]", ""));
	            }
				if(newValue.length() > 3)
	                txfThreadNumber.setText(oldValue);
			}
			
		});
		txfRuns.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
	                txfRuns.setText(newValue.replaceAll("[^\\d]", ""));
	            }
				if(newValue.length() > 5)
	                txfRuns.setText(oldValue);
			}
			
		});
		txfDelay.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
	                txfDelay.setText(newValue.replaceAll("[^\\d]", ""));
	            }
				if(newValue.length() > 4)
	                txfDelay.setText(oldValue);
			}
			
		});
		txfManuallyInput.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				if (!(newValue.matches("\\d*") || newValue.matches(","))) {
	                txfManuallyInput.setText(newValue.replaceAll("[^\\d,]", ""));
	            }
			}
			
		});
		btnBrowseInputFile.setOnAction(new EventHandler<ActionEvent> () {

			@Override
			public void handle(ActionEvent arg0) {
				
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Wähle Input Datei");
				fileChooser.getExtensionFilters().addAll(
		                new FileChooser.ExtensionFilter("TXT", "*.txt")
		            );
				File selected = fileChooser.showOpenDialog(null);
				if(selected != null && selected.exists())txfFileInput.setText(selected.getAbsolutePath());
			}
			
		});
	}
	
	public Options getUserInputOptions () throws IncorrectInputException {
		
		Options options = new Options();
			if (rbtnManuallyInput.isSelected()){
				options.setInputType(InputType.MANUALLY);
				options.setManualInput(txfManuallyInput.getText());
			}
			else if(rbtnRandomInput.isSelected()){
			options.setInputType(InputType.RANDOM);
			options.setLowerLimit(Integer.parseInt(txfLowerLimit.getText()));
			options.setUpperLimit(Integer.parseInt(txfUpperLimit.getText()));
			options.setCountNumbers(Integer.parseInt(txfChooseNumbers.getText()));
			}
			else if (rbtnFileInput.isSelected()){
			options.setInputType(InputType.FILE);
			options.setFilePath(txfFileInput.getText());
			}
			
			if(rbtnQuickSort.isSelected()) {
				options.setAlgorithm("Quicksort");
			} else {
				options.setAlgorithm("Mergesort");
			}
			options.setNumberOfThreads(Integer.parseInt(txfThreadNumber.getText()));
			options.setRepNumber(Integer.parseInt(txfRuns.getText()));
			options.setDelay(Integer.parseInt(txfDelay.getText()));
			options.setAnimation(chboxAnimation.isSelected());
			return options;
	}

	public Label getlInputType() {
		return lInputType;
	}

	public void setlInputType(Label lInputType) {
		this.lInputType = lInputType;
	}

	public ToggleGroup getTgrInputChooser() {
		return tgrInputChooser;
	}

	public void setTgrInputChooser(ToggleGroup tgrInputChooser) {
		this.tgrInputChooser = tgrInputChooser;
	}

	public RadioButton getRbtnManuallyInput() {
		return rbtnManuallyInput;
	}

	public void setRbtnManuallyInput(RadioButton rbtnManuallyInput) {
		this.rbtnManuallyInput = rbtnManuallyInput;
	}

	public TextField getTxfManuallyInput() {
		return txfManuallyInput;
	}

	public void setTxfManuallyInput(TextField txfManuallyInput) {
		this.txfManuallyInput = txfManuallyInput;
	}

	public RadioButton getRbtnFileInput() {
		return rbtnFileInput;
	}

	public void setRbtnFileInput(RadioButton rbtnFileInput) {
		this.rbtnFileInput = rbtnFileInput;
	}

	public TextField getTxfFileInput() {
		return txfFileInput;
	}

	public void setTxfFileInput(TextField txfFileInput) {
		this.txfFileInput = txfFileInput;
	}

	public Button getBtnBrowseInputFile() {
		return btnBrowseInputFile;
	}

	public void setBtnBrowseInputFile(Button btnBrowseInputFile) {
		this.btnBrowseInputFile = btnBrowseInputFile;
	}

	public RadioButton getRbtnRandomInput() {
		return rbtnRandomInput;
	}

	public void setRbtnRandomInput(RadioButton rbtnRandomInput) {
		this.rbtnRandomInput = rbtnRandomInput;
	}

	public Label getlChooseLowerLimit() {
		return lChooseLowerLimit;
	}

	public void setlChooseLowerLimit(Label lChooseLowerLimit) {
		this.lChooseLowerLimit = lChooseLowerLimit;
	}

	public TextField getTxfLowerLimit() {
		return txfLowerLimit;
	}

	public void setTxfLowerLimit(TextField txfLowerLimit) {
		this.txfLowerLimit = txfLowerLimit;
	}

	public Label getlUnd() {
		return lUnd;
	}

	public void setlChooseUpperLimit(Label lUnd) {
		this.lUnd = lUnd;
	}

	public TextField getTxfUpperLimit() {
		return txfUpperLimit;
	}

	public void setTxfUpperLimit(TextField txfUpperLimit) {
		this.txfUpperLimit = txfUpperLimit;
	}

	public TextField getTxfChooseNumbers() {
		return txfChooseNumbers;
	}

	public void setTxfChooseNumbers(TextField txfChooseNumbers) {
		this.txfChooseNumbers = txfChooseNumbers;
	}

	public Label getlChooseThreadNumber() {
		return lChooseThreadNumber;
	}

	public void setlChooseThreadNumber(Label lChooseThreadNumber) {
		this.lChooseThreadNumber = lChooseThreadNumber;
	}

	public Label getlThreadNumber() {
		return lThreadNumber;
	}

	public void setlThreadNumber(Label lThreadNumber) {
		this.lThreadNumber = lThreadNumber;
	}

	public TextField getTxfThreadNumber() {
		return txfThreadNumber;
	}

	public void setTxfThreadNumber(TextField txfThreadNumber) {
		this.txfThreadNumber = txfThreadNumber;
	}

	public Label getlSortType() {
		return lSortType;
	}

	public void setlSortType(Label lSortType) {
		this.lSortType = lSortType;
	}

	public ToggleGroup getTgrSortChooser() {
		return tgrSortChooser;
	}

	public void setTgrSortChooser(ToggleGroup tgrSortChooser) {
		this.tgrSortChooser = tgrSortChooser;
	}

	public RadioButton getRbtnQuickSort() {
		return rbtnQuickSort;
	}

	public void setRbtnQuickSort(RadioButton rbtnQuickSort) {
		this.rbtnQuickSort = rbtnQuickSort;
	}

	public RadioButton getRbtnMergeSort() {
		return rbtnMergeSort;
	}

	public void setRbtnMergeSort(RadioButton rbtnMergeSort) {
		this.rbtnMergeSort = rbtnMergeSort;
	}

	public CheckBox getChboxConsoleOutput() {
		return chboxConsoleOutput;
	}

	public void setChboxConsoleOutput(CheckBox chboxConsoleOutput) {
		this.chboxConsoleOutput = chboxConsoleOutput;
	}

	public Button getBtnStart() {
		return btnStart;
	}

	public void setBtnStart(Button btnStart) {
		this.btnStart = btnStart;
	}
	
	public TextArea getConsole() {
		return console;
	}

	public void setConsole(TextArea console) {
		this.console = console;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
}