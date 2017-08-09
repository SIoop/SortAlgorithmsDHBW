package de.dhbw.horb.programmieren.projekt.sortingview;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class ViewDragController implements EventHandler<DragEvent> {

	private SortingApp parent;
	
	public ViewDragController(SortingApp view) {
		super();
		this.parent = view;
	}
	
	@Override
	public void handle(DragEvent arg0) {
		if(arg0.getEventType().equals(DragEvent.DRAG_OVER)) {
			Dragboard db = arg0.getDragboard();
            if (db.hasFiles()) {
                arg0.acceptTransferModes(TransferMode.LINK);
            } else {
                arg0.consume();
            }
		} else if(arg0.getEventType().equals(DragEvent.DRAG_DROPPED)) {
			 Dragboard db = arg0.getDragboard();
             boolean success = false;
             if (db.hasFiles()) {
                 success = true;
                 String filePath = null;
                 for (File file:db.getFiles()) {
                     filePath = file.getAbsolutePath();
                     parent.getForm().getTxfFileInput().setText(filePath);
                 }
             }
             arg0.setDropCompleted(success);
             arg0.consume();
		}
		
	}

	public SortingApp getParent() {
		return parent;
	}

	public void setParent(SortingApp parent) {
		this.parent = parent;
	}

}
