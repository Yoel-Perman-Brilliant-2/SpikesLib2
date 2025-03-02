package com.spikes2212.command.genericsubsystem.commands.smartmotorcontrollergenericsubsystem;

import com.spikes2212.command.genericsubsystem.smartmotorcontrollersubsystem.SmartMotorControllerGenericSubsystem;
import com.spikes2212.control.FeedForwardSettings;
import com.spikes2212.control.PIDSettings;
import com.spikes2212.util.UnifiedControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.Supplier;

/**
 * A command that moves a {@link SmartMotorControllerGenericSubsystem} using its master motor controller's control loops.
 *
 * @author Yoel Perman Brilliant
 * @see SmartMotorControllerGenericSubsystem
 */
public class MoveSmartMotorControllerGenericSubsystem extends CommandBase {

    /**
     * The {@link SmartMotorControllerGenericSubsystem} this command will run on.
     */
    protected final SmartMotorControllerGenericSubsystem subsystem;

    /**
     * The loop's PID constants.
     */
    protected final PIDSettings pidSettings;

    /**
     * The loop's feed forward gains.
     */
    protected final FeedForwardSettings feedForwardSettings;

    /**
     * The loop's control mode (e.g. voltage, velocity, position...).
     */
    protected final UnifiedControlMode controlMode;

    /**
     * The setpoint this command should bring the {@link SmartMotorControllerGenericSubsystem} to.
     */
    protected final Supplier<Double> setpoint;

    /**
     * The most recent timestamp on which the loop has not reached its target setpoint.
     */
    private double lastTimeNotOnTarget;

    /**
     * Constructs a new instance of {@link MoveSmartMotorControllerGenericSubsystem}.
     *
     * @param subsystem           the {@link SmartMotorControllerGenericSubsystem} this command will run on
     * @param pidSettings         the loop's PID constants
     * @param feedForwardSettings the loop's feed forward gains
     * @param controlMode         the loop's control mode (e.g. voltage, velocity, position...)
     * @param setpoint            the setpoint this command should bring the {@link SmartMotorControllerGenericSubsystem} to
     */
    public MoveSmartMotorControllerGenericSubsystem(SmartMotorControllerGenericSubsystem subsystem, PIDSettings pidSettings,
                                                    FeedForwardSettings feedForwardSettings,
                                                    UnifiedControlMode controlMode, Supplier<Double> setpoint) {
        addRequirements(subsystem);
        this.subsystem = subsystem;
        this.controlMode = controlMode;
        this.pidSettings = pidSettings;
        this.feedForwardSettings = feedForwardSettings;
        this.setpoint = setpoint;
        this.lastTimeNotOnTarget = 0;
    }

    /**
     * Configures the subsystem's control loops.
     */
    @Override
    public void initialize() {
        subsystem.configureLoop(pidSettings, feedForwardSettings);
    }

    /**
     * Updates any control loops running on the subsystem.
     */
    @Override
    public void execute() {
        subsystem.pidSet(controlMode, setpoint.get(), pidSettings, feedForwardSettings);
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.finish();
    }

    /**
     * Checks if the subsystem has been at its target setpoint for longer than its allowed wait time.
     *
     * @return {@code true} if the command should finish running, {@code false} otherwise
     */
    @Override
    public boolean isFinished() {
        if (!subsystem.onTarget(controlMode, pidSettings.getTolerance(), setpoint.get())) {
            lastTimeNotOnTarget = Timer.getFPGATimestamp();
        }
        return Timer.getFPGATimestamp() - lastTimeNotOnTarget >= pidSettings.getWaitTime();
    }
}
