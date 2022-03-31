// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.subsystems.Drive;
import frc.robot.testingdashboard.TestingDashboard;

public class ToggleIdleMode extends CommandBase {

  private Drive m_drive;
  private boolean m_parameterized;
  private OI m_oi;
  private boolean m_finished;
  private IdleMode m_idleMode;

  /** Creates a new ToggleIdleMode. */
  public ToggleIdleMode(IdleMode idleMode) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = Drive.getInstance();
    addRequirements(m_drive);
    m_finished = false;
    m_idleMode = idleMode;
  }

  //Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    Drive drive = Drive.getInstance();
    ToggleIdleMode cmd = new ToggleIdleMode(IdleMode.kCoast);
    TestingDashboard.getInstance().registerCommand(drive, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_oi = OI.getInstance();
    m_finished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.setIdleMode(m_idleMode);
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
