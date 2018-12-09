package pt.ulisboa.tecnico.securechildlocator.domain;

/**
 * Location
 */
public class Location {
    /** */
    private final String latitude;
    /** */
    private final String longitude;

    /** Create a new location */
    public Location(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Location [latitude=").append(latitude);
		builder.append(", longitude=").append(longitude);
		builder.append("]");
		return builder.toString();
	}
}