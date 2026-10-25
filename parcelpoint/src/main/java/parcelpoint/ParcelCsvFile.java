package parcelpoint;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;

public class ParcelCsvFile {
    private final String fileName;

    public ParcelCsvFile(String fileName) {
        this.fileName = fileName;
    }

    public void save(List<Parcel> parcels) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (Parcel parcel : parcels) {
                writer.write(parcel.getCode() + "," + parcel.getStatus() + "\n");
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot save " + fileName, e);
        }
    }
}
