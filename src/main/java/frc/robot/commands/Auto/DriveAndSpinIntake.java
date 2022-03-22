// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Intake.SpinIntake;
import frc.robot.testingdashboard.TestingDashboard;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveAndSpinIntake extends ParallelDeadlineGroup {
  /** Creates a new DriveAndSpinIntake. */
  public DriveAndSpinIntake(double distance, double speed) {
    // Add the deadline command in the super() call. Add other commands using
    // addCommands().
    super(new Wait(4, true),
      new DriveDistance(distance, speed, true),
      new SpinIntake(0.35)
    );
  }

  //Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    DriveAndSpinIntake cmd = new DriveAndSpinIntake(0.35, 0.3);
  }
}
