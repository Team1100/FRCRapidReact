// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Climber.RotateCaneToBar;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Drive.MotorTurnAngle;
import frc.robot.commands.Drive.ToggleIdleMode;
import frc.robot.commands.Drive.TurnAngle;
import frc.robot.commands.Intake.LowerIntake;
import frc.robot.commands.Intake.RaiseIntake;
import frc.robot.subsystems.Auto;
import frc.robot.testingdashboard.TestingDashboard;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SimpleShootTwice extends SequentialCommandGroup {

  public static final double AUTO_DRIVE_COLLECT_BALL_SPEED = 0.55;
  public static final double AUTO_DRIVE_CROSS_LINE_SPEED = AUTO_DRIVE_COLLECT_BALL_SPEED * 1.5;
  public static final double AUTO_DRIVE_DIST = 95.0;
  public static final double BACKUP_DIST = 12;
  public static final double ANGLE_TO_TURN = 120;
  public static final double TURN_SPEED = 0.4;

  /** Creates a new ShootTwiceAndCrossLine. */
  public SimpleShootTwice() {
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
      new RotateCaneToBar(-0.15, true),
      new ShootBallsHighTimed(),
      new DriveDistance(-46, AUTO_DRIVE_COLLECT_BALL_SPEED, true),
      new ToggleIdleMode(IdleMode.kBrake),
      new RaiseIntake(),
      new MotorTurnAngle(-200, TURN_SPEED, 0.25, true)
      /**new DriveAndSpinIntake(50, AUTO_DRIVE_COLLECT_BALL_SPEED),
      new MotorTurnAngle(170, TURN_SPEED, 0.25, true),
      new LowerIntake(),
      new DriveDistance(95, AUTO_DRIVE_COLLECT_BALL_SPEED, true),
      new ShootBallsHighTimed()*/
    );
  }

  //Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    SimpleShootTwice cmd = new SimpleShootTwice();
    TestingDashboard.getInstance().registerCommand(auto, "AutoSequence", cmd);
  }
}
