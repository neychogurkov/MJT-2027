package bg.sofia.uni.fmi.mjt.laundry.program;

import bg.sofia.uni.fmi.mjt.laundry.garment.Cotton;

public class CottonProgram implements WashProgram<Cotton> {
    private static final int COTTON_PROGRAM_TEMPERATURE = 90;

    public CottonProgram() {
    }

    @Override
    public int getTemperature() {
        return COTTON_PROGRAM_TEMPERATURE;
    }

    @Override
    public void wash(Cotton garment) {
        if (garment == null) {
            throw new IllegalArgumentException("Garment cannot be null.");
        }

        garment.markClean();
    }
}
