/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.testingdashboard.TestingDashboard;

public class Shooter extends SubsystemBase {
  private static Shooter m_shooter;
  private CANSparkMax m_topShooter;
  private Encoder m_topEncoder;
  private final double PPD = 2048;
  private DoubleSolenoid m_piston;
  private double kP, kI, kD;

  /**
   * Creates a new Shooter.
   */
  private Shooter() {
    
    m_topShooter = new CANSparkMax(RobotMap.SH_TOP, MotorType.kBrushless);
    m_topEncoder = new Encoder(RobotMap.SH_TOP_ENCODER_A, RobotMap.SH_TOP_ENCODER_B);
    

    m_topEncoder.setDistancePerPulse(1/PPD);
    m_topEncoder.setReverseDirection(true);

    kP = 0.00125;
    kI = 0.00045;
    kD = 0;
  }

  public static Shooter getInstance() {
    if (m_shooter == null) {
      m_shooter = new Shooter();
      TestingDashboard.getInstance().registerSubsystem(m_shooter, "Shooter");
      //Controlling shooter speeds
      TestingDashboard.getInstance().registerNumber(m_shooter, "TopShooter", "TopShooterInputSpeed", 0.2);
      TestingDashboard.getInstance().registerNumber(m_shooter, "TopShooter", "Top Setpoint", 2000);
      TestingDashboard.getInstance().registerNumber(m_shooter, "TopShooter", "TopShooterDist", 0);
      TestingDashboard.getInstance().registerNumber(m_shooter, "TopShooter", "TopShooterOutputSpeed", 0);
    }
    return m_shooter;
  }


  public void setTop(double speed) {
    m_topShooter.set(-speed);
  }

  public void setTopVoltage(double voltage) {
    m_topShooter.setVoltage(-voltage);
  }

  public Encoder getTopEncoder() {
    return m_topEncoder;
  }

  public double getRPM(Encoder encoder) {
    return encoder.getRate() * 60;
  }

  public DoubleSolenoid getPiston() {
    return m_piston;
  }

  public double getkP() {
    return kP;
  }

  public double getkI() {
    return kI;
  }

  public double getkD() {
    return kD;
  }

  public void setkP(double kP) {
    this.kP = kP;
  }

  public void setkI(double kI) {
    this.kI = kI;
  }

  public void setkD(double kD) {
    this.kD = kD;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    TestingDashboard.getInstance().updateNumber(this, "TopShooterDist", m_topEncoder.getDistance());
    TestingDashboard.getInstance().updateNumber(this, "TopShooterOutputSpeed", getRPM(m_topEncoder));
  }
}
