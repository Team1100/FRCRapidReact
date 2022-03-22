// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.subsystems.Intake;
import frc.robot.testingdashboard.TestingDashboard;

public class SpinIntake extends CommandBase {

  private Intake m_intake;
  private boolean m_parameterized;
  private OI m_oi;
  private boolean m_finished;
  private double m_speed = 0.35;

  /** Creates a new SpinIntake. */
  public SpinIntake(double s) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intake = Intake.getInstance();
    addRequirements(m_intake);
    m_finished = false;
    m_speed = s;
  }

  //Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    Intake intake = Intake.getInstance();
    SpinIntake cmd = new SpinIntake(0.35);
    TestingDashboard.getInstance().registerCommand(intake, "Basic", cmd);
    TestingDashboard.getInstance().registerNumber(intake, "", "speed", 0.35);
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
    m_intake.spinIntakeRoller(m_speed);
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
