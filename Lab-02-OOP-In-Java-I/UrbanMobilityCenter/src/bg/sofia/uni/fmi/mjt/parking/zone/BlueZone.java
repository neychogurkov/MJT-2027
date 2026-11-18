package bg.sofia.uni.fmi.mjt.parking.zone;

import bg.sofia.uni.fmi.mjt.parking.location.Location;

public class BlueZone implements ParkingZone {
    private final String name;
    private final Location center;
    private final double radius;

    public BlueZone(String name, Location center, double radius) {
        this.name = name;
        this.center = center;
        this.radius = radius;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSmsNumber() {
        return "1302";
    }

    @Override
    public boolean contains(Location location) {
        if (location == null) {
            return false;
        }

        double distance = center.getDistanceToOther(location);

        return distance <= radius;
    }
}
