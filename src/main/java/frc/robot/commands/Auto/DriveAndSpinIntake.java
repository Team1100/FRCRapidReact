// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Intake.SpinIntake;
import frc.robot.subsystems.Auto;
import frc.robot.testingdashboard.TestingDashboard;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveAndSpinIntake extends ParallelDeadlineGroup {
  public static final double AUTO_DEFAULT_DISTANCE_TO_DRIVE = 10; // inches
  public static final double AUTO_DEFAULT_SPEED_TO_DRIVE = 0.3; // percentage
  public static final double AUTO_INTAKE_SPIN_SPEED = 0.35; // percentage
  public static final double AUTO_SECONDS_TO_RUN_COMMANDS = 4; // seconds

  /** Creates a new DriveAndSpinIntake. */
  public DriveAndSpinIntake(double distance, double speed) {
    // Add the deadline command in the super() call. Add other commands using
    // addCommands().
    super(new Wait(AUTO_SECONDS_TO_RUN_COMMANDS, true),
      new DriveDistance(distance, speed, true),
      new SpinIntake(AUTO_INTAKE_SPIN_SPEED)
    );
  }

  //Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    DriveAndSpinIntake cmd = new DriveAndSpinIntake(AUTO_DEFAULT_DISTANCE_TO_DRIVE, AUTO_DEFAULT_SPEED_TO_DRIVE);
    TestingDashboard.getInstance().registerCommand(auto, "AutoPieces", cmd);
  }
}
