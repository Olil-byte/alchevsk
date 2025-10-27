package lpr.olil.model;

class InvalidWaterFlowException extends RuntimeException {
    private final boolean isInletTemperatureBelowMinimal;
    private final boolean isInletTemperatureNotLessThanOutlet;
    private final boolean isDensityNonPositive;
    private final boolean isConductivityNonPositive;

    public InvalidWaterFlowException(
            boolean isInletTemperatureBelowMinimal,
            boolean isInletTemperatureNotLessThanOutlet,
            boolean isDensityNonPositive,
            boolean isConductivityNonPositive)
    {
        super();

        this.isInletTemperatureBelowMinimal = isInletTemperatureBelowMinimal;
        this.isInletTemperatureNotLessThanOutlet = isInletTemperatureNotLessThanOutlet;
        this.isDensityNonPositive = isDensityNonPositive;
        this.isConductivityNonPositive = isConductivityNonPositive;
    }

    public boolean isInletTemperatureBelowMinimal() {
        return isInletTemperatureBelowMinimal;
    }
    public boolean isInletTemperatureNotLessThanOutlet() {
        return isInletTemperatureNotLessThanOutlet;
    }
    public boolean isDensityNonPositive() {
        return isDensityNonPositive;
    }
    public boolean isConductivityNonPositive() {
        return isConductivityNonPositive;
    }
}

public class WaterFlow {
    private static final double MIN_TEMPERATURE = 20.0;

    private final double inletTemperature; // 'C
    private final double outletTemperature; // 'C

    private final double density; // Kg / m^3

    private final double conductivity; // J / (Kg * K)

    public WaterFlow(double inletTemperature, double outletTemperature, double density, double conductivity) {
        final boolean isInletTemperatureBelowMinimal = inletTemperature < 20.0;
        final boolean isInletTemperatureNotLessThanOutlet = inletTemperature >= outletTemperature;
        final boolean isDensityNonPositive = density <= 0.0;
        final boolean isConductivityNonPositive = conductivity <= 0.0;

        if (isInletTemperatureBelowMinimal ||
                isInletTemperatureNotLessThanOutlet ||
                isDensityNonPositive ||
                isConductivityNonPositive
        ) {
            throw new InvalidWaterFlowException(
                    isInletTemperatureBelowMinimal,
                    isInletTemperatureNotLessThanOutlet,
                    isDensityNonPositive,
                    isConductivityNonPositive
            );
        }

        this.inletTemperature = inletTemperature;
        this.outletTemperature = outletTemperature;

        this.density = density;
        this.conductivity = conductivity;
    }

    public double getInletTemperature() {
        return inletTemperature;
    }
    public double getOutletTemperature() {
        return outletTemperature;
    }

    public double getDensity() {
        return density;
    }

    public double getConductivity() {
        return conductivity;
    }
}
