// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.Climber.RotateCaneToBar;
import frc.robot.commands.Shooter.SpinShooter;
import frc.robot.subsystems.Auto;
import frc.robot.testingdashboard.TestingDashboard;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootBallsLow extends ParallelCommandGroup {
  
  /** Creates a new ShootBallsLow. */
  public ShootBallsLow() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new RotateCaneToBar(-.2, true),
      new SpinShooter(Constants.SHOOTER_SPEED_LOW, true),
      new DelayThenFeedBalls(0.5)
    );
  }

  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    ShootBallsLow cmd = new ShootBallsLow();
    TestingDashboard.getInstance().registerCommand(auto, "Shooting", cmd);
  }

}
