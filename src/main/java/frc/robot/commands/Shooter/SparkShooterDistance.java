// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.testingdashboard.TestingDashboard;

public class SparkShooterDistance extends CommandBase {

  private Shooter m_shooter;
  private double m_targetDistance;
  /** Creates a new SparkSpinShooter. */
  public SparkShooterDistance() {
    m_shooter = Shooter.getInstance();
    addRequirements(m_shooter);
    m_targetDistance = 0;
  }

  public static void registerWithDashboard()
  {
    Shooter shooter = Shooter.getInstance();
    SparkShooterDistance cmd = new SparkShooterDistance();
    TestingDashboard.getInstance().registerCommand(shooter, "Spark PID Test", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() 
  {
    m_targetDistance = TestingDashboard.getInstance().getNumber(m_shooter, "TargetDistance");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() 
  {
    m_targetDistance = TestingDashboard.getInstance().getNumber(m_shooter, "TargetDistance");
    m_shooter.setPIDDistance(m_targetDistance);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) 
  {
    m_shooter.setMotor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
