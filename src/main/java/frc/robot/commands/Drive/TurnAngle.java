// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.Timer;
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
  double m_brakeDelay;
  boolean m_finished;

  private Timer m_timer;
  
  final static double CLOCKWISE = 1;
  final static double COUNTER_CLOCKWISE = -1;
  final static double BRAKE_DELAY = 2;

  /** Creates a new TurnAngle. */
  public TurnAngle(double angle, double speed, double brakeDelay, boolean parameterized) {
    m_drive = Drive.getInstance();
    m_timer = new Timer();
    m_parameterized = parameterized;
    m_angle = angle;
    m_speed = speed;
    m_finished = false;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drive);
  }

  public static void registerWithTestingDashboard() {
    Drive drive = Drive.getInstance();
    TurnAngle cmd = new TurnAngle(180, 0.6, BRAKE_DELAY, false);
    TestingDashboard.getInstance().registerCommand(drive, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.zeroNavx();
    m_initialAngle = m_drive.getAngle();
    if (!m_parameterized) {
      TestingDashboard.getInstance().updateNumber(m_drive, "InitialAngle", m_initialAngle);
      m_angle = TestingDashboard.getInstance().getNumber(m_drive, "TurnAngleInDegrees");
      m_brakeDelay = BRAKE_DELAY;
    }
    m_direction = m_angle/Math.abs(m_angle);
    m_angle = (Math.abs(m_angle) % 360) * m_direction;
    m_finalAngle = m_initialAngle + m_angle;
    m_drive.setIdleMode(IdleMode.kBrake);
    m_timer.reset();
    m_finished = false;
  }




  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_parameterized) {
      m_speed = TestingDashboard.getInstance().getNumber(m_drive, "SpeedWhenTurning");
    }
    if (!m_finished) {
      m_drive.tankDrive(m_speed * m_direction, m_speed * m_direction * -1);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive = Drive.getInstance();
    m_drive.setIdleMode(IdleMode.kCoast);
    m_timer.stop();
    m_timer.reset();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double angle = m_drive.getAngle();
    if (m_direction == CLOCKWISE) {
      if (angle >= m_finalAngle) {
        m_finished = true;
        m_timer.start();
      }
      
    } else if (m_direction == COUNTER_CLOCKWISE) {
      if (angle <= m_finalAngle) {
        m_finished = true;
        m_timer.start();
      }
    }
    //m_timer.hasElapsed(m_brakeDelay) && 
    return m_finished;
  }
}
