/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.Intake.UserSpinIntake;
import frc.robot.subsystems.Shooter;
import frc.robot.testingdashboard.TestingDashboard;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShootBall extends ParallelCommandGroup {
  /**
   * Creates a new ShootBalls.
   */
  public ShootBall(double[] doubleArray) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());super();
    super(new PIDTopShooter(doubleArray[0]), 
          new PIDBottomShooter(doubleArray[1]));
  }

  public static void registerWithTestingDashboard() {
    Shooter shooter = Shooter.getInstance();
    double top_setpoint = TestingDashboard.getInstance().getNumber(shooter, "topSetpoint");
    double bot_setpoint = TestingDashboard.getInstance().getNumber(shooter, "bottomSetpoint");
    double[] setpoints = {top_setpoint, bot_setpoint};
    ShootBall cmd = new ShootBall(setpoints);
    TestingDashboard.getInstance().registerCommand(shooter, "Basic", cmd);
  }

}
