// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.testingdashboard.TestingDashboard;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Climber extends SubsystemBase {
  private static Climber m_climber;
  public final static double INITIAL_TRAVEL_SPEED = 0.3;
  public final static double INITIAL_CANE_EXTENTION_SPEED = 0.2;
  VictorSPX m_leftCaneMotor;
  VictorSPX m_rightCaneMotor;
  private DoubleSolenoid m_leftClawPiston;
  private DoubleSolenoid m_rightClawPiston;
  // limit switches on the top of the cane (one per cane) for detecting
  // contact with a bar
  private DigitalInput leftSwitch, rightSwitch;
  
  /** Creates a new Climber. */
  public Climber() {
    m_leftCaneMotor = new VictorSPX(RobotMap.CL_LEFT_ENCODER);
    m_rightCaneMotor = new VictorSPX(RobotMap.CL_RIGHT_ENCODER);
    m_rightCaneMotor.setInverted(true);
    leftSwitch = new DigitalInput(RobotMap.CL_LEFT_LIMIT_SWITCH);
    rightSwitch = new DigitalInput(RobotMap.CL_RIGHT_LIMIT_SWITCH);
    if (Constants.HW_AVAILABLE_PNEUMATIC_CONTROL_MODULE) {
      m_leftClawPiston = new DoubleSolenoid(RobotMap.PCM_CAN, PneumaticsModuleType.CTREPCM, RobotMap.CL_LEFT_CLAW_PISTON_PORT1, RobotMap.CL_LEFT_CLAW_PISTON_PORT2);
      m_rightClawPiston = new DoubleSolenoid(RobotMap.PCM_CAN, PneumaticsModuleType.CTREPCM, RobotMap.CL_RIGHT_CLAW_PISTON_PORT1, RobotMap.CL_RIGHT_CLAW_PISTON_PORT2);
    }
  }

  public static Climber getInstance() {
    if (m_climber == null) {
      m_climber = new Climber();
      TestingDashboard.getInstance().registerSubsystem(m_climber, "Climber");


      TestingDashboard.getInstance().registerNumber(m_climber, "Travel", "DistanceToTravelInInches", 12);
      TestingDashboard.getInstance().registerNumber(m_climber, "Travel", "SpeedToTravel", INITIAL_TRAVEL_SPEED);
      TestingDashboard.getInstance().registerNumber(m_climber, "Travel", "Sensor", Constants.NO_SENSOR);
      TestingDashboard.getInstance().registerNumber(m_climber, "Extension", "ExtensionSpeed", INITIAL_CANE_EXTENTION_SPEED);
    }
    return m_climber;
  }

  public DigitalInput getLeftSwitch() {
    return leftSwitch;
  }

  public DigitalInput getRightSwitch() {
    return rightSwitch;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void tankCane(double leftSpeed, double rightSpeed) {
    m_leftCaneMotor.set(ControlMode.PercentOutput, leftSpeed);
    m_rightCaneMotor.set(ControlMode.PercentOutput, rightSpeed);
  }

  public void openLeftClaw() {
    if (Constants.HW_AVAILABLE_PNEUMATIC_CONTROL_MODULE) {
      m_leftClawPiston.set(DoubleSolenoid.Value.kReverse);
    }
  }

  public void openRightClaw() {
    if (Constants.HW_AVAILABLE_PNEUMATIC_CONTROL_MODULE) {
      m_rightClawPiston.set(DoubleSolenoid.Value.kReverse);
    }
  }

  public void openClaws() {
    openLeftClaw();
    openRightClaw();
  }

  public void closeLeftClaw() {
    if (Constants.HW_AVAILABLE_PNEUMATIC_CONTROL_MODULE) {
      m_leftClawPiston.set(DoubleSolenoid.Value.kForward);
    }
  }

  public void closeRightClaw() {
    if (Constants.HW_AVAILABLE_PNEUMATIC_CONTROL_MODULE) {
      m_rightClawPiston.set(DoubleSolenoid.Value.kForward);
    }
  }

  public void closeClaws() {
    closeLeftClaw();
    closeRightClaw();
  }
}
