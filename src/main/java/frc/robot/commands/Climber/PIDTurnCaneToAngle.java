// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NTSendable;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.Climber;
import frc.robot.testingdashboard.TestingDashboard;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class PIDTurnCaneToAngle extends PIDCommand {
  /** Creates a new PIDTurnCaneToAngle. */
  public PIDTurnCaneToAngle(double setpoint) {
    super(
        // The controller that the command will use
        new PIDController(0.1, 0.1, 0),
        // This should return the measurement
        () -> Climber.getInstance().getRotationAngle(),
        // This should return the setpoint (can also be a constant)
        () -> setpoint,
        // This uses the output
        output -> {
          // Use the output here
          Climber.getInstance().rotateBothCanes(output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    addRequirements(Climber.getInstance());
    getController().setTolerance(5);
    getController().disableContinuousInput();
  }

  /*
   * Alternate constructor that allows the setpoint to be dynamically
   * retrieved from the testing dashboard.
   */
  public PIDTurnCaneToAngle() {
    super(
        // The controller that the command will use
        new PIDController(0.1, 0.1, 0),
        // This should return the measurement
        () -> Climber.getInstance().getRotationAngle(),
        // This should return the setpoint (can also be a constant)
        () -> TestingDashboard.getInstance().getNumber(Climber.getInstance(), "CaneSetpoint"),
        // This uses the output
        output -> {
          // Use the output here
          Climber.getInstance().rotateBothCanes(output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    addRequirements(Climber.getInstance());
    getController().setTolerance(5);
    getController().disableContinuousInput();
  }

  public static void registerWithTestingDashboard() {
    Climber climber = Climber.getInstance();
    PIDTurnCaneToAngle cmd = new PIDTurnCaneToAngle();
    TestingDashboard.getInstance().registerCommand(climber, "Basic", cmd);
    TestingDashboard.getInstance().registerSendable(climber, "PIDRotation", "CanePIDController", cmd.getController());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
