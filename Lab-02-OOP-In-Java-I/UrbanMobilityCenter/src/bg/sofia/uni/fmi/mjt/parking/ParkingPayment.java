package bg.sofia.uni.fmi.mjt.parking;

import bg.sofia.uni.fmi.mjt.parking.vehicle.LicensePlate;
import bg.sofia.uni.fmi.mjt.parking.zone.ParkingZone;

import java.time.LocalDateTime;

public class ParkingPayment {
    private final LicensePlate licensePlate;
    private final ParkingZone zone;
    private final LocalDateTime paidFrom;

    public ParkingPayment(LicensePlate licensePlate, ParkingZone zone, LocalDateTime moment) {
        this.licensePlate = licensePlate;
        this.zone = zone;
        this.paidFrom = moment;
    }

    public boolean isValid(LicensePlate licensePlate, ParkingZone zone, LocalDateTime moment) {
        return this.licensePlate.getNumber().equals(licensePlate.getNumber())
                && this.zone == zone
                && !moment.isBefore(paidFrom)
                && moment.isBefore(paidFrom.plusHours(1));
    }
}
