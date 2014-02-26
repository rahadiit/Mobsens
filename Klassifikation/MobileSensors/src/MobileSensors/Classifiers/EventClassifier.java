package MobileSensors.Classifiers;

import java.util.ArrayList;

import MobileSensors.Storage.Events.Event;

public interface EventClassifier {

	public ArrayList<Event> getEvents(Window win);

}