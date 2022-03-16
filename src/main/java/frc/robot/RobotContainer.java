// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.Climber.CloseClaws;
import frc.robot.commands.Climber.OpenClaws;
import frc.robot.commands.Climber.DriveToBar;
import frc.robot.commands.Climber.ElevatorCane;
import frc.robot.commands.Climber.TankCane;
import frc.robot.commands.Climber.TankRotateCane;
import frc.robot.commands.Climber.TestStateMachineSequence;
import frc.robot.commands.Climber.UserOperateCane;
import frc.robot.commands.Drive.ArcadeDrive;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Drive.KeyboardDrive;
import frc.robot.commands.Drive.TankDrive;
import frc.robot.commands.Intake.LowerIntake;
import frc.robot.commands.Intake.RaiseIntake;
import frc.robot.commands.Intake.UserSpinIntake;
import frc.robot.commands.Shooter.PIDBottomShooter;
import frc.robot.commands.Shooter.PIDTopShooter;
import frc.robot.commands.Shooter.ShootBall;
import frc.robot.subsystems.Auto;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.testingdashboard.TestingDashboard;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here...
  
  private final Drive m_drive;
  private final Climber m_climber;

  

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Initialize subsystems
    Auto.getInstance();
    m_drive = Drive.getInstance();
    m_climber = Climber.getInstance();

    m_drive.setDefaultCommand(new ArcadeDrive());
    m_climber.setDefaultCommand(new UserOperateCane());

    // Configure the button bindings
    configureButtonBindings();

    // Register commands with TestingDashboard commands
    DriveToBar.registerWithTestingDashboard();
    TankCane.registerWithTestingDashboard();
    OpenClaws.registerWithTestingDashboard();
    CloseClaws.registerWithTestingDashboard();
    TankRotateCane.registerWithTestingDashboard();
    UserOperateCane.registerWithTestingDashboard();
    TestStateMachineSequence.registerWithTestingDashboard();
    TankDrive.registerWithTestingDashboard();
    ArcadeDrive.registerWithTestingDashboard();
    ElevatorCane.registerWithTestingDashboard();
    DriveDistance.registerWithTestingDashboard();
    KeyboardDrive.registerWithTestingDashboard();
    UserSpinIntake.registerWithTestingDashboard();
    RaiseIntake.registerWithTestingDashboard();
    LowerIntake.registerWithTestingDashboard();
    PIDTopShooter.registerWithTestingDashboard();
    PIDBottomShooter.registerWithTestingDashboard();
    ShootBall.registerWithTestingDashboard();
    
    // Create Testing Dashboard
    TestingDashboard.getInstance().createTestingDashboard();
    
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    OI.getInstance();
  }

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
