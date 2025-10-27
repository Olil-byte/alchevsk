package lpr.olil.model;

class InvalidCurvedCrystallizerException extends RuntimeException {
    private final boolean isNegativeCastingSpeed;

    public InvalidCurvedCrystallizerException(boolean isNegativeCastingSpeed) {
        this.isNegativeCastingSpeed = isNegativeCastingSpeed;
    }

    public boolean isNegativeCastingSpeed() {
        return isNegativeCastingSpeed;
    }
}

public class CurvedCrystallizer extends Crystallizer {
    private double castingSpeed;

    public CurvedCrystallizer(double castingSpeed) {
        final boolean isNegativeCastingSpeed = castingSpeed < 0.0;

        if (isNegativeCastingSpeed) {
            throw new InvalidCurvedCrystallizerException(isNegativeCastingSpeed);
        }

        this.castingSpeed = castingSpeed;
    }

    @Override
    public double getCastingSpeed() {
        return castingSpeed;
    }
}
