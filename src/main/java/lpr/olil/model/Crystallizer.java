package lpr.olil.model;

public class Crystallizer {
    private double ductDiameter;

    private Wall wall;

    public Crystallizer(Wall wall, double ductDiameter) {
        this.ductDiameter = ductDiameter;
        this.wall = wall;
    }

    public double getDuctDiameter() {
        return ductDiameter;
    }

    public Wall getWall() {
        return wall;
    }
}
