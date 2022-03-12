// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Climber;
import frc.robot.testingdashboard.TestingDashboard;

public class OpenClaws extends CommandBase {
  
  private Climber m_climber;
  private boolean m_finished;

  /** Creates a new OpenClaws. */
  
  public OpenClaws() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climber = Climber.getInstance();
    addRequirements(m_climber);
    m_finished = false;
  }

  //Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    Climber climber = Climber.getInstance();
    OpenClaws cmd = new OpenClaws();
    TestingDashboard.getInstance().registerCommand(climber, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_finished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_climber.openClaws();
    m_finished = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_finished = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_finished;
  }
}
