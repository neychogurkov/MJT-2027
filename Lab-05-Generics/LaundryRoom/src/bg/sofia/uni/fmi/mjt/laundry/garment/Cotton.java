package bg.sofia.uni.fmi.mjt.laundry.garment;

public abstract class Cotton extends Garment {
    /**
     * @param brand    the brand of the garment
     * @param weightKg the weight of the garment in kilograms
     * @throws IllegalArgumentException if the brand is null or blank, or the weight is not positive
     */
    public Cotton(String brand, double weightKg) {
        super(brand, weightKg);
    }
}