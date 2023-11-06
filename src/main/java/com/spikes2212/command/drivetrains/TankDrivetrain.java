package com.spikes2212.command.drivetrains;

import com.spikes2212.command.DashboardedSubsystem;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import jdk.javadoc.doclet.StandardDoclet;

import java.awt.*;

/**
 * This class represents a type of drivetrain that its left and right sides are controlled independently,
 * allowing it to move by giving each side a speed value separately.
 *
 * <p> It can move forwards/backwards by giving both its sides an equal speed or
 * turn by giving the sides different speeds. </p>
 *
 * @author Yuval Levy
 * @see DashboardedSubsystem
 */
public class TankDrivetrain extends DashboardedSubsystem {

    private static final String DEFAULT_NAMESPACE_NAME = "tank drivetrain";

    protected MotorController leftController;
    protected MotorController rightController;

    private final DifferentialDrive drive;

    public TankDrivetrain(String namespaceName, MotorController left, MotorController right) {
        super(namespaceName);
        this.leftController = left;
        this.rightController = right;
        rightController.setInverted(true);
        drive = new DifferentialDrive(leftController, rightController);
    }

    public TankDrivetrain(MotorController left, MotorController right) {
        this(getClassName(DEFAULT_NAMESPACE_NAME), left, right);
    }

    /**
     * Moves both sides of this drivetrain by the given speeds for each side.
     *
     * @param speedLeft  the speed to set to the left side. Positive values move this side
     *                   forward.
     * @param speedRight the speed to set to the right side. Positive values move this side
     *                   forward.
     */
    public void tankDrive(double speedLeft, double speedRight) {
        drive.tankDrive(speedLeft, speedRight);
    }

    /**
     * Moves both sides of this drivetrain by the given speeds for each side.
     *
     * @param speedLeft    the speed to set to the left side. Positive values move this side
     *                     forward.
     * @param speedRight   the speed to set to the right side. Positive values move this side
     *                     forward.
     * @param squareInputs whether to square the given inputs before putting them in the speed controllers
     */
    public void tankDrive(double speedLeft, double speedRight, boolean squareInputs) {
        drive.tankDrive(speedLeft, speedRight, squareInputs);
    }

    /**
     * Moves both sides of this drivetrain by the given voltages for each side
     *
     * @param leftVoltage the voltage to the left side. Positive values move this side
     *
     * @param rightVoltage the voltage to the right side. Positive values move this side
     */

    public void tankDriveVoltages(double leftVoltage, double rightVoltage) {
        tankDrive(leftVoltage / RobotController.getBatteryVoltage(),
                rightVoltage / RobotController.getBatteryVoltage());
    }

    /**
     * Moves both sides of this drivetrain by the given squareInputs and voltages for each side
     *
     * @param leftVoltage the voltage to set to the left side. Positive values move this side
     *
     * @param rightVoltage the voltage to set to the right side. Positive values move this side
     *
     * @param squareInputs whether to square the given inputs before putting them in the speed controllers
     */

    public void tankDriveVoltage(double leftVoltage, double rightVoltage, boolean squareInputs) {
        tankDrive(leftVoltage / RobotController.getBatteryVoltage(),
                rightVoltage / RobotController.getBatteryVoltage(), squareInputs);
    }

    /**
     * Moves the drivetrain with the given forward and angular speed.
     *
     * @param moveValue   the forward movement speed.
     * @param rotateValue the angular movement speed. Positive values go clockwise.
     */
    public void arcadeDrive(double moveValue, double rotateValue) {
        drive.arcadeDrive(moveValue, rotateValue);
    }

    /**
     * Moves the drivetrain by the given voltages for each side
     *
     * @param leftVoltage the voltage to set to the left side. Positive values move this side
     *
     * @param rightVoltage the voltage to set to the left side. Positive values move this side
     */

    public void arcadeDriveVoltages(double leftVoltage, double rightVoltage) {
        arcadeDrive(leftVoltage / RobotController.getBatteryVoltage(),
                rightVoltage / RobotController.getBatteryVoltage());
    }

    /**
     * Moves both sides of this drivetrain by the given speeds for each side.
     *
     * @param moveValue    the forward movement speed.
     * @param rotateValue  the angular movement speed. Positive values go clockwise.
     * @param squareInputs whether to square the given inputs before putting them in the speed controllers
     */
    public void arcadeDrive(double moveValue, double rotateValue, boolean squareInputs) {
        drive.arcadeDrive(moveValue, rotateValue, squareInputs);
    }

    /**
     * Moves both sides of this drivetrain by the given squareInputs and the given voltages for each side
     *
     * @param leftVoltage the voltage to set to the left side. Positive values move this side
     *
     * @param rightVoltage the voltage to set to the right side. Positive values move this value
     *
     * @param squareInputs whether to square the given inputs before putting them in the speed controllers
     */

    public void arcadeDriveVoltages(double leftVoltage, double rightVoltage, boolean squareInputs) {
        arcadeDrive(leftVoltage / RobotController.getBatteryVoltage(),
                rightVoltage / RobotController.getBatteryVoltage(), squareInputs);
    }

    /**
     * Moves the drivetrain while rotating it.
     *
     * @param speed     the forward movement speed.
     * @param curvature the rotational movement speed. Positive values go clockwise.
     */
    public void curvatureDrive(double speed, double curvature) {
        drive.curvatureDrive(speed, curvature, true);
    }

    /**
     * Moves this drivetrain while rotating it by the given voltages for each side
     *
     * @param leftVoltage the voltage to set to the left side. Positive values move this value
     *
     * @param rightVoltage the voltage to set to the right side. Positive values move this value
     */



    /**
     * Moves the left side of this drivetrain by a given speed.
     *
     * @param speedLeft the speed to set to the left side. Positive values move this side forward.
     */
    public void setLeft(double speedLeft) {
        leftController.set(speedLeft);
    }

    /**
     * Moves the right side of this drivetrain with a given speed.
     *
     * @param speedRight the speed to set to the right side. Positive values move this side forward.
     */
    public void setRight(double speedRight) {
        rightController.set(-speedRight);
    }

    public void setLeftVoltage(double leftVoltage) {
        leftController.set(leftVoltage / RobotController.getBatteryVoltage());
    }

    public void setRightVoltage(double rightVoltage) {
        rightController.set(rightVoltage / RobotController.getBatteryVoltage());
    }

    public void stop() {
        leftController.stopMotor();
        rightController.stopMotor();
    }

    /**
     * Adds any commands or data from this subsystem to the {@link NetworkTable}s.
     */
    @Override
    public void configureDashboard() {
    }
}
