package com.spikes2212.util;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * A wrapper class for the Pigeon IMU sensor.
 *
 * @author Tal Sitton
 */
public class PigeonWrapper implements Gyro {

    public enum RotationAxis {
        X, Y, Z;
    }

    public static final RotationAxis DEFAULT_ROTATION_AXIS = RotationAxis.Z;

    protected final double[] values = new double[3];
    protected final PigeonIMU pigeon;

    private final RotationAxis axis;

    public PigeonWrapper(int canPort, RotationAxis defaultRotationAxis) {
        this.pigeon = new PigeonIMU(canPort);
        this.axis = defaultRotationAxis;
    }

    public PigeonWrapper(TalonSRX talonSRX, RotationAxis defaultRotationAxis) {
        this.pigeon = new PigeonIMU(talonSRX);
        this.axis = defaultRotationAxis;
    }

    public PigeonWrapper(int canPort) {
        this(canPort, DEFAULT_ROTATION_AXIS);
    }

    public PigeonWrapper(TalonSRX talonSRX) {
        this(talonSRX, DEFAULT_ROTATION_AXIS);
    }

    /**
     * Calibrates the Pigeon based on the yaw sent.
     *
     * @param yaw the yaw the Pigeon shall be calibrated to
     */
    public void calibrate(double yaw) {
        pigeon.enterCalibrationMode(PigeonIMU.CalibrationMode.BootTareGyroAccel);
        setYaw(yaw);
    }

    /**
     * Calibrates the Pigeon wrapper to yaw 0.
     */
    public void calibrate() {
        calibrate(0);
    }

    @Override
    public void reset() {
        calibrate();
    }

    /**
     * Don't.
     */
    @Override
    public void close() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Why did you feel the need to close the pigeon");
    }

    /**
     * @return the heading of the robot in degrees
     */
    @Override
    public double getAngle() {
        if (axis == RotationAxis.X)
            return getX();
        if (axis == RotationAxis.Y)
            return getY();
        return getZ();
    }

    /**
     * @return the angle change rate in degrees per second
     */
    @Override
    public double getRate() {
        pigeon.getRawGyro(values);
        if (axis == RotationAxis.X)
            return values[0];
        if (axis == RotationAxis.Y)
            return values[1];
        return values[2];
    }

    /**
     * @return the X axis from the gyro
     */
    public double getX() {
        pigeon.getAccumGyro(values);
        return values[0];
    }

    /**
     * @return the Y axis from the gyro
     */
    public double getY() {
        pigeon.getAccumGyro(values);
        return values[1];
    }

    /**
     * @return the Z axis from the gyro
     */
    public double getZ() {
        pigeon.getAccumGyro(values);
        return values[2];
    }

    /**
     * <p>If you don't know what yaw is, see <a href=https://letmegooglethat.com/?q=what+is+yaw>here</a>.</p>
     *
     * @return the yaw
     */
    public double getYaw() {
        return pigeon.getYaw();
    }

    /**
     * @param yaw if you don't know what yaw is, see <a href=https://letmegooglethat.com/?q=what+is+yaw>here</a>
     */
    public void setYaw(double yaw) {
        pigeon.setYaw(yaw);
    }

    public double getPitch() {
        return pigeon.getPitch();
    }

    public double getRoll() {
        return pigeon.getRoll();
    }
}
