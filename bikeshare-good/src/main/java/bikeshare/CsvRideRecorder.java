package bikeshare;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;

public class CsvRideRecorder implements RideRecorder {
    private final String fileName;

    public CsvRideRecorder(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void record(Ride ride) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(String.join(",", ride.bikeId(), ride.memberId(), ride.memberType().name(),
                    String.valueOf(ride.minutes()), String.valueOf(ride.fee())) + "\n");
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot write " + fileName, e);
        }
    }
}
