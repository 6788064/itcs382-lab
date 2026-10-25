package parcelpoint;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;

public class ParcelService {
    private List<Locker> lockers = new ArrayList<>();
    private List<Parcel> parcels = new ArrayList<>();

    public ParcelService() {
        lockers.add(new Locker(1, ParcelSize.SMALL));
        lockers.add(new Locker(2, ParcelSize.MEDIUM));
        lockers.add(new Locker(3, ParcelSize.LARGE));
    }

    public List<Locker> getLockers() {
        return lockers;
    }

    public Locker storeParcel(Parcel parcel) {
        for (Locker locker : lockers) {
            if (locker.isFree() && parcel.getSize().fitsIn(locker.getSize())) {
                locker.setParcel(parcel);
                parcel.markStored();
                parcels.add(parcel);
                System.out.println("SMS to " + parcel.getStudentId() + ": parcel "
                        + parcel.getCode() + " is in locker " + locker.getNumber());
                saveToFile();
                return locker;
            }
        }
        return null;
    }

    public double pickUp(String code, int days) {
        for (Locker locker : lockers) {
            Parcel parcel = locker.getParcel();
            if (parcel != null && parcel.getCode().equals(code)) {
                locker.setParcel(null);
                parcel.markPickedUp();
                saveToFile();
                return fee(parcel, days);
            }
        }
        return 0;
    }

    public double fee(Parcel parcel, int days) {
        if (days <= 3) {
            return 0;
        }
        return (days - 3) * parcel.getSize().getDailyFee();
    }

    public void printParcels(boolean forStaff) {
        for (Parcel parcel : parcels) {
            if (forStaff) {
                System.out.println(parcel.getCode() + " " + parcel.getStudentId() + " " + parcel.getStatus());
            } else if ("STORED".equals(parcel.getStatus())) {
                System.out.println(parcel.getCode() + " is waiting");
            }
        }
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter("parcels.csv")) {
            for (Parcel parcel : parcels) {
                writer.write(parcel.getCode() + "," + parcel.getStatus() + "\n");
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot save parcels.csv", e);
        }
    }
}
