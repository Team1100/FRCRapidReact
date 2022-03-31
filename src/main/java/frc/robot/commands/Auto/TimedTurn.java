// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.commands.Drive.TurnAngle;
import frc.robot.subsystems.Auto;
import frc.robot.subsystems.Drive;
import frc.robot.testingdashboard.TestingDashboard;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TimedTurn extends ParallelDeadlineGroup {
  /** Creates a new TimedTurn. */
  public TimedTurn(double time, double angle, double speed, boolean parameterized) {
    // Add the deadline command in the super() call. Add other commands using
    // addCommands().
    super(new Wait(time, true),
          new TurnAngle(angle, speed, parameterized)
    );
  }

  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    TimedTurn cmd = new TimedTurn(2.5, 180, Drive.INITIAL_SPEED, false);
    TestingDashboard.getInstance().registerCommand(auto, "Basic", cmd);
  }
}
