package com.spikes2212.command.drivetrains.commands;

import com.spikes2212.command.drivetrains.TankDrivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.Supplier;

/**
 * This command moves a {@link TankDrivetrain} by linear and rotational speeds, using
 * the arcade control method written by WPILIB.
 *
 * @author Yuval Levy
 * @see TankDrivetrain
 */

public class DriveArcade extends CommandBase {

    protected final TankDrivetrain tankDrivetrain;
    protected final Supplier<Double> moveValueSupplier;
    protected final Supplier<Double> rotateValueSupplier;
    protected final Supplier<Boolean> isFinished;
    /**
     * Whether to square the velocity suppliers.
     */
    protected final boolean squareInputs;

    /**
     * This constructs a new {@link DriveArcade} command that moves the given
     * {@link TankDrivetrain} according to speed values from Double {@link Supplier}s
     * for linear and rotational movements.
     *
     * @param drivetrain          the tank drivetrain this command operates on.
     * @param moveValueSupplier   the double {@link Supplier} supplying the linear velocity. Positive values go forwards.
     * @param rotateValueSupplier the double {@link Supplier} supplying the rotational velocity. Positive values go left.
     */
    public DriveArcade(TankDrivetrain drivetrain, Supplier<Double> moveValueSupplier,
                       Supplier<Double> rotateValueSupplier, Supplier<Boolean> isFinished, boolean squareInputs) {
        addRequirements(drivetrain);
        this.tankDrivetrain = drivetrain;
        this.moveValueSupplier = moveValueSupplier;
        this.rotateValueSupplier = rotateValueSupplier;
        this.isFinished = isFinished;
        this.squareInputs = squareInputs;
    }

    public DriveArcade(TankDrivetrain drivetrain, Supplier<Double> moveValueSupplier,
                       Supplier<Double> rotateValueSupplier, Supplier<Boolean> isFinished) {
        this(drivetrain, moveValueSupplier, rotateValueSupplier, isFinished, false);
    }

    public DriveArcade(TankDrivetrain drivetrain, Supplier<Double> moveValueSupplier,
                       Supplier<Double> rotateValueSupplier, boolean squareInputs) {
        this(drivetrain, moveValueSupplier, rotateValueSupplier, () -> false, squareInputs);
    }

    public DriveArcade(TankDrivetrain drivetrain, Supplier<Double> moveValueSupplier,
                       Supplier<Double> rotateValueSupplier) {
        this(drivetrain, moveValueSupplier, rotateValueSupplier, () -> false, false);
    }

    public DriveArcade(TankDrivetrain drivetrain, double moveValueSupplier,
                       double rotateValueSupplier, Supplier<Boolean> isFinished, boolean squareInputs) {
        this(drivetrain, () -> moveValueSupplier, () -> rotateValueSupplier, isFinished, squareInputs);
    }

    public DriveArcade(TankDrivetrain drivetrain, double moveValueSupplier,
                       double rotateValueSupplier, Supplier<Boolean> isFinished) {
        this(drivetrain, moveValueSupplier, rotateValueSupplier, isFinished, false);
    }

    public DriveArcade(TankDrivetrain drivetrain, double moveValueSupplier,
                       double rotateValueSupplier, boolean squareInputs) {
        this(drivetrain, moveValueSupplier, rotateValueSupplier, () -> false, squareInputs);
    }

    public DriveArcade(TankDrivetrain drivetrain, double moveValueSupplier,
                       double rotateValueSupplier) {
        this(drivetrain, moveValueSupplier, rotateValueSupplier, () -> false, false);
    }

    @Override
    public void execute() {
        tankDrivetrain.arcadeDrive(moveValueSupplier.get(), rotateValueSupplier.get());
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
