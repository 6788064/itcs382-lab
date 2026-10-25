package bikeshare;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BikeShare {
    public static final int STUDENT = 1;
    public static final int STAFF = 2;

    public static List<String> bikeIds = new ArrayList<>();
    public static List<String> bikeStations = new ArrayList<>();
    public static List<String> bikeMembers = new ArrayList<>();
    public static List<Integer> bikeTypes = new ArrayList<>();

    private BikeShare() {
    }

    public static void setup() {
        String[] stations = {"Library", "Canteen", "Dorm"};
        int n = 1;
        for (String s : stations) {
            for (int i = 0; i < 2; i++) {
                bikeIds.add("B" + n++);
                bikeStations.add(s);
                bikeMembers.add(null);
                bikeTypes.add(0);
            }
        }
    }

    public static void handle(String[] w) {
        try {
            if (w[0].equals("rent")) {
                int type = w[2].equals("staff") ? STAFF : STUDENT;
                process(true, w[1], type, w[3], 0);
            } else if (w[0].equals("return")) {
                process(false, w[1], 0, w[2], Integer.parseInt(w[3]));
            } else if (w[0].equals("list")) {
                for (int i = 0; i < bikeIds.size(); i++) {
                    System.out.println(bikeIds.get(i) + " " + (bikeMembers.get(i) == null ? bikeStations.get(i) : "rented by " + bikeMembers.get(i)));
                }
            } else {
                System.out.println("Unknown command");
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("Bad input: " + e.getMessage());
        }
    }

    // rent == true rents a bike, rent == false returns one
    public static void process(boolean rent, String key, int type, String station, int minutes) {
        if (rent) {
            for (int i = 0; i < bikeIds.size(); i++) {
                if (bikeMembers.get(i) == null && bikeStations.get(i).equalsIgnoreCase(station)) {
                    bikeMembers.set(i, key);
                    bikeTypes.set(i, type);
                    bikeStations.set(i, null);
                    System.out.println("Rented " + bikeIds.get(i));
                    return;
                }
            }
            System.out.println("No bike available at " + station);
        } else {
            int i = bikeIds.indexOf(key);
            if (i < 0 || bikeMembers.get(i) == null) {
                System.out.println("Bike " + key + " is not rented");
                return;
            }
            int fee = minutes * (bikeTypes.get(i) == STAFF ? 3 : 2);
            System.out.println("Receipt: bike " + key + ", " + minutes + " min, fee " + fee + " baht");
            try (FileWriter fw = new FileWriter("rides.csv", true)) {
                fw.write(key + "," + bikeMembers.get(i) + "," + bikeTypes.get(i) + "," + minutes + "," + fee + "\n");
            } catch (IOException e) {
                System.out.println("Could not save ride: " + e.getMessage());
            }
            bikeMembers.set(i, null);
            bikeStations.set(i, station);
        }
    }
}
