package com.spikes2212.control;

import java.util.function.Supplier;

public class TrapezoidProfileSettings {

    public static final TrapezoidProfileSettings EMPTY_TRAPEZOID_PROFILE_SETTINGS =
            new TrapezoidProfileSettings(0, 0, 0);

    private Supplier<Double> accelerationRate;

    private Supplier<Double> maxVelocity;

    private Supplier<Integer> curve;

    public TrapezoidProfileSettings(Supplier<Double> accelerationRate, Supplier<Double> maxVelocity,
                                    Supplier<Integer> curve) {
        this.accelerationRate = accelerationRate;
        this.maxVelocity = maxVelocity;
        this.curve = curve;
    }

    public TrapezoidProfileSettings(Supplier<Double> accelerationRate, Supplier<Double> maxVelocity) {
        this(accelerationRate, maxVelocity, () -> 0);
    }

    public TrapezoidProfileSettings(double accelerationRate, double maxVelocity, int curve) {
        this(() -> accelerationRate, () -> maxVelocity, () -> curve);
    }

    public TrapezoidProfileSettings(double accelerationRate, double maxVelocity) {
        this(accelerationRate, maxVelocity, 0);
    }

    public double getAccelerationRate() {
        return accelerationRate.get();
    }

    public void setAccelerationRate(Supplier<Double> accelerationRate) {
        this.accelerationRate = accelerationRate;
    }

    public double getMaxVelocity() {
        return maxVelocity.get();
    }

    public void setMaxVelocity(Supplier<Double> maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public int getCurve() {
        return curve.get();
    }

    public void setCurve(Supplier<Integer> curve) {
        this.curve = curve;
    }
}
