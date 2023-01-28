// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ClimberCaneRotation;
import frc.robot.testingdashboard.TestingDashboard;
import frc.robot.input.XboxController;

public class TankRotateCane extends CommandBase {
  /** Creates a new TankRotateCane. */
  Climber m_climber;
  ClimberCaneRotation m_climberCaneRotation;
  OI m_oi;
  private double m_caneSpeed;

  public TankRotateCane() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climberCaneRotation = ClimberCaneRotation.getInstance();
    addRequirements(m_climberCaneRotation);
    m_caneSpeed = Climber.INITIAL_CANE_ROTATION_SPEED;
  }

  public static void registerWithTestingDashboard() {
    Climber climber = Climber.getInstance();
    TankRotateCane cmd = new TankRotateCane();
    TestingDashboard.getInstance().registerCommand(climber, "CaneRotation", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_oi = OI.getInstance();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_caneSpeed = TestingDashboard.getInstance().getNumber(m_climber, "RotationSpeed");
    double rotationSpeed = 0;
    if (Constants.XBOX_CONTROLLER_OPERATOR_ENABLE) {
      XboxController xbox = m_oi.getOperatorXboxController();
      if (xbox.getDPad().getRight().getAsBoolean()) {
        rotationSpeed = m_caneSpeed;
      }
      else if (xbox.getDPad().getLeft().getAsBoolean()) {
        rotationSpeed = -m_caneSpeed;
      }
    }
    m_climber.rotateBothCanes(rotationSpeed);
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
