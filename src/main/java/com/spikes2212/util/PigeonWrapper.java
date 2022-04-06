package com.spikes2212.util;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * A wrapper class for the pigeon IMU sensor.
 *
 * @author Tal Sitton
 */
public class PigeonWrapper implements Gyro {

    public double[] values = new double[3];
    protected final PigeonIMU pigeon;

    private double lastTime = 0;
    private double lastAngle;

    public PigeonWrapper(int canPort) {
        pigeon = new PigeonIMU(canPort);
    }

    public PigeonWrapper(TalonSRX talonSRX) {
        pigeon = new PigeonIMU(talonSRX);
    }

    /**
     * Resets the yaw.
     */
    public void reset() {
        setYaw(0);
    }

    @Override
    public double getAngle() {
        return getYaw();
    }

    /**
     * @return the yaw change rate in degrees per second
     */
    @Override
    public double getRate() {
        double currentTime = Timer.getFPGATimestamp() / 1000;
        double currentAngle = getYaw();
        double rate = (currentAngle - lastAngle) / (currentTime - lastTime);
        lastAngle = currentAngle;
        lastTime = currentTime;
        return rate;
    }

    /**
     * Calibrates the pigeon based on the yaw sent.
     *
     * @param yaw the yaw the pigeon shall be calibrated to
     */
    public void calibrate(double yaw) {
        pigeon.enterCalibrationMode(PigeonIMU.CalibrationMode.BootTareGyroAccel);
        setYaw(yaw);
    }

    /**
     * Calibrates the pigeon wrapper to yaw 0.
     */
    public void calibrate() {
        calibrate(0);
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
        pigeon.getYawPitchRoll(values);
        return values[0];
    }

    /**
     * @param yaw If you don't know what yaw is, see <a href=https://letmegooglethat.com/?q=what+is+yaw>here</a>.
     */
    public void setYaw(double yaw) {
        pigeon.setYaw(yaw);
    }

    /**
     * Don't.
     */
    @Override
    public void close() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Why did you feel the need to close the pigeon");
    }
}
