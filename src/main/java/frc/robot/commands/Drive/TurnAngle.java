// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drive;
import frc.robot.testingdashboard.TestingDashboard;

public class TurnAngle extends CommandBase {
  static Drive m_drive;
  double m_angle; // angle is in degrees
  double m_finalAngle;
  boolean m_parameterized;
  double m_speed;
  double m_initialAngle;
  double m_direction; // positive is clockwise, negative is counter-clockwise

  final double CLOCKWISE = 1;
  final double COUNTER_CLOCKWISE = -1;
  private final boolean getTDAngle = false;

  /** Creates a new TurnAngle. */
  public TurnAngle(double angle, double speed, boolean parameterized) {
    m_drive = Drive.getInstance();
    m_parameterized = parameterized;
    m_angle = angle;
    m_speed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drive);
  }

  public static void registerWithTestingDashboard() {
    Drive drive = Drive.getInstance();
    TurnAngle cmd = new TurnAngle(180, 0.6, false);
    TestingDashboard.getInstance().registerCommand(drive, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_initialAngle = m_drive.getYaw();
    if (!m_parameterized) {
      TestingDashboard.getInstance().updateNumber(m_drive, "InitialAngle", m_initialAngle);
      if (getTDAngle == true) {
        m_angle = TestingDashboard.getInstance().getNumber(m_drive, "TurnAngleInDegrees");
      }
    }
    m_direction = m_angle / Math.abs(m_angle);
    m_angle = (Math.abs(m_angle) % 360) * m_direction;
    updateFinalAngle();
    m_drive = Drive.getInstance();
    m_drive.setIdleMode(IdleMode.kBrake);
  }

  public void updateFinalAngle() {
    m_finalAngle = m_initialAngle + m_angle;
    if (m_finalAngle > 180) {
    double delta = m_angle - (180-m_initialAngle);
    m_finalAngle = -180 + delta;
    } else if (m_finalAngle < -180) {
    double delta = m_angle - (-180-m_initialAngle);
    m_finalAngle = 180 + delta;
    }
  }

  public boolean overShoot() {
    if (m_direction == CLOCKWISE) {
      if (m_initialAngle > 0 && m_finalAngle < 0) {
        return true;
      }       
    } else if (m_direction == COUNTER_CLOCKWISE) {
      if (m_initialAngle < 0 && m_finalAngle > 0) {
        return true;
      }
  }
    return false;
  }
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_parameterized) {
      m_speed = TestingDashboard.getInstance().getNumber(m_drive, "SpeedWhenTurning");
    }
    m_drive.tankDrive(m_speed * m_direction, m_speed * m_direction * -1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive = Drive.getInstance();
    m_drive.setIdleMode(IdleMode.kCoast);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double yaw = m_drive.getYaw();
    if (m_direction == CLOCKWISE) {
      return (yaw >= m_finalAngle && ((overShoot() && yaw < 0) || !overShoot()));
    } else if (m_direction == COUNTER_CLOCKWISE) {
      return (yaw <= m_finalAngle && ((overShoot() && yaw > 0) || !overShoot()));
    }
    return false;
  }
}
