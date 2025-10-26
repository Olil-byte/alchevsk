package lpr.olil.validator;

import lpr.olil.model.Slab;

import java.util.LinkedList;
import java.util.Objects;

import java.util.List;

public class SlabValidator {
    public static abstract class Error {
        public abstract double getValue();
    }

    public static class NegativeWidth extends Error {
        private final double value;

        private NegativeWidth(double value) {
            this.value = value;
        }

        @Override
        public double getValue() {
            return value;
        }
    }

    public static class NegativeHeight extends Error {
        private final double value;

        private NegativeHeight(double value) {
            this.value = value;
        }

        @Override
        public double getValue() {
            return value;
        }
    }

    private static List<Error> validate(final Slab slab) {
        Objects.requireNonNull(slab);

        LinkedList<Error> errors = new LinkedList<Error>();

        final double width = slab.getWidth();
        if (width < 0.0) {
            errors.add(new NegativeWidth(width));
        }

        final double height = slab.getHeight();
        if (height < 0.0) {
            errors.add(new NegativeHeight(height));
        }

        return errors;
    }
}
