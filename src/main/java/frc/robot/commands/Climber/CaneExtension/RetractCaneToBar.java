// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber.CaneExtension;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ClimberCaneExtension;
import frc.robot.testingdashboard.TestingDashboard;

public class RetractCaneToBar extends CommandBase {
  private Climber m_climber;
  private ClimberCaneExtension m_climberCaneExtension;
  private double m_caneSpeed; 
  private boolean m_parameterized;
  
  /** Creates a new RetractCaneToBar. */
  public RetractCaneToBar(double caneSpeed, boolean parameterized) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climberCaneExtension = ClimberCaneExtension.getInstance();
    addRequirements(m_climberCaneExtension);
    m_caneSpeed = -Math.abs(caneSpeed);
    m_parameterized = parameterized;
  }

  public static void registerWithTestingDashboard() {
    Climber climber = Climber.getInstance();
    RetractCaneToBar cmd = new RetractCaneToBar(-Climber.INITIAL_CANE_EXTENTION_SPEED, false);
    TestingDashboard.getInstance().registerCommand(climber, "CaneExtension", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_parameterized) {
      m_caneSpeed = -Math.abs(TestingDashboard.getInstance().getNumber(m_climber, "ExtensionSpeed"));
    }
    m_climber.extendCane(m_caneSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climber.extendCane(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double averageCurrent = (m_climber.getTotalAverageLeftCaneMotorCurrent() + m_climber.getTotalAverageRightCaneMotorCurrent())/2;
    boolean finished = false;

    if (averageCurrent > Constants.CANE_MOTOR_CURRENT_THRESHOLD) {
      finished = true;
    }
    return finished;
  }
}
