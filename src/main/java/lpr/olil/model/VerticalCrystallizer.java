package lpr.olil.model;

public class VerticalCrystallizer extends Crystallizer {
    private double castingSpeed;

    VerticalCrystallizer(double castingSpeed) {
        this.castingSpeed = castingSpeed;
    }

    @Override
    public double getCastingSpeed() {
        return castingSpeed;
    }
}
