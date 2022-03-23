// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.Conveyor.SpinConveyorBackwards;
import frc.robot.commands.Intake.SpinIntake;
import frc.robot.commands.Shooter.SpinShooter;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ExpelBalls extends ParallelCommandGroup {
  public static final double SHOOTER_EXPEL_SPEED = -0.3;
  public static final double INTAKE_EXPEL_SPEED  = -0.2;
 
  /** Creates a new ExpelBalls. */
  public ExpelBalls() {
    
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    super(
      new SpinShooter(SHOOTER_EXPEL_SPEED, true),
      new SpinConveyorBackwards(),
      new SpinIntake(INTAKE_EXPEL_SPEED)
    );
  }
}
