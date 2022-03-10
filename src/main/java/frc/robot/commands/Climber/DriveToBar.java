// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.testingdashboard.TestingDashboard;
import frc.robot.Constants;
import frc.robot.sensors.BarDetectionSensorHelper;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;

public class DriveToBar extends CommandBase {
  static Drive m_drive;
  static Climber m_climber;
  private boolean m_parameterized;
  private double m_distance;
  private double m_speed;
  private int m_sensor;
  private int m_direction;
  private boolean m_leftSensorHasActivated;
  private boolean m_rightSensorHasActivated;
  private BarDetectionSensorHelper m_barSensor;

  private final static double ENCODER_INITIAL_POSITION = 0;
  

  /** Creates a new DriveToBar. */
  // distance is in inches
  public DriveToBar(double distance, double speed, int sensor, boolean parameterized) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = Drive.getInstance();
    m_climber = Climber.getInstance();
    addRequirements(m_climber);
    m_barSensor = new BarDetectionSensorHelper();
    m_parameterized = parameterized;
    m_distance = distance;
    m_speed = speed;
    m_sensor = sensor;
    m_direction = 1;
    m_leftSensorHasActivated = false;
    m_rightSensorHasActivated = false;
  }

  public static void registerWithTestingDashboard() {
    Climber climber = Climber.getInstance();
    DriveToBar cmd = new DriveToBar(12.0, Climber.INITIAL_TRAVEL_SPEED, Constants.NO_SENSOR, false);
    TestingDashboard.getInstance().registerCommand(climber, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RelativeEncoder leftEncoder = m_drive.getLeftEncoder();
    RelativeEncoder rightEncoder = m_drive.getRightEncoder();
    leftEncoder.setPosition(ENCODER_INITIAL_POSITION);
    rightEncoder.setPosition(ENCODER_INITIAL_POSITION);
    m_leftSensorHasActivated = false;
    m_rightSensorHasActivated = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_parameterized) {
      m_distance = TestingDashboard.getInstance().getNumber(m_climber, "DistanceToTravelInInches");
      m_speed = TestingDashboard.getInstance().getNumber(m_climber, "SpeedToTravel");
      m_sensor = (int) TestingDashboard.getInstance().getNumber(m_climber, "Sensor");
    }

    // determines the direction to travel. Will be used to multiply the speeds later.
    if (m_distance >= 0) {
      m_direction = 1;
    } else {
      m_direction = -1;
    }
    /** 
     * The tankDrive is automatically set to go straight.
     * If the left sensor is activated, the left motor is stopped and the right motor continues at half it's speed.
     * If the right sensor is activated, the right motor is stopped and the left motor continues at half it's speed.
     **/ 
    m_drive.tankDrive(m_speed * m_direction, m_speed * m_direction);
    if (m_barSensor.leftSensorActivated(m_sensor)) {
      m_drive.tankDrive(0, (m_speed) * m_direction);
      m_leftSensorHasActivated = true;
    }
    if (m_barSensor.rightSensorActivated(m_sensor)) {
      m_drive.tankDrive((m_speed) * m_direction, 0);
      m_rightSensorHasActivated = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    RelativeEncoder leftEncoder = m_drive.getLeftEncoder();
    RelativeEncoder rightEncoder = m_drive.getRightEncoder();
    boolean finished = false;
    /** 
     * If no sensor is to be used, the method runs like the driveDistance command. 
     * Otherwise it will wait until both sensors have been activated before ending the command.
     **/ 
    if (m_sensor == Constants.NO_SENSOR) {
      if (m_distance >= 0) {
        if (leftEncoder.getPosition() >= m_distance || rightEncoder.getPosition() >= m_distance) {
          finished = true;
        }
      } else if (m_distance < 0) {
        if (leftEncoder.getPosition() <= m_distance || rightEncoder.getPosition() <= m_distance) {
          finished = true;
        }
      }
    } else {
      if (m_leftSensorHasActivated && m_rightSensorHasActivated) {
        finished = true;
      }
    }
    
    return finished;
  }
}
