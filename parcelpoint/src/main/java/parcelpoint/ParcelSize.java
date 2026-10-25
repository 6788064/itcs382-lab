package parcelpoint;

public enum ParcelSize {
    SMALL(10.0),
    MEDIUM(15.0),
    LARGE(20.0);

    private final double dailyFee;

    ParcelSize(double dailyFee) {
        this.dailyFee = dailyFee;
    }

    public double getDailyFee() {
        return dailyFee;
    }

    public boolean fitsIn(ParcelSize lockerSize) {
        return compareTo(lockerSize) <= 0;
    }

    public static ParcelSize fromCode(int code) {
        if (code < 1 || code > values().length) {
            throw new IllegalArgumentException("Size must be 1 to " + values().length + ": " + code);
        }
        return values()[code - 1];
    }
}
