package bg.sofia.uni.fmi.mjt.laundry.program;

import bg.sofia.uni.fmi.mjt.laundry.garment.Wool;

public class WoolProgram implements WashProgram<Wool> {
    private static final int WOOL_PROGRAM_TEMPERATURE = 40;

    public WoolProgram() {
    }

    @Override
    public int getTemperature() {
        return WOOL_PROGRAM_TEMPERATURE;
    }

    @Override
    public void wash(Wool garment) {
        if (garment == null) {
            throw new IllegalArgumentException("Garment cannot be null.");
        }

        garment.markClean();
    }
}
