package com.github.tornaia.sorty.image;

public enum DistanceUnit {

    METER {
        @Override
        protected double conversionFactor(DistanceUnit toDistanceUnit) {
            switch (toDistanceUnit) {
                case METER:
                    return 1;
                case KILOMETER:
                    return 0.0001D;
                default:
                    throw new UnsupportedOperationException(toDistanceUnit + " is not supported");
            }
        }
    },
    KILOMETER {
        @Override
        protected double conversionFactor(DistanceUnit toDistanceUnit) {
            switch (toDistanceUnit) {
                case METER:
                    return 1000;
                case KILOMETER:
                    return 1;
                default:
                    throw new UnsupportedOperationException(toDistanceUnit + " is not supported");
            }
        }
    };

    protected abstract double conversionFactor(DistanceUnit toDistanceUnit);

}