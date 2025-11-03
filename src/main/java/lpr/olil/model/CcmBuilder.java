package lpr.olil.model;

import java.util.Objects;

public class CcmBuilder {
    public enum Geometry {
        CURVED,
        VERTICAL
    }

    private Geometry geometry;

    private Double castingSpeed;

    private Crystallizer crystallizer;

    public CcmBuilder() {
        geometry = null;

        castingSpeed = null;

        crystallizer = null;
    }

    public CcmBuilder withGeometry(Geometry geometry) {
        this.geometry = geometry;

        return this;
    }

    public CcmBuilder withCastingSpeed(double value) {
        castingSpeed = value;

        return this;
    }

    public CcmBuilder withCrystallizer(Crystallizer crystallizer) {
        this.crystallizer = crystallizer;

        return this;
    }

    public Ccm build() {
        if (Objects.nonNull(geometry) && Objects.nonNull(castingSpeed) && Objects.nonNull(crystallizer)) {
            return switch (geometry) {
                case CURVED -> new CurvedCcm(castingSpeed, crystallizer);
                case VERTICAL -> new VerticalCcm(castingSpeed, crystallizer);
            };
        }

        throw new IllegalStateException();
    }

}
