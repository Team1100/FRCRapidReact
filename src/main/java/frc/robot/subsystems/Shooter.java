/*----------------------------------------------------------------------------*/
/* Copytop (c) 2019 FIRST. All tops Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.testingdashboard.TestingDashboard;

public class Shooter extends SubsystemBase {
  
  private static Shooter m_shooter;
  private CANSparkMax m_topShooter;
  private CANSparkMax m_bottomShooter;
  private RelativeEncoder m_topEncoder;
  private RelativeEncoder m_bottomEncoder;
  private double kP, kI, kD;

  /**
   * Creates a new Shooter.
   */
  private Shooter() {
    
    m_topShooter = new CANSparkMax(RobotMap.S_TOP_MOTOR, MotorType.kBrushless);
    m_bottomShooter = new CANSparkMax(RobotMap.S_BOT_MOTOR, MotorType.kBrushless);
    m_topEncoder = m_topShooter.getEncoder();
    m_bottomEncoder = m_bottomShooter.getEncoder();


    kP = 0.00125;
    kI = 0.00045;
    kD = 0;
  }

  public static Shooter getInstance() {
    if (m_shooter == null) {
      m_shooter = new Shooter();
      TestingDashboard.getInstance().registerSubsystem(m_shooter, "Shooter");
      //Controlling shooter speeds
      TestingDashboard.getInstance().registerNumber(m_shooter, "topShooter", "topShooterInputSpeed", 0.2);
      TestingDashboard.getInstance().registerNumber(m_shooter, "topShooter", "topSetpoint", 2000);
      TestingDashboard.getInstance().registerNumber(m_shooter, "topShooter", "topShooterDist", 0);
      TestingDashboard.getInstance().registerNumber(m_shooter, "topShooter", "topShooterOutputSpeed", 0);
      TestingDashboard.getInstance().registerNumber(m_shooter, "bottomShooter", "bottomShooterInputSpeed", 0.2);
      TestingDashboard.getInstance().registerNumber(m_shooter, "bottomShooter", "bottomSetpoint", 2000);
      TestingDashboard.getInstance().registerNumber(m_shooter, "bottomShooter", "bottomShooterDist", 0);
      TestingDashboard.getInstance().registerNumber(m_shooter, "bottomShooter", "bottomShooterOutputSpeed", 0);
    }
    return m_shooter;
  }


  public void setTop(double speed) {
    m_topShooter.set(speed);
  }

  public void setBottom(double speed) {
    m_bottomShooter.set(speed);
  }

  public void setTopVoltage(double voltage) {
    m_topShooter.setVoltage(voltage);
  }

  public void setBottomVoltage(double voltage) {
    m_bottomShooter.setVoltage(voltage);
  }

  public RelativeEncoder getTopEncoder() {
    return m_topEncoder;
  }

  public RelativeEncoder getBottomEncoder() {
    return m_bottomEncoder;
  }

  public double getRPM(RelativeEncoder encoder) {
    return encoder.getVelocity();
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
    TestingDashboard.getInstance().updateNumber(this, "topShooterDist", m_topEncoder.getPosition());
    TestingDashboard.getInstance().updateNumber(this, "topShooterOutputSpeed", getRPM(m_topEncoder));
    TestingDashboard.getInstance().updateNumber(this, "bottomShooterDist", m_bottomEncoder.getPosition());
    TestingDashboard.getInstance().updateNumber(this, "bottomShooterOutputSpeed", getRPM(m_bottomEncoder));
  }
}
