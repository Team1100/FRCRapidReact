// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.Climber.RotateCaneToBar;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Drive.TurnAngle;
import frc.robot.commands.Intake.LowerIntake;
import frc.robot.subsystems.Auto;
import frc.robot.testingdashboard.TestingDashboard;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootAndCrossLine extends SequentialCommandGroup {

  public static final double AUTO_DRIVE_SPEED = 0.5; //0.45;
  public static final double AUTO_DISTANCE_TO_DRIVE = -60; //-30.0;
  public static final double AUTO_WAIT_TIME_BEFORE_DRIVING = 1; // seconds

  /** Creates a new ShootTwiceAndCrossLine. */
  public ShootAndCrossLine() {
    // Add your commands in the addCommands() call, e.g.

    /* 
       1 - Shoot Ball
       2 - Wait 5-10 secs
       3 - Drive back out of Zone
    */

    addCommands(
      new RotateCaneToBar(-.15, true),
      new ShootBallsHighTimed(),
      new Wait(AUTO_WAIT_TIME_BEFORE_DRIVING, true),
      new DriveDistance(AUTO_DISTANCE_TO_DRIVE, AUTO_DRIVE_SPEED, 0, true)
    );
  }

  //Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    ShootAndCrossLine cmd = new ShootAndCrossLine();
    TestingDashboard.getInstance().registerCommand(auto, "AutoSequenceLessAmbitious", cmd);
  }
}
