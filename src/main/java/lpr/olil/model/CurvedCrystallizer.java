package lpr.olil.model;

public class CurvedCrystallizer extends Crystallizer {
    private double castingSpeed;

    CurvedCrystallizer(double castingSpeed) {
        this.castingSpeed = castingSpeed;
    }

    @Override
    public double getCastingSpeed() {
        return castingSpeed;
    }
}
