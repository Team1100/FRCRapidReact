// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.testingdashboard.TestingDashboard;
import frc.robot.subsystems.Shooter;

public class PIDBottomShooter extends PIDCommand {
  /** Creates a new PIDBottomshooter. */
  public PIDBottomShooter(double setPoint) {
    super(
        // The controller that the command will use
        new PIDController(Shooter.getInstance().getkP(), Shooter.getInstance().getkI(), Shooter.getInstance().getkD()),
        // This should return the measurement
        () -> Shooter.getInstance().getRPM(Shooter.getInstance().getBottomEncoder()),
        // This should return the setpoint (can also be a constant)
        () -> setPoint,
        // This uses the output
        output -> {
          // Use the output here
          Shooter.getInstance().setBottom(output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    getController().setTolerance(10);
    getController().disableContinuousInput();
  }

  public static void registerWithTestingDashboard() {
    Shooter shooter = Shooter.getInstance();
    double setpoint = TestingDashboard.getInstance().getNumber(shooter, "bottomSetpoint");
    PIDBottomShooter cmd = new PIDBottomShooter(setpoint);
    TestingDashboard.getInstance().registerCommand(shooter, "Basic", cmd);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
