package com.spikes2212.command.genericsubsystem.smartmotorcontrollersubsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.spikes2212.command.genericsubsystem.GenericSubsystem;
import com.spikes2212.control.FeedForwardSettings;
import com.spikes2212.control.PIDSettings;
import com.spikes2212.control.TrapezoidProfileSettings;

import java.util.List;

public class SparkMaxGenericSubsystem extends GenericSubsystem implements SmartMotorControllerSubsystem {

    protected final CANSparkMax master;
    protected final List<CANSparkMax> slaves;

    private final int TRAPEZOID_SLOT_ID = 0;

    public SparkMaxGenericSubsystem(String namespaceName, CANSparkMax master, CANSparkMax slaves) {
        super(namespaceName);
        this.master = master;
        this.slaves = List.of(slaves);
        this.slaves.forEach(s -> s.follow(master));
    }

    @Override
    public void configureDashboard() {

    }

    @Override
    protected void apply(double speed) {
        master.set(speed);
    }

    @Override
    public boolean canMove(double speed) {
        return true;
    }

    @Override
    public void stop() {
        master.stopMotor();
    }

    @Override
    public void configPIDF(PIDSettings pidSettings, FeedForwardSettings feedForwardSettings) {
        master.getPIDController().setFF(feedForwardSettings.getkV());
        master.getPIDController().setP(pidSettings.getkP());
        master.getPIDController().setI(pidSettings.getkI());
        master.getPIDController().setD(pidSettings.getkD());
    }

    @Override
    public void configureTrapezoid(TrapezoidProfileSettings settings) {
        master.getPIDController().setSmartMotionMaxAccel(settings.getAccelerationRate(), TRAPEZOID_SLOT_ID);
        master.getPIDController().setSmartMotionMaxVelocity(settings.getMaxVelocity(), TRAPEZOID_SLOT_ID);
        master.getPIDController().setSmartMotionAccelStrategy(
                SparkMaxPIDController.AccelStrategy.fromInt(settings.getCurve()), TRAPEZOID_SLOT_ID);
    }

    @Override
    public void configureLoop(PIDSettings pidSettings, FeedForwardSettings feedForwardSettings,
                              TrapezoidProfileSettings trapezoidProfileSettings) {
        master.restoreFactoryDefaults();
        configPIDF(pidSettings, feedForwardSettings);
        configureTrapezoid(trapezoidProfileSettings);
    }

    @Override
    public void configureLoop(PIDSettings pidSettings, FeedForwardSettings feedForwardSettings) {
        master.restoreFactoryDefaults();
        configureLoop(pidSettings, feedForwardSettings, TrapezoidProfileSettings.EMPTY_TRAPEZOID_PROFILE_SETTINGS);
    }

    @Override
    public void pidSet(CANSparkMax.ControlType controlType, double setpoint, PIDSettings pidSettings,
                       FeedForwardSettings feedForwardSettings, TrapezoidProfileSettings trapezoidProfileSettings) {
        configPIDF(pidSettings, feedForwardSettings);
        configureTrapezoid(trapezoidProfileSettings);
        master.getPIDController().setReference(setpoint, controlType);
    }




    @Override
    public void finish() {
        stop();
    }
}
