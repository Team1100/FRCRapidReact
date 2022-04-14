// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.commands.Drive.ToggleIdleMode;
import frc.robot.subsystems.Auto;
import frc.robot.testingdashboard.TestingDashboard;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveBackAndShootHigh extends SequentialCommandGroup {
  /** Creates a new DriveBackAndShootHigh. */
  public DriveBackAndShootHigh() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      //new ToggleIdleMode(IdleMode.kBrake),
      new ToggleIdleMode(IdleMode.kBrake),
      new DriveDistance(-30, 0.8, Constants.DEFAULT_BRAKE_TIME_DELAY, true),
      new ToggleIdleMode(IdleMode.kBrake),
      new ShootBallsHighTimed(),
      new ToggleIdleMode(IdleMode.kCoast)
    );
  }
  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    DriveBackAndShootHigh cmd = new DriveBackAndShootHigh();
    TestingDashboard.getInstance().registerCommand(auto, "Shooting", cmd);
  }
}
