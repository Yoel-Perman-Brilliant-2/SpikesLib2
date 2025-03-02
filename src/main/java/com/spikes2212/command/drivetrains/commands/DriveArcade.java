package com.spikes2212.command.drivetrains.commands;

import com.spikes2212.command.drivetrains.TankDrivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.Supplier;

/**
 * A command that moves a {@link TankDrivetrain} using linear and rotational speeds.
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
     * Constructs a new {@link DriveArcade} command that moves the given
     * {@link TankDrivetrain} according to speed values from Double {@link Supplier}s
     * for linear and rotational movements.
     *
     * @param drivetrain          the tank drivetrain this command operates on
     * @param moveValueSupplier   the Double {@link Supplier} supplying the linear speed (-1 to 1).
     *                            Positive values go forwards
     * @param rotateValueSupplier the Double {@link Supplier} supplying the rotational speed (-1 to 1).
     *                            Positive values go clockwise
     * @param isFinished          when to finish the command
     * @param squareInputs        whether to square the speed suppliers' values
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

    /**
     * Constructs a new {@link DriveArcade} command that moves the given
     * {@link TankDrivetrain} according to speed values from Double {@link Supplier}s
     * for linear and rotational movements. Does not square the inputs.
     *
     * @param drivetrain          the tank drivetrain this command operates on
     * @param moveValueSupplier   the Double {@link Supplier} supplying the linear speed (-1 to 1).
     *                            Positive values go forwards
     * @param rotateValueSupplier the Double {@link Supplier} supplying the rotational speed (-1 to 1).
     *                            Positive values go clockwise
     * @param isFinished          when to finish the command
     */
    public DriveArcade(TankDrivetrain drivetrain, Supplier<Double> moveValueSupplier,
                       Supplier<Double> rotateValueSupplier, Supplier<Boolean> isFinished) {
        this(drivetrain, moveValueSupplier, rotateValueSupplier, isFinished, false);
    }

    /**
     * Constructs a new {@link DriveArcade} command that moves the given
     * {@link TankDrivetrain} according to speed values from Double {@link Supplier}s
     * for linear and rotational movements.
     *
     * @param drivetrain          the tank drivetrain this command operates on
     * @param moveValueSupplier   the Double {@link Supplier} supplying the linear speed (-1 to 1).
     *                            Positive values go forwards
     * @param rotateValueSupplier the Double {@link Supplier} supplying the rotational speed (-1 to 1).
     *                            Positive values go clockwise
     * @param squareInputs        whether to square the speed suppliers' values
     */
    public DriveArcade(TankDrivetrain drivetrain, Supplier<Double> moveValueSupplier,
                       Supplier<Double> rotateValueSupplier, boolean squareInputs) {
        this(drivetrain, moveValueSupplier, rotateValueSupplier, () -> false, squareInputs);
    }

    /**
     * Constructs a new {@link DriveArcade} command that moves the given
     * {@link TankDrivetrain} according to speed values from Double {@link Supplier}s
     * for linear and rotational movements. Does not square the inputs.
     *
     * @param drivetrain          the tank drivetrain this command operates on
     * @param moveValueSupplier   the Double {@link Supplier} supplying the linear speed (-1 to 1).
     *                            Positive values go forwards
     * @param rotateValueSupplier the Double {@link Supplier} supplying the rotational speed (-1 to 1).
     *                            Positive values go clockwise
     */
    public DriveArcade(TankDrivetrain drivetrain, Supplier<Double> moveValueSupplier,
                       Supplier<Double> rotateValueSupplier) {
        this(drivetrain, moveValueSupplier, rotateValueSupplier, () -> false, false);
    }

    /**
     * Constructs a new {@link DriveArcade} command that moves the given
     * {@link TankDrivetrain} according to speed values for linear and rotational movements.
     *
     * @param drivetrain   the tank drivetrain this command operates on
     * @param moveValue    the linear speed (-1 to 1). Positive values go forwards
     * @param rotateValue  the rotational speed (-1 to 1). Positive values go clockwise
     * @param isFinished   when to finish the command
     * @param squareInputs whether to square the speed values
     */
    public DriveArcade(TankDrivetrain drivetrain, double moveValue,
                       double rotateValue, Supplier<Boolean> isFinished, boolean squareInputs) {
        this(drivetrain, () -> moveValue, () -> rotateValue, isFinished, squareInputs);
    }

    /**
     * Constructs a new {@link DriveArcade} command that moves the given
     * {@link TankDrivetrain} according to speed values for linear and rotational movements. Does not square the inputs.
     *
     * @param drivetrain   the tank drivetrain this command operates on
     * @param moveValue    the linear speed (-1 to 1). Positive values go forwards
     * @param rotateValue  the rotational speed (-1 to 1). Positive values go clockwise
     * @param isFinished   when to finish the command
     */
    public DriveArcade(TankDrivetrain drivetrain, double moveValue,
                       double rotateValue, Supplier<Boolean> isFinished) {
        this(drivetrain, moveValue, rotateValue, isFinished, false);
    }

    /**
     * Constructs a new {@link DriveArcade} command that moves the given
     * {@link TankDrivetrain} according to speed values for linear and rotational movements.
     *
     * @param drivetrain   the tank drivetrain this command operates on
     * @param moveValue    the linear speed (-1 to 1). Positive values go forwards
     * @param rotateValue  the rotational speed (-1 to 1). Positive values go clockwise
     * @param squareInputs whether to square the speed values
     */
    public DriveArcade(TankDrivetrain drivetrain, double moveValue,
                       double rotateValue, boolean squareInputs) {
        this(drivetrain, moveValue, rotateValue, () -> false, squareInputs);
    }

    /**
     * Constructs a new {@link DriveArcade} command that moves the given
     * {@link TankDrivetrain} according to speed values for linear and rotational movements. Does not square the inputs.
     *
     * @param drivetrain   the tank drivetrain this command operates on
     * @param moveValue    the linear speed (-1 to 1). Positive values go forwards
     * @param rotateValue  the rotational speed (-1 to 1). Positive values go clockwise
     */
    public DriveArcade(TankDrivetrain drivetrain, double moveValue,
                       double rotateValue) {
        this(drivetrain, moveValue, rotateValue, () -> false, false);
    }

    @Override
    public void execute() {
        tankDrivetrain.arcadeDrive(moveValueSupplier.get(), rotateValueSupplier.get(), squareInputs);
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
