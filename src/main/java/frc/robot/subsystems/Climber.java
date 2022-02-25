// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.testingdashboard.TestingDashboard;
import frc.robot.Constants;
import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Climber extends SubsystemBase {
  private static Climber m_climber;
  public final static double INITIAL_SPEED = 0.3;
  VictorSPX m_leftCaneMotor;
  VictorSPX m_rightCaneMotor;
  private DigitalInput leftSwitch, rightSwitch;
  
  /** Creates a new Climber. */
  public Climber() {
    m_leftCaneMotor = new VictorSPX(RobotMap.CL_LEFT_ENCODER);
    m_rightCaneMotor = new VictorSPX(RobotMap.CL_RIGHT_ENCODER);
    m_rightCaneMotor.setInverted(true);
    leftSwitch = new DigitalInput(RobotMap.CL_LEFT_LIMIT_SWITCH);
    rightSwitch = new DigitalInput(RobotMap.CL_RIGHT_LIMIT_SWITCH);
  }

  public static Climber getInstance() {
    if (m_climber == null) {
      m_climber = new Climber();
      TestingDashboard.getInstance().registerSubsystem(m_climber, "Climber");


      TestingDashboard.getInstance().registerNumber(m_climber, "Travel", "DistanceToTravelInInches", 12);
      TestingDashboard.getInstance().registerNumber(m_climber, "Travel", "SpeedToTravel", INITIAL_SPEED);
      TestingDashboard.getInstance().registerNumber(m_climber, "Travel", "Sensor", Constants.NO_SENSOR);

    }
    return m_climber;
  }

  public boolean leftSensorActivated(int sensor) {
    switch (sensor) {
      case Constants.NO_SENSOR: 
        break;
      case Constants.MOTOR_CURRENT:
        break;
      case Constants.LIMIT_SWITCH:
        if (leftSwitch.get())
          return true;
        else 
          return false;
    }
    return false;
  }

  public boolean rightSensorActivated(int sensor) {
    switch (sensor) {
      case Constants.NO_SENSOR: 
        break;
      case Constants.MOTOR_CURRENT:
        break;
      case Constants.LIMIT_SWITCH:
        if (rightSwitch.get())
          return true;
        else 
          return false;
    }
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void tankCane(double leftSpeed, double rightSpeed) {
    m_leftCaneMotor.set(ControlMode.PercentOutput, leftSpeed);
    m_rightCaneMotor.set(ControlMode.PercentOutput, rightSpeed);
  }
}
