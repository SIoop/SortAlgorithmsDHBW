package de.dhbw.horb.programmieren.projekt.events;

/**
 * Listener k�nnen sich beim SortingService registrieren, um Events zu erhalten.
 * @author itleppa
 *
 */
public interface SortingListener {

	public void handle (SortingEvent event);
}
