package client.backend;

public class Coordinates {
	private double latitude;
	private double longitude;
	
	public Coordinates(double lati, double longi) {
		this.setLatitude(lati);
		this.setLongitude(longi);
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
