// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.testingdashboard.TestingDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Intake extends SubsystemBase {

  private static Intake m_intake;
  private CANSparkMax m_intakeRoller1;
  private CANSparkMax m_intakeRoller2;
  public static final double DEF_ROLLER_SPEED = 0.5;
  
  /** Creates a new Intake. */
  private Intake() {
    m_intakeRoller1 = new CANSparkMax(RobotMap.I_INTAKE_ROLLER1, MotorType.kBrushless);
  }

  public static Intake getInstance() {
    if (m_intake == null) {
      m_intake = new Intake();
      TestingDashboard.getInstance().registerSubsystem(m_intake, "Vision");
    }
    return m_intake;
  }

  public void spinIntakeRoller(double speed) {
    m_intakeRoller1.set(speed);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
