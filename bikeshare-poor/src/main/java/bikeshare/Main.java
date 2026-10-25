package bikeshare;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BikeShare.setup();
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Commands: rent <memberId> <student|staff> <station>, return <bikeId> <station> <minutes>, list, quit");
            while (in.hasNextLine()) {
                String[] w = in.nextLine().trim().split("\\s+");
                if (w[0].equals("quit")) {
                    break;
                }
                BikeShare.handle(w);
            }
        }
    }
}
