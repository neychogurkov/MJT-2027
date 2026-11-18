package bg.sofia.uni.fmi.mjt.parking;

import bg.sofia.uni.fmi.mjt.parking.location.Location;
import bg.sofia.uni.fmi.mjt.parking.sanction.Sanction;
import bg.sofia.uni.fmi.mjt.parking.sanction.TowAway;
import bg.sofia.uni.fmi.mjt.parking.sanction.WheelClamp;
import bg.sofia.uni.fmi.mjt.parking.vehicle.LicensePlate;
import bg.sofia.uni.fmi.mjt.parking.vehicle.Vehicle;
import bg.sofia.uni.fmi.mjt.parking.zone.BlueZone;
import bg.sofia.uni.fmi.mjt.parking.zone.GreenZone;
import bg.sofia.uni.fmi.mjt.parking.zone.ParkingZone;

import java.time.LocalDateTime;
import java.util.Arrays;

public class UrbanMobilityCenter implements ParkingClientAPI, ParkingInspectorAPI {

    private final BlueZone blueZone;
    private final GreenZone greenZone;

    private ParkingPayment[] payments;
    private int paymentCount;

    public UrbanMobilityCenter(BlueZone blueZone, GreenZone greenZone) {
        this.blueZone = blueZone;
        this.greenZone = greenZone;

        this.payments = new ParkingPayment[0];
        this.paymentCount = 0;
    }

    @Override
    public boolean sendSms(String smsNumber, String plate, LocalDateTime moment) {
        if (smsNumber == null || plate == null || moment == null) {
            return false;
        }

        LicensePlate licensePlate = LicensePlate.parse(plate);

        if (licensePlate == null) {
            return false;
        }

        ParkingZone zone = getZoneBySmsNumber(smsNumber);

        if (zone == null) {
            return false;
        }

        payments = Arrays.copyOf(payments, paymentCount + 1);
        payments[paymentCount++] = new ParkingPayment(licensePlate, zone, moment);

        return true;
    }

    @Override
    public Sanction inspect(Vehicle vehicle, Location location, LocalDateTime moment) {
        if (vehicle == null || location == null || moment == null ||
                vehicle.getLicensePlate() == null) {
            return null;
        }

        ParkingZone zone;

        if (blueZone.contains(location)) {
            zone = blueZone;
        } else if (greenZone.contains(location)) {
            zone = greenZone;
        } else {
            return null;
        }

        if (isParkingPaid(vehicle.getLicensePlate(), zone, moment)) {
            return null;
        }

        if (zone instanceof BlueZone) {
            return new TowAway(vehicle, blueZone);
        }

        return new WheelClamp(vehicle, greenZone);
    }

    private ParkingZone getZoneBySmsNumber(String smsNumber) {
        if (blueZone.getSmsNumber().equals(smsNumber)) {
            return blueZone;
        }

        if (greenZone.getSmsNumber().equals(smsNumber)) {
            return greenZone;
        }

        return null;
    }

    private boolean isParkingPaid(LicensePlate plate, ParkingZone zone, LocalDateTime moment) {
        for (int i = 0; i < paymentCount; i++) {
            if (payments[i].isValid(plate, zone, moment)) {
                return true;
            }
        }

        return false;
    }
}
