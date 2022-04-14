// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.testingdashboard.TestingDashboard;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;

public class DriveDistance extends CommandBase {
  static Drive m_drive;
  boolean m_parameterized;
  double m_distance;
  double m_speed;
  boolean m_finished;

  private Timer m_timer;

  private double m_brakeDelay = 2; // ensures that the brake mode has time to take effect before being shut off
  private final static double ENCODER_INITIAL_POSITION = 0;
  final static double BRAKE_DELAY = 2;

  /** Creates a new DriveDistance. */
  // distance is in inches
  public DriveDistance(double distance, double speed, double brakeDelay, boolean parameterized) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = Drive.getInstance();
    addRequirements(m_drive);
    m_parameterized = parameterized;
    m_distance = distance;
    m_speed = speed;
    m_timer = new Timer();
    m_brakeDelay = brakeDelay;
    m_finished = false;
  }

  public static void registerWithTestingDashboard() {
    Drive drive = Drive.getInstance();
    DriveDistance cmd = new DriveDistance(12.0, Drive.INITIAL_SPEED, BRAKE_DELAY, false);
    TestingDashboard.getInstance().registerCommand(drive, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RelativeEncoder leftEncoder = m_drive.getLeftEncoder();
    RelativeEncoder rightEncoder = m_drive.getRightEncoder();
    leftEncoder.setPosition(ENCODER_INITIAL_POSITION);
    rightEncoder.setPosition(ENCODER_INITIAL_POSITION);
    m_timer.reset();
    m_finished = false;
    m_drive.setIdleMode(IdleMode.kBrake);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_parameterized) {
      m_distance = TestingDashboard.getInstance().getNumber(m_drive, "DistanceToTravelInInches");
      m_speed = TestingDashboard.getInstance().getNumber(m_drive, "SpeedToTravel");
      m_brakeDelay = BRAKE_DELAY;
    }
    if (m_distance >= 0) {
      m_drive.tankDrive(m_speed, m_speed);
    } else {
      m_drive.tankDrive(-m_speed, -m_speed);
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.setIdleMode(IdleMode.kCoast);
    m_timer.stop();
    m_timer.reset();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    RelativeEncoder leftEncoder = m_drive.getLeftEncoder();
    RelativeEncoder rightEncoder = m_drive.getRightEncoder();
    // if the distance has not been driven then the encoder checks the endstate
    // if the endstate is detected, the timer is started and the motors are stopped
    if (!m_finished) {
      if (m_distance >= 0) {
        if (leftEncoder.getPosition() >= m_distance || rightEncoder.getPosition() >= m_distance) {
          m_timer.start();
          m_finished = true;
        }
      } else if (m_distance < 0) {
        if (leftEncoder.getPosition() <= m_distance || rightEncoder.getPosition() <= m_distance) {
          m_timer.start();
          m_finished = true;
        }
      }
    }
    if (m_finished) {
      m_drive.tankDrive(0, 0);
    }
    // if the timer has elapsed the delay and the command is finished, the command can end
    return m_timer.hasElapsed(m_brakeDelay) && m_finished;
  }
}
