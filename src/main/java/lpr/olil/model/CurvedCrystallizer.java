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
    private final Wall wall;
    private final double ductDiameter;
    private final double castingSpeed;

    public CurvedCrystallizer(Wall wall, double ductDiameter, double castingSpeed) {
        final boolean isNegativeCastingSpeed = castingSpeed < 0.0;

        if (isNegativeCastingSpeed) {
            throw new InvalidCurvedCrystallizerException(isNegativeCastingSpeed);
        }

        this.wall = wall;
        this.ductDiameter = ductDiameter;

        this.castingSpeed = castingSpeed;
    }

    @Override
    public Wall getWall() {
        return wall;
    }

    @Override
    public double getDuctDiameter() {
        return ductDiameter;
    }

    @Override
    public double getCastingSpeed() {
        return castingSpeed;
    }
}
