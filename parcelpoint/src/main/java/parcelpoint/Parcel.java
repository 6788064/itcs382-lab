package parcelpoint;

public class Parcel {
    private String code;
    private String studentId;
    private ParcelSize size;
    private String status;

    public Parcel(String code, String studentId, ParcelSize size) {
        this.code = code;
        this.studentId = studentId;
        this.size = size;
        this.status = "NEW";
    }

    public String getCode() {
        return code;
    }

    public String getStudentId() {
        return studentId;
    }

    public ParcelSize getSize() {
        return size;
    }

    public String getStatus() {
        return status;
    }

    public void markStored() {
        status = "STORED";
    }

    public void markPickedUp() {
        status = "PICKED_UP";
    }
}
