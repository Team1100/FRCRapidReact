// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Drive.TurnAngle;
import frc.robot.commands.Intake.LowerIntake;
import frc.robot.subsystems.Auto;
import frc.robot.testingdashboard.TestingDashboard;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootTwiceAndCrossLine extends SequentialCommandGroup {

  public static final double AUTO_DRIVE_SPEED = 0.3;
  public static final double AUTO_DRIVE_DIST = 95.0;

  /** Creates a new ShootTwiceAndCrossLine. */
  public ShootTwiceAndCrossLine() {
    // Add your commands in the addCommands() call, e.g.

    /* 
    1 - Shoot Ball
    2 - Turn 180 degrees
    3 - Lower Intake
    4 - Drive Forwards
    5 - Pick Up Ball
    6 - Turn 180 Degrees
    7 - Drive Forwards
    8 - Shoot Ball
    9 - Drive Backwards out of the Zone
    */

    addCommands(
      new ShootBallsHighTimed(),
      new TurnAngle(180, 0.35, true),
      new LowerIntake(),
      new DriveAndSpinIntake(AUTO_DRIVE_DIST, AUTO_DRIVE_SPEED),
      new TurnAngle(180, 0.35, true),
      new DriveDistance(AUTO_DRIVE_DIST, AUTO_DRIVE_SPEED, true),
      new ShootBallsHighTimed(),
      new DriveDistance(-AUTO_DRIVE_DIST, (AUTO_DRIVE_SPEED * 1.5), true)
    );
  }

  //Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    ShootTwiceAndCrossLine cmd = new ShootTwiceAndCrossLine();
    TestingDashboard.getInstance().registerCommand(auto, "AutoSequence", cmd);
  }
}
