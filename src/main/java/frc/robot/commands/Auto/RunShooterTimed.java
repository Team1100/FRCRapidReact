// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import frc.robot.commands.Conveyor.SpinConveyorForwards;
import frc.robot.commands.Shooter.RunShooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class RunShooterTimed extends ParallelDeadlineGroup {
  /** Creates a new RunShooterTimed. */
  public RunShooterTimed() {
    // Add the deadline command in the super() call. Add other commands using
    // addCommands().
    super(new Wait(3, true),
      new RunShooter(0.35, true),
      new Wait(0.75, true),
      new SpinConveyorForwards()
    );
  }
}
