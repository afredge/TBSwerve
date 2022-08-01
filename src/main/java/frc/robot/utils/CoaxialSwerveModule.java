// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

/** Create a coaxial swerve module */
public class CoaxialSwerveModule implements AutoCloseable {
  public static class Hardware {
    private WPI_TalonFX driveMotor;
    private WPI_TalonFX rotateMotor;
    
    public Hardware(WPI_TalonFX driveMotor, WPI_TalonFX rotateMotor) {
      this.driveMotor = driveMotor;
      this.rotateMotor = rotateMotor;
    }
  }

  public enum LocationIndex {
    FRONT_LEFT(0),
    FRONT_RIGHT(1),
    REAR_LEFT(2),
    REAR_RIGHT(3);

    public final int value;
    private LocationIndex(int value) {
      this.value = value;
    }
  }

  private final double MOTOR_DEADBAND = 0.04;
  private final double MAX_VOLTAGE = 12.0;

  public final WPI_TalonFX driveMotor;
  public final WPI_TalonFX rotateMotor;
  public final Translation2d moduleLocation;
  public final LocationIndex locationIndex;

  private static TractionControlController m_tractionControlController;

  public CoaxialSwerveModule(Hardware swerveHardware, Translation2d moduleLocation) {
    this.driveMotor = swerveHardware.driveMotor;
    this.rotateMotor = swerveHardware.rotateMotor;

    this.moduleLocation = moduleLocation;

    driveMotor.configFactoryDefault();
    rotateMotor.configFactoryDefault();

    driveMotor.setNeutralMode(NeutralMode.Brake);
    rotateMotor.setNeutralMode(NeutralMode.Brake);

    driveMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    driveMotor.configNeutralDeadband(MOTOR_DEADBAND);
    rotateMotor.configNeutralDeadband(MOTOR_DEADBAND);

    driveMotor.configVoltageCompSaturation(MAX_VOLTAGE);
    driveMotor.enableVoltageCompensation(true);

    // Figure out module location index based on given coordinates
    if (moduleLocation.getX() >= 0.0) {
      if (moduleLocation.getY() >= 0.0) locationIndex = LocationIndex.FRONT_LEFT;
      else locationIndex = LocationIndex.FRONT_RIGHT;
    } else {
      if (moduleLocation.getY() >= 0.0) locationIndex = LocationIndex.REAR_LEFT;
      else locationIndex = LocationIndex.REAR_RIGHT;
    }
  }

  public static Hardware initializeHardware(int driveMotorPort, int rotateMotorPort) {
    Hardware swerveModuleHardware = new Hardware(new WPI_TalonFX(driveMotorPort),
                                                 new WPI_TalonFX(rotateMotorPort));
    return swerveModuleHardware;
  }

  public static void setTractionControlController(TractionControlController tractionControlController) {
    m_tractionControlController = tractionControlController;
  }

  /**
   * Set swerve module direction and speed
   * @param state Desired swerve module state
   */
  public void set(SwerveModuleState state) {
    // Optimize swerve module rotation state
    // CANCoder returns an angle in degrees
    state = SwerveModuleState.optimize(state, new Rotation2d(Math.toRadians(rotateMotor.get())));

    // Set rotate motor position
    rotateMotor.set(ControlMode.MotionMagic, state.angle.getDegrees());
    
    // Set drive motor speed
    driveMotor.set(ControlMode.PercentOutput, m_tractionControlController.velocityLookup(state.speedMetersPerSecond));
  }

  /**
   * Reset swerve module to 0 degrees
   */
  public void reset() {
    rotateMotor.set(ControlMode.MotionMagic, 0.0);
    driveMotor.stopMotor();
  }

  /**
   * Stop swerve module
   */
  public void stop() {
    rotateMotor.stopMotor();
    driveMotor.stopMotor();
  }

  @Override
  public void close() {
    driveMotor.close();
    rotateMotor.close();
  }
}