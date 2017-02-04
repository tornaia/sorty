package com.github.tornaia.sorty.image;

public class Distance {

    private final double amount;
    private final DistanceUnit unit;

    private Distance(double amount, DistanceUnit unit) {
        this.amount = amount;
        this.unit = unit;
    }

    public static Distance ofKilometer(double amount) {
        return new Distance(amount, DistanceUnit.KILOMETER);
    }

    public static Distance ofMeter(double amount) {
        return new Distance(amount, DistanceUnit.METER);
    }

    public static Distance max(Distance a, Distance b) {
        return a.getAmountInMeter() > b.getAmountInMeter() ? a : b;
    }

    public boolean isSmaller(Distance otherDistance) {
        double amountInMeter = getAmountInMeter();
        double otherAmountInMeter = otherDistance.getAmountInMeter();
        return amountInMeter < otherAmountInMeter;
    }

    public double getAmountInMeter() {
        return amount * unit.conversionFactor(DistanceUnit.METER);
    }
}
