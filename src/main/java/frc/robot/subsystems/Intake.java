// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.testingdashboard.TestingDashboard;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Intake extends SubsystemBase {

  private static Intake m_intake;
  private CANSparkMax m_intakeRollerMotor;
  private DoubleSolenoid m_leftPiston;
  private DoubleSolenoid m_rightPiston;
  public static final double DEFAULT_ROLLER_SPEED = 0.3;
  
  /** Creates a new Intake. */
  private Intake() {
    m_intakeRollerMotor = new CANSparkMax(RobotMap.I_LEFT_ROLLER, MotorType.kBrushless);
    if (Constants.HW_AVAILABLE_PNEUMATIC_CONTROL_MODULE) {
      m_leftPiston = new DoubleSolenoid(RobotMap.PCM_CAN, PneumaticsModuleType.CTREPCM, RobotMap.I_LEFT_PISTON_PORT1, RobotMap.I_LEFT_PISTON_PORT2);
      m_rightPiston = new DoubleSolenoid(RobotMap.PCM_CAN, PneumaticsModuleType.CTREPCM, RobotMap.I_RIGHT_PISTON_PORT1, RobotMap.I_RIGHT_PISTON_PORT2);
    }
  }

  public static Intake getInstance() {
    if (m_intake == null) {
      m_intake = new Intake();
      TestingDashboard.getInstance().registerSubsystem(m_intake, "Intake");
      TestingDashboard.getInstance().registerNumber(m_intake, "Inputs", "RollerSpeed", DEFAULT_ROLLER_SPEED);
    }
    return m_intake;
  }

  public void spinIntakeRoller(double speed) {
    m_intakeRollerMotor.set(speed);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void raiseIntake() {
    if (Constants.HW_AVAILABLE_PNEUMATIC_CONTROL_MODULE) {
      m_leftPiston.set(DoubleSolenoid.Value.kReverse);
      m_rightPiston.set(DoubleSolenoid.Value.kReverse);
    }
  }
 
  public void lowerIntake() {
    if (Constants.HW_AVAILABLE_PNEUMATIC_CONTROL_MODULE) {
      m_leftPiston.set(DoubleSolenoid.Value.kForward);
      m_rightPiston.set(DoubleSolenoid.Value.kForward);
    }
  }
}
