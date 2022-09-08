// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.DriveSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  XboxController m_primaryController = new XboxController(0);

  // The robot's subsystems and commands are defined here...
  private DriveSubsystem m_driveSubsystem = new DriveSubsystem(DriveSubsystem.initializeHardware(Constants.WHEEL_BASE, Constants.TRACK_WIDTH),
                                                               Constants.DRIVE_KP,
                                                               Constants.DRIVE_KD,
                                                               Constants.DRIVE_TURNSCALAR,
                                                               Constants.DEADBAND,
                                                               Constants.DRIVE_LOOKAHEAD,
                                                               Constants.DRIVE_METERS_PER_TICK,
                                                               Constants.DRIVE_MAX_LINEAR_SPEED,
                                                               Constants.DRIVE_TRACTION_CONTROL_CURVE,
                                                               Constants.DRIVE_THROTTLE_INPUT_CURVE,
                                                               Constants.DRIVE_TURN_INPUT_CURVE,
                                                               Constants.DRIVE_CURRENT_LIMIT_CONFIGURATION);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    m_driveSubsystem.setDefaultCommand(new RunCommand(() ->
      m_driveSubsystem.teleopPID(
        m_primaryController.getLeftX(),
        m_primaryController.getLeftY(),
        m_primaryController.getRightX()),
      m_driveSubsystem));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
