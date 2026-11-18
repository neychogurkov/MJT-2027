package bg.sofia.uni.fmi.mjt.parking.vehicle;

public class Car extends AbstractVehicle {
    public static final int CAR_SURCHARGE = 15;

    public Car(LicensePlate licensePlate) {
        super(licensePlate);
    }

    @Override
    public int getSurcharge() {
        return CAR_SURCHARGE;
    }
}
