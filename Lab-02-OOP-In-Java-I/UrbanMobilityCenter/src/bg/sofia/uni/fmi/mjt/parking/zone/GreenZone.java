package bg.sofia.uni.fmi.mjt.parking.zone;

import bg.sofia.uni.fmi.mjt.parking.location.Location;

public class GreenZone implements ParkingZone {
    private final String name;
    private final Location center;
    private final double innerRadius;
    private final double outerRadius;

    public GreenZone(String name, Location center, double innerRadius, double outerRadius) {
        this.name = name;
        this.center = center;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSmsNumber() {
        return "1303";
    }

    @Override
    public boolean contains(Location location) {
        if (location == null) {
            return false;
        }

        double distance = center.getDistanceToOther(location);

        return distance > innerRadius && distance <= outerRadius;
    }
}
