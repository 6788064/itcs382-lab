package bikeshare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BikeShareService {
    private final List<Bike> bikes = new ArrayList<>();
    private final Map<String, Rental> rentals = new HashMap<>();
    private final RideRecorder recorder;

    private record Rental(Bike bike, String memberId, MemberType memberType) {
    }

    public BikeShareService(List<String> stations, int bikesPerStation, RideRecorder recorder) {
        this.recorder = recorder;
        for (String station : stations) {
            for (int i = 0; i < bikesPerStation; i++) {
                bikes.add(new Bike("B" + (bikes.size() + 1), station));
            }
        }
    }

    public Optional<String> rent(String memberId, MemberType memberType, String station) {
        for (Bike bike : bikes) {
            if (bike.isAvailableAt(station)) {
                bike.rent();
                rentals.put(bike.getId(), new Rental(bike, memberId, memberType));
                return Optional.of(bike.getId());
            }
        }
        return Optional.empty();
    }

    public Optional<Ride> giveBack(String bikeId, String station, int minutes) {
        Rental rental = rentals.remove(bikeId);
        if (rental == null) {
            return Optional.empty();
        }
        rental.bike().returnTo(station);
        Ride ride = new Ride(bikeId, rental.memberId(), rental.memberType(), minutes,
                rental.memberType().feeFor(minutes));
        recorder.record(ride);
        return Optional.of(ride);
    }
}
