package pt.ulisboa.tecnico.securechildlocator.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SecureChildLocator {

	private long timestamp = System.currentTimeMillis();

	private List<Location> locations = new CopyOnWriteArrayList<>();

	// Singleton -------------------------------------------------------------

	/* Private constructor prevents instantiation from other classes */
	private SecureChildLocator() {
	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		private static final SecureChildLocator INSTANCE = new SecureChildLocator();
	}

	public static synchronized SecureChildLocator getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public void reset() {
		locations.clear();
	}

	public void addLocation(String latitude, String longitude) {
		locations.add(new Location(latitude, longitude));
	}

	public List<Location> getLocations() {
		return new ArrayList<>(locations);
	}

	public void imAlive() {
		timestamp = System.currentTimeMillis();
	}

	public long getTimestamp() {
		return timestamp;
	}

}