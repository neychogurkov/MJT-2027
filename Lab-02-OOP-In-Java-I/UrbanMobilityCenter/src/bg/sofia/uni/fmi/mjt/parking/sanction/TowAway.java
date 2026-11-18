package bg.sofia.uni.fmi.mjt.parking.sanction;

import bg.sofia.uni.fmi.mjt.parking.vehicle.Vehicle;
import bg.sofia.uni.fmi.mjt.parking.zone.ParkingZone;

public class TowAway extends AbstractSanction {

    public static final int TOW_AWAY_PRICE = 30;

    public TowAway(Vehicle vehicle, ParkingZone zone) {
        super(vehicle, zone);
    }

    @Override
    protected int getSanctionPrice() {
        return TOW_AWAY_PRICE;
    }
}
