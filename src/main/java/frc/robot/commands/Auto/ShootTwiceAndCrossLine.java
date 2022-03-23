// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Intake.LowerIntake;
import frc.robot.subsystems.Auto;
import frc.robot.testingdashboard.TestingDashboard;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootTwiceAndCrossLine extends SequentialCommandGroup {

  public static final double AUTO_DRIVE_SPEED = 0.3;
  public static final double AUTO_DRIVE_DIST = 0.0;

  /** Creates a new ShootTwiceAndCrossLine. */
  public ShootTwiceAndCrossLine() {
    // Add your commands in the addCommands() call, e.g.
    addCommands(
      new RunShooterTimed(),
      new LowerIntake(),
      // Turn
      new DriveAndSpinIntake(AUTO_DRIVE_DIST, AUTO_DRIVE_SPEED),
      // Turn
      new DriveDistance(AUTO_DRIVE_DIST, AUTO_DRIVE_SPEED, true),
      new RunShooterTimed()
    );
  }

  //Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    ShootTwiceAndCrossLine cmd = new ShootTwiceAndCrossLine();
    TestingDashboard.getInstance().registerCommand(auto, "AutoSequence", cmd);
  }
}
