// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.subsystems.Auto;
import frc.robot.testingdashboard.TestingDashboard;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootBallsHighTimed extends ParallelDeadlineGroup {
  private static final double AUTO_SECONDS_TO_SHOOT_BALL = 3; // seconds
  /** Creates a new ShootBallsHighTimed. */
  public ShootBallsHighTimed() {
    // Add the deadline command in the super() call. Add other commands using
    // addCommands().
    // addCommands(new FooCommand(), new BarCommand());
    super(new Wait(AUTO_SECONDS_TO_SHOOT_BALL, true),
          new ShootBallsHigh()
         );
  }
   //Register with TestingDashboard
   public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    ShootBallsHighTimed cmd = new ShootBallsHighTimed();
    TestingDashboard.getInstance().registerCommand(auto, "Shooting", cmd);
  }
}
