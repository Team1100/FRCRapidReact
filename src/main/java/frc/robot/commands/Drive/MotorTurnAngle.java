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

public class MotorTurnAngle extends CommandBase {
  static Drive m_drive;
  boolean m_parameterized;
  double m_distance;
  double m_speed;
  double m_angle;
  boolean m_finished;
  double m_direction; // positive is clockwise, negative is counter-clockwise

  final static double CLOCKWISE = 1;
  final static double COUNTER_CLOCKWISE = -1;
  final static double DIAMETER = 25.75;


  private Timer m_timer;

  private double m_brakeDelay = 2; // ensures that the brake mode has time to take effect before being shut off
  private final static double ENCODER_INITIAL_POSITION = 0;
  final static double BRAKE_DELAY = 1;

  /** Creates a new DriveDistance. */
  // distance is in inches
  public MotorTurnAngle(double angle, double speed, double brakeDelay, boolean parameterized) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = Drive.getInstance();
    addRequirements(m_drive);
    m_parameterized = parameterized;
    m_speed = speed;
    m_timer = new Timer();
    m_brakeDelay = brakeDelay;
    m_finished = false;
    m_angle = angle;
    m_direction = m_angle/Math.abs(m_angle);
    m_distance = (m_angle / 360) * DIAMETER * Math.PI;
  }

  public static void registerWithTestingDashboard() {
    Drive drive = Drive.getInstance();
    MotorTurnAngle cmd = new MotorTurnAngle(12.0, Drive.INITIAL_SPEED, BRAKE_DELAY, false);
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
    if (!m_parameterized) {
      m_angle = TestingDashboard.getInstance().getNumber(m_drive, "TurnAngleInDegrees");
      m_speed = TestingDashboard.getInstance().getNumber(m_drive, "SpeedWhenTurning");
      m_brakeDelay = BRAKE_DELAY;
      m_distance = (m_angle / 360) * DIAMETER * Math.PI;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_direction == CLOCKWISE) {
      // Turning clockwise
      m_drive.tankDrive(m_speed, -m_speed);
    } else {
      // turning counterclockwise
      m_drive.tankDrive(-m_speed, m_speed);
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
      if (m_direction == CLOCKWISE) {
        if (leftEncoder.getPosition() >= m_distance || rightEncoder.getPosition() <= -m_distance) {
          m_timer.start();
          m_finished = true;
        }
      } else if (m_direction == COUNTER_CLOCKWISE) {
        if (leftEncoder.getPosition() <= m_distance || rightEncoder.getPosition() >= -m_distance) {
          m_timer.start();
          m_finished = true;
        }
      }
    }
    if (m_finished) {
      m_drive.tankDrive(0, 0);
    }
    // if the timer has elapsed the delay and the command is finished, the command can end
    //  
    return m_timer.hasElapsed(m_brakeDelay) && m_finished;
  }
}
