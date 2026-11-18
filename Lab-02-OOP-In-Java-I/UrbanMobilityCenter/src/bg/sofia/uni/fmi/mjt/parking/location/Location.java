package bg.sofia.uni.fmi.mjt.parking.location;

public class Location {
    private final double x;
    private final double y;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDistanceToOther(Location location) {
        return Math.sqrt(Math.pow((location.getX() - this.x), 2) + Math.pow((location.getY() - this.y), 2));
    }
}
