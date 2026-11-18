package bg.sofia.uni.fmi.mjt.parking.vehicle;

public class Jeep extends AbstractVehicle {
    public static final int JEEP_SURCHARGE = 20;

    public Jeep(LicensePlate licensePlate) {
        super(licensePlate);
    }

    @Override
    public int getSurcharge() {
        return JEEP_SURCHARGE;
    }
}
