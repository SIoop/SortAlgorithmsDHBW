package de.dhbw.horb.programmieren.projekt.events;

/**
 * Events enthalten bestimmte Informationen zum aktuellen Status der Sortierung.
 * @author Alexander Lepper
 *
 */
public class SortingEvent {

	/**
	 * Die Benachrichtigung, die der Benutzer in der Konsole erhält, wenn ein Event ausgelöst wurde
	 */
	String message;
	/**
	 * Die Art des ausgelösten Events
	 */
	EventType type;
	/**
	 * Die Zeit, die für die Sortierung benötigt wurde (wird auch in der Konsole ausgegeben)
	 */
	long time;
	/**
	 * Das Array mit den zu sortierenden Zahlen
	 */
	int[] currentArray;
	

	public SortingEvent(String message, EventType type, long time) {
		super();
		this.message = message;
		this.type = type;
		this.time = time;
		
	}

	public int[] getCurrentArray() {
		return currentArray;
	}
	
	public void setCurrentArray(int[] currentArray) {
		this.currentArray = currentArray;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
}
