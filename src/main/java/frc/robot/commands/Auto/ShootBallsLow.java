// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.Shooter.SpinShooter;
import frc.robot.subsystems.Conveyor;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootBallsLow extends SequentialCommandGroup {
  private Conveyor m_conveyor;
  private Shooter m_shooter;
  
  /** Creates a new ShootBallsHigh. */
  public ShootBallsLow() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new SpinShooter(Constants.SHOOTER_SPEED_LOW, true),
      new DelayThenFeedBalls(0.5)
    );

    m_conveyor = Conveyor.getInstance();
    addRequirements(m_conveyor);
    m_shooter = Shooter.getInstance();
    addRequirements(m_shooter);
  }
}
