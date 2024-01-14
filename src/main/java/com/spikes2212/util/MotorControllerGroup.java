package com.spikes2212.util;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

import java.util.List;

public class MotorControllerGroup implements MotorController, Sendable, AutoCloseable {

    private final List<MotorController> motorControllers;
    private boolean inverted;

    public MotorControllerGroup(MotorController... motorControllers) {
        this.motorControllers = List.of(motorControllers);
    }

    @Override
    public void close() {
        SendableRegistry.remove(this);
    }

    @Override
    public void set(double speed) {
        for (MotorController motorController : motorControllers) {
            motorController.set(inverted ? -speed : speed);
        }
    }

    @Override
    public void setVoltage(double outputVolts) {
        for (MotorController motorController : motorControllers) {
            motorController.setVoltage(inverted ? -outputVolts : outputVolts);
        }
    }

    @Override
    public double get() {
        return motorControllers.get(0).get();
    }

    @Override
    public boolean getInverted() {
        return inverted;
    }

    @Override
    public void setInverted(boolean isInverted) {
        inverted = isInverted;
    }

    @Override
    public void disable() {
        for (MotorController motorController : motorControllers) {
            motorController.disable();
        }
    }

    @Override
    public void stopMotor() {
        for (MotorController motorController : motorControllers) {
            motorController.stopMotor();
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Motor Controller");
        builder.setActuator(true);
        builder.setSafeState(this::stopMotor);
        builder.addDoubleProperty("Value", this::get, this::set);
    }
}
