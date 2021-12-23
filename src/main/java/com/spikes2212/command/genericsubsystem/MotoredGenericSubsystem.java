package com.spikes2212.command.genericsubsystem;

import com.spikes2212.dashboard.RootNamespace;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import java.util.function.Supplier;

/**
 * This class represents a motored controlled generic subsystem.
 *
 * @author Ofri Rosenbaum
 * @see GenericSubsystem
 */
public class MotoredGenericSubsystem extends GenericSubsystem {

    private MotorControllerGroup motorControllerGroup;
    private Supplier<Double> minSpeed;
    private Supplier<Double> maxSpeed;
    protected RootNamespace rootNamespace;

    /**
     * Constructs a new instance of MotoredGenericSubsystem with the given {@code RootNamespace}'s name and
     * the given {@code MotorController}s.
     *
     * @param namespaceName    the subsystem's rootNamespace name
     * @param motorControllers the motor controllers in the subsystem
     */
    public MotoredGenericSubsystem(String namespaceName, MotorController... motorControllers) {
        this.rootNamespace = new RootNamespace(namespaceName);
        this.motorControllerGroup = new MotorControllerGroup(motorControllers);
    }

    /**
     * Constructs a new instance of MotoredGenericSubsystem with the given {@code RootNamespace}'s name, the given
     * maxSpeed supplier, the given minSpeed supplier and the {@code MotorController}s.
     *
     * @param minSpeed         the minimum speed
     * @param maxSpeed         the maximum speed
     * @param namespaceName    the subsystem's rootNamespace name
     * @param motorControllers the motor controllers in the subsystem
     */
    public MotoredGenericSubsystem(Supplier<Double> minSpeed, Supplier<Double> maxSpeed, String namespaceName,
                                   MotorController... motorControllers) {
        super(minSpeed, maxSpeed);
        this.rootNamespace = new RootNamespace(namespaceName);
        this.motorControllerGroup = new MotorControllerGroup(motorControllers);
    }

    /**
     * Constructs a new instance of MotoredGenericSubsystem with the given {@code RootNamespace}'s name, the given
     * maxSpeed, the given minSpeed and the {@code MotorController}s.
     *
     * @param minSpeed         the minimum speed
     * @param maxSpeed         the maximum speed
     * @param namespaceName    the subsystem's rootNamespace name
     * @param motorControllers the motor controllers in the subsystem
     */
    public MotoredGenericSubsystem(double minSpeed, double maxSpeed, String namespaceName,
                                   MotorController... motorControllers) {
        super(minSpeed, maxSpeed);
        this.rootNamespace = new RootNamespace(namespaceName);
        this.motorControllerGroup = new MotorControllerGroup(motorControllers);
    }

    @Override
    public void apply(double speed) {
        motorControllerGroup.set(speed);
    }

    @Override
    public boolean canMove(double speed) {
        return true;
    }

    @Override
    public void stop() {
        motorControllerGroup.stopMotor();
    }

    @Override
    public void periodic() {
        rootNamespace.update();
    }
}
