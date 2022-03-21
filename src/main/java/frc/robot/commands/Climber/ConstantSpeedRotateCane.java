// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ClimberCaneRotation;
import frc.robot.testingdashboard.TestingDashboard;

public class ConstantSpeedRotateCane extends CommandBase {
  ClimberCaneRotation m_climberCaneRotation;
  Climber m_climber;
  private boolean m_parameterized;
  private double m_caneSpeed;

  /** Creates a new ConstantSpeedRotateCane. */
  public ConstantSpeedRotateCane(double speed, boolean parameterized) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climberCaneRotation = ClimberCaneRotation.getInstance();
    addRequirements(m_climberCaneRotation);
    m_climber = Climber.getInstance();
    m_caneSpeed = speed;
    m_parameterized = parameterized;
  }

  public static void registerWithTestingDashboard() {
    Climber climber = Climber.getInstance();
    ConstantSpeedRotateCane cmd = new ConstantSpeedRotateCane(Climber.INITIAL_CANE_ROTATION_SPEED, false);
    TestingDashboard.getInstance().registerCommand(climber, "CaneRotation", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_parameterized) {
      m_caneSpeed = TestingDashboard.getInstance().getNumber(m_climber, "RotationSpeed");
    }
    m_climber.rotateBothCanes(m_caneSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climber.rotateBothCanes(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
