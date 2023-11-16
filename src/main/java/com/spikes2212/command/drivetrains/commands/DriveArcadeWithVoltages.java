package com.spikes2212.command.drivetrains.commands;

import java.util.function.Supplier;

import com.spikes2212.command.drivetrains.TankDrivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * This command moves a {@link TankDrivetrain} by linear and rotational speeds, using
 * the arcade control method written by WPILIB.
 *
 * @author Yuval Levy
 * @see TankDrivetrain
 */

public class DriveArcadeWithVoltages extends CommandBase {

    protected final TankDrivetrain tankDrivetrain;
    protected final Supplier<Double> moveValueSupplier;
    protected final Supplier<Double> rotateValueSupplier;
    protected final Supplier<Boolean> isFinished;

    /**
     * This constructs a new {@link DriveArcadeWithVoltages} command that moves the given
     * {@link TankDrivetrain} according to Voltage values for Linear and rotational movements.
     *
     * @param drivetrain          the tank drivetrain this command operates on.
     * @param moveValueSupplier   the double {@link Supplier} supplying the linear Voltage. Positive values go forwards.
     * @param rotateValueSupplier the double {@link Supplier} supplying the rotational Voltage. Positive values go left.
     */
    public DriveArcadeWithVoltages(TankDrivetrain drivetrain, Supplier<Double> moveValueSupplier,
                                   Supplier<Double> rotateValueSupplier, Supplier<Boolean> isFinished) {
        addRequirements(drivetrain);
        this.tankDrivetrain = drivetrain;
        this.moveValueSupplier = moveValueSupplier;
        this.rotateValueSupplier = rotateValueSupplier;
        this.isFinished = isFinished;
    }

    public DriveArcadeWithVoltages(TankDrivetrain drivetrain, Supplier<Double> moveValueSupplier,
                                   Supplier<Double> rotateValueSupplier) {
        this(drivetrain, moveValueSupplier, rotateValueSupplier, () -> false);
    }

    public DriveArcadeWithVoltages(TankDrivetrain drivetrain, double moveValue, double rotateValue) {
        this(drivetrain, () -> moveValue, () -> rotateValue, () -> false);
    }

    public DriveArcadeWithVoltages(TankDrivetrain drivetrain, double moveValue, double rotateValue, Supplier<Boolean> isFinished) {
        this(drivetrain, () -> moveValue, () -> rotateValue, isFinished);
    }

    @Override
    public void execute() {
        tankDrivetrain.arcadeDriveVoltages(moveValueSupplier.get(), rotateValueSupplier.get());
    }

    @Override
    public boolean isFinished() {
        return isFinished.get();
    }

    @Override
    public void end(boolean interrupted) {
        tankDrivetrain.stop();
    }
}
