// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.subsystems.Climber;
import frc.robot.testingdashboard.TestingDashboard;

public class CloseClaw extends CommandBase {

  private Climber m_climber;
  private boolean m_parameterized;
  private OI m_oi;

  /** Creates a new OpenClaw. */
  public CloseClaw() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climber = Climber.getInstance();
    addRequirements(m_climber);
  }

  //Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    Climber climber = Climber.getInstance();
    CloseClaw cmd = new CloseClaw();
    TestingDashboard.getInstance().registerCommand(climber, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_oi = OI.getInstance();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Empty currently
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Not sure if needed:  m_climber.openClaw();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
