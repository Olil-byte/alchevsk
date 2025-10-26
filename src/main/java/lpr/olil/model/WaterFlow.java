package lpr.olil.model;

public class WaterFlow {
    private static final double MIN_TEMPERATURE = 20.0;

    private final double inletTemperature; // 'C
    private final double outletTemperature; // 'C

    private final double density; // Kg / m^3

    private final double conductivity; // J / (Kg * K)

    public WaterFlow(double inletTemperature, double outletTemperature, double density, double conductivity) {
        if (inletTemperature < MIN_TEMPERATURE) {
            throw new IllegalArgumentException(
                    String.format(
                            "Inlet temperature must be more than %f 'C",
                            MIN_TEMPERATURE
                    )
            );
        }

        if (inletTemperature > outletTemperature) {
            throw new IllegalArgumentException(
                        String.format(
                                "Inlet temperature (%f 'C) must be less than outlet temperature (%f 'C)",
                                inletTemperature,
                                outletTemperature
                        )
                    );
        }

        this.inletTemperature = inletTemperature;
        this.outletTemperature = outletTemperature;

        if (density <= 0.0) {
            throw new IllegalArgumentException(
                    String.format(
                            "Density must be positive, got %f",
                            density
                    )
            );
        }

        this.density = density;

        if (conductivity <= 0.0) {
            throw new IllegalArgumentException(
                    String.format(
                            "Conductivity must be positive, got %f",
                            conductivity
                    )
            );
        }
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
