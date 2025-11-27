package lpr.olil.model;

class InvalidSteelException extends RuntimeException {
    private final boolean isNegativeCarbon;
    private final boolean isCarbonMoreThan100Percents;

    public InvalidSteelException(boolean isNegativeCarbon, boolean isCarbonMoreThan100Percents) {
        this.isNegativeCarbon = isNegativeCarbon;
        this.isCarbonMoreThan100Percents = isCarbonMoreThan100Percents;
    }

    public boolean isNegativeCarbon() {
        return isNegativeCarbon;
    }

    public boolean isCarbonMoreThan100Percents() {
        return isCarbonMoreThan100Percents;
    }
}

public class Steel extends Metal {
    private final double hydrogen;
    private final double nitrogen;
    private final double oxygen;
    private final double carbon;
    private final double phosphor;
    private final double sulfur;
    private final double arsenic;
    private final double lead;
    private final double silicon;
    private final double manganese;
    private final double copper;
    private final double nickel;
    private final double molybdenum;
    private final double vanadium;
    private final double chromium;
    private final double aluminum;
    private final double tungsten;

    private final double liquidus; // K
    private final double solidus; // K

    public Steel(
            double hydrogen,
            double nitrogen,
            double oxygen,
            double carbon,
            double phosphor,
            double sulfur,
            double arsenic,
            double lead,
            double silicon,
            double manganese,
            double copper,
            double nickel,
            double molybdenum,
            double vanadium,
            double chromium,
            double aluminum,
            double tungsten
    ) {
        this.hydrogen = hydrogen;
        this.nitrogen = nitrogen;
        this.oxygen = oxygen;
        this.carbon = carbon;
        this.phosphor = phosphor;
        this.sulfur = sulfur;
        this.arsenic = arsenic;
        this.lead = lead;
        this.silicon = silicon;
        this.manganese = manganese;
        this.copper = copper;
        this.nickel = nickel;
        this.molybdenum = molybdenum;
        this.vanadium = vanadium;
        this.chromium = chromium;
        this.aluminum = aluminum;
        this.tungsten = tungsten;

        double deltaT = 0.0;

        if (this.hydrogen >= 0.0 && this.hydrogen <= 1.0e-3) {
            deltaT += 1300.0 * this.hydrogen;
        }

        if (this.nitrogen >= 0.0 && this.nitrogen <= 0.03) {
            deltaT += 90.0 * this.nitrogen;
        }

        if (this.oxygen >= 0.0 && this.oxygen <= 0.03) {
            deltaT += 80.0 * this.oxygen;
        }

        if (this.carbon >= 0 && this.carbon < 1.0) {
            deltaT += 65.0 * this.carbon;
        } else if (this.carbon >= 1.0 && this.carbon < 2.0) {
            deltaT += 70.0 * this.carbon;
        } else if (this.carbon >= 2.0 && this.carbon < 2.5) {
            deltaT += 75.0 * this.carbon;
        } else if (this.carbon >= 2.5 && this.carbon < 3.0) {
            deltaT += 80.0 * this.carbon;
        } else if (this.carbon >= 3.0 && this.carbon < 3.5) {
            deltaT += 85.0 * this.carbon;
        } else if (this.carbon >= 3.5 && this.carbon < 4.0) {
            deltaT += 91.0 * this.carbon;
        } else if (this.carbon >= 4.0) {
            deltaT += 100.0 * this.carbon;
        }

        if (this.phosphor >= 0.0 && this.phosphor <= 0.7) {
            deltaT += 30.0 * this.phosphor;
        }

        if (this.sulfur >= 0.0 && this.sulfur <= 0.08) {
            deltaT += 25.0 * this.phosphor;
        }

        if (this.arsenic >= 0.0 && this.arsenic <= 0.5) {
            deltaT += 14.0 * this.arsenic;
        }

        if (this.lead >= 0.0 && this.lead <= 0.3) {
            deltaT += 10.0 * this.lead;
        }

        if (this.silicon >= 0.0 && this.silicon <= 3.0) {
            deltaT += 12.0 * this.silicon;
        }

        if (this.manganese >= 0.0 && this.manganese <= 1.5) {
            deltaT += 5.0 * this.manganese;
        }

        if (this.copper >= 0.0 && this.copper <= 0.3) {
            deltaT += 5.0 * this.copper;
        }

        if (this.nickel >= 0.0 && this.nickel <= 9.0) {
            deltaT += 4.0 * this.nickel;
        }

        if (this.molybdenum >= 0.0 && this.molybdenum <= 0.3) {
            deltaT += 2.0 * this.molybdenum;
        }

        if (this.vanadium >= 0.0 && this.vanadium <= 1.0) {
            deltaT += 2.0 * this.vanadium;
        }

        if (this.chromium >= 0.0 && this.chromium <= 18.0) {
            deltaT += 1.5 * this.chromium;
        }

        if (this.aluminum >= 0.0 && this.aluminum <= 1.0) {
            deltaT += 1.0 * this.aluminum;
        }

        if (this.tungsten == 18.0) {
            deltaT += 1.0 * this.tungsten;
        }

        final boolean isNegativeCarbon = carbon < 0.0;
        final boolean isCarbonMoreThan100Percents = carbon > 1.0;

        if (isNegativeCarbon || isCarbonMoreThan100Percents) {
            throw new InvalidSteelException(isNegativeCarbon, isCarbonMoreThan100Percents);
        }

        this.liquidus = 1812.0 - 80.0 * carbon;
        this.solidus = 1786.0 - 156.0 * carbon;
    }

    @Override
    public double getSolidus() {
        return solidus;
    }
    @Override
    public double getLiquidus() {
        return liquidus;
    }
}
