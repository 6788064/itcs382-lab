package bikeshare;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private Main() {
    }

    public static void main(String[] args) {
        BikeShareService service = new BikeShareService(
                List.of("Library", "Canteen", "Dorm"), 2, new CsvRideRecorder("rides.csv"));
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Commands: rent <memberId> <student|staff> <station>, return <bikeId> <station> <minutes>, quit");
            while (in.hasNextLine()) {
                String[] words = in.nextLine().trim().split("\\s+");
                if (words[0].equals("quit")) {
                    break;
                }
                System.out.println(run(service, words));
            }
        }
    }

    private static String run(BikeShareService service, String[] words) {
        try {
            if (words[0].equals("rent") && words.length == 4) {
                MemberType type = MemberType.valueOf(words[2].toUpperCase(Locale.ROOT));
                return service.rent(words[1], type, words[3])
                        .map(bikeId -> "Rented " + bikeId)
                        .orElse("No bike available at " + words[3]);
            }
            if (words[0].equals("return") && words.length == 4) {
                return service.giveBack(words[1], words[2], Integer.parseInt(words[3]))
                        .map(ride -> "Receipt: bike " + ride.bikeId() + ", " + ride.minutes()
                                + " min, fee " + ride.fee() + " baht")
                        .orElse("Bike " + words[1] + " is not rented");
            }
            return "Unknown command";
        } catch (IllegalArgumentException e) {
            return "Bad input: " + e.getMessage();
        }
    }
}
