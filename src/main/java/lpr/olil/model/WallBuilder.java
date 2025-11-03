package lpr.olil.model;

import java.util.Objects;

public class WallBuilder {
    public static enum Type {
        SMOOTHED,
        PROFILED
    }

    private Type type;

    private Double length;
    private Double activeLength;

    private Double thickness;

    public WallBuilder() {
        type = null;

        length = null;
        activeLength = null;
        thickness = null;
    }

    public Wall build() {
        if (Objects.nonNull(type) && Objects.nonNull(length) && Objects.nonNull(activeLength) && Objects.nonNull(thickness)) {
            switch (type) {
                case PROFILED -> {
                    return new ProfiledWall(length, activeLength, thickness);
                }
                case SMOOTHED -> {
                    return new SmoothedWall(length, activeLength, thickness);
                }
            }
        }

        throw new IllegalStateException();
    }

    public WallBuilder ofType(Type type) {
        this.type = type;

        return this;
    }

    public WallBuilder withLength(double value) {
        length = value;

        return this;
    }

    public WallBuilder withActiveLength(double value) {
        activeLength = value;

        return this;
    }

    public WallBuilder withThickness(double value) {
        thickness = value;

        return this;
    }
}
