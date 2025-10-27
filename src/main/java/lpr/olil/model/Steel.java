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
//    private static final Map<Double, Double> LIQUIDUS_BY_CARBON = Map.ofEntries(
//            entry(0.0000, 1539.0),
//            entry(0.0005, 1536.0),
//            entry(0.0010, 1532.0),
//            entry(0.0015, 1529.0),
//            entry(0.0020, 1526.0),
//            entry(0.0025, 1522.0),
//            entry(0.0030, 1519.0),
//            entry(0.0035, 1516.0),
//            entry(0.0040, 1512.0),
//            entry(0.0045, 1509.0),
//            entry(0.0050, 1505.0),
//            entry(0.0055, 1502.0),
//            entry(0.0060, 1499.0),
//            entry(0.0065, 1495.0),
//            entry(0.0070, 1492.0),
//            entry(0.0080, 1485.0),
//            entry(0.0090, 1479.0),
//            entry(0.0100, 1469.0),
//            entry(0.0110, 1459.0),
//            entry(0.0120, 1452.0),
//            entry(0.0130, 1445.0)
//    );
//
//    private double interpolateLiquidus() {
//        if (LIQUIDUS_BY_CARBON.containsKey(carbonConcentration)) {
//            return LIQUIDUS_BY_CARBON.get(carbonConcentration);
//        }
//
//        Set<Double> keySet = LIQUIDUS_BY_CARBON.keySet();
//
//        Double left = null;
//        Double right = null;
//
//        for (Double carbon : keySet) {
//            if (carbon < carbonConcentration) {
//                if (left == null || carbon > left) {
//                    left = carbon;
//                }
//            }
//
//            if (carbon > carbonConcentration) {
//                if (right == null || carbon < right) {
//                    right = carbon;
//                }
//            }
//        }
//
//        if (left == null || right == null) {
//            throw new IllegalArgumentException();
//        }
//
//        final double solidusLeft = LIQUIDUS_BY_CARBON.get(left);
//        final double solidusRight = LIQUIDUS_BY_CARBON.get(right);
//
//        return solidusLeft + (carbonConcentration - left) *
//                (solidusRight - solidusLeft) / (right - left);
//    }

    private final double carbon;

    private final double liquidus; // K
    private final double solidus; // K

    public Steel (double carbon) {
        this.carbon = carbon;

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
