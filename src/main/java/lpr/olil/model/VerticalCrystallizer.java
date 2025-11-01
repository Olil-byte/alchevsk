package lpr.olil.model;

public class VerticalCrystallizer extends Crystallizer {
    private final Wall wall;
    private final double ductDiameter;

    private final double castingSpeed;

    VerticalCrystallizer(Wall wall, double ductDiameter, double castingSpeed) {
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
