// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Auto;
import frc.robot.testingdashboard.TestingDashboard;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DelayThenFeedBalls extends SequentialCommandGroup {
  private double m_delayTime;
  /** Creates a new DelayThenFeedBalls. */
  public DelayThenFeedBalls(double delayTime) {
    m_delayTime = delayTime;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new Wait(m_delayTime, true),
      new OpenGateAndFeedBalls()
    );
  }

  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    double delayTime = Constants.DEFAULT_SHOOTER_SPIN_UP_TIME;
    DelayThenFeedBalls cmd = new DelayThenFeedBalls(delayTime);
    TestingDashboard.getInstance().registerCommand(auto, "BallFeeding", cmd);
  }
}
