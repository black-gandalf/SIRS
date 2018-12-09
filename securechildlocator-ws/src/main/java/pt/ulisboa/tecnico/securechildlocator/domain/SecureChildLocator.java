package pt.ulisboa.tecnico.securechildlocator.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/** Domain Root. */
public class SecureChildLocator {

	// Members ---------------------------------------------------------------

	/**
	 * Map of existing products. Uses concurrent hash table implementation
	 * supporting full concurrency of retrievals and high expected concurrency
	 * for updates.
	 */
	// private Map<String, Location> locations = new ConcurrentHashMap<>();
	private List<Location> locations = new ArrayList<>();

	/**
	 * Global purchase identifier counter. Uses lock-free thread-safe single
	 * variable.
	 */
	// private AtomicInteger locationIdCounter = new AtomicInteger(0);

	// For more information regarding concurrent collections, see:
	// https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/package-summary.html#package.description

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

	// product ---------------------------------------------------------------

	public void reset() {
		locations.clear();
	}

	public void addLocation(String latitude, String longitude) {
		locations.add(new Location(latitude, longitude));
	}

	public List<Location> getLocations() {
		return new ArrayList<>(locations);
	}

}