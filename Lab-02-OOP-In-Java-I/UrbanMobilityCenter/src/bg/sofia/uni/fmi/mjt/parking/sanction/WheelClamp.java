package bg.sofia.uni.fmi.mjt.parking.sanction;

import bg.sofia.uni.fmi.mjt.parking.vehicle.Vehicle;
import bg.sofia.uni.fmi.mjt.parking.zone.ParkingZone;

public class WheelClamp extends AbstractSanction {

    public static final int WHEEL_CLAMP_PRICE = 15;

    public WheelClamp(Vehicle vehicle, ParkingZone zone) {
        super(vehicle, zone);
    }

    @Override
    protected int getSanctionPrice() {
        return WHEEL_CLAMP_PRICE;
    }
}
