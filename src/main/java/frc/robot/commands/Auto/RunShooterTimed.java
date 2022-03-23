// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.commands.Conveyor.SpinConveyorForwards;
import frc.robot.commands.Shooter.SpinShooter;
import frc.robot.subsystems.Auto;
import frc.robot.testingdashboard.TestingDashboard;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class RunShooterTimed extends ParallelDeadlineGroup {
  public static final double AUTO_SECONDS_TO_RUN_COMMANDS = 3; // seconds
  public static final double AUTO_SHOOTER_SPIN_SPEED = 0.35; // percentage

  /** Creates a new RunShooterTimed. */
  public RunShooterTimed() {
    // Add the deadline command in the super() call. Add other commands using
    // addCommands().
    super(new Wait(AUTO_SECONDS_TO_RUN_COMMANDS, true),
      new SpinShooter(AUTO_SHOOTER_SPIN_SPEED, true)
    );
  }

  //Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    RunShooterTimed cmd = new RunShooterTimed();
    TestingDashboard.getInstance().registerCommand(auto, "AutoPieces", cmd);
  }
}
