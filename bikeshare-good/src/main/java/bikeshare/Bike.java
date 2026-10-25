package bikeshare;

public class Bike {
    private final String id;
    private String station;
    private boolean rented;

    public Bike(String id, String station) {
        this.id = id;
        this.station = station;
    }

    public String getId() {
        return id;
    }

    public boolean isAvailableAt(String stationName) {
        return !rented && station.equalsIgnoreCase(stationName);
    }

    public void rent() {
        rented = true;
    }

    public void returnTo(String stationName) {
        rented = false;
        station = stationName;
    }
}
