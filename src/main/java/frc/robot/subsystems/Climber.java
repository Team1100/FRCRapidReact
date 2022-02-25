// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.testingdashboard.TestingDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Climber extends SubsystemBase {
  private static Climber m_climber;
  VictorSPX m_leftCaneMotor;
  VictorSPX m_rightCaneMotor;
  
  /** Creates a new Climber. */
  public Climber() {
    m_leftCaneMotor = new VictorSPX(RobotMap.C_LEFT_ENCODER);
    m_rightCaneMotor = new VictorSPX(RobotMap.C_RIGHT_ENCODER);
    m_rightCaneMotor.setInverted(true);
  }

  public static Climber getInstance() {
    if (m_climber == null) {
      m_climber = new Climber();
      TestingDashboard.getInstance().registerSubsystem(m_climber, "Climber");
    }
    return m_climber;
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
