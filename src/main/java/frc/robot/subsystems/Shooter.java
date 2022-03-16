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
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.testingdashboard.TestingDashboard;

public class Shooter extends SubsystemBase {
  
  private static Shooter m_shooter;
  private CANSparkMax m_topShooter;
  private CANSparkMax m_bottomShooter;
  private RelativeEncoder m_topEncoder;
  private RelativeEncoder m_bottomEncoder;
  private double m_P, m_I, m_D;

  /**
   * Creates a new Shooter.
   */
  private Shooter() {
    
    if (Constants.HW_ENABLE_SHOOTER) {
      m_topShooter = new CANSparkMax(RobotMap.S_TOP_MOTOR, MotorType.kBrushless);
      m_bottomShooter = new CANSparkMax(RobotMap.S_BOT_MOTOR, MotorType.kBrushless);
      m_topEncoder = m_topShooter.getEncoder();
      m_bottomEncoder = m_bottomShooter.getEncoder();
    }

    m_P = 0.00125;
    m_I = 0.00045;
    m_D = 0;
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
    if (Constants.HW_ENABLE_SHOOTER) {
      m_topShooter.set(speed);
    }
  }

  public void setBottom(double speed) {
    if (Constants.HW_ENABLE_SHOOTER) {
      m_bottomShooter.set(speed);
    }
  }

  public void setTopVoltage(double voltage) {
    if (Constants.HW_ENABLE_SHOOTER) {
      m_topShooter.setVoltage(voltage);
    }
  }

  public void setBottomVoltage(double voltage) {
    if (Constants.HW_ENABLE_SHOOTER) {
      m_bottomShooter.setVoltage(voltage);
    }
  }

  public double getTopRpm() {
    double velocity = 0;
    if (Constants.HW_ENABLE_SHOOTER) {
      velocity = m_topEncoder.getVelocity();
    }
    return velocity;
  }

  public double getBottomRpm() {
    double velocity = 0;
    if (Constants.HW_ENABLE_SHOOTER) {
      velocity = m_bottomEncoder.getVelocity();
    }
    return velocity;
  }

  public double getTopDistance() {
    double distance = 0;
    if (Constants.HW_ENABLE_SHOOTER) {
      distance = m_topEncoder.getPosition();
    }
    return distance;
  }

  public double getBottomDistance() {
    double distance = 0;
    if (Constants.HW_ENABLE_SHOOTER) {
      distance = m_bottomEncoder.getPosition();
    }
    return distance;
  }

  public double getP() {
    return m_P;
  }

  public double getI() {
    return m_I;
  }

  public double getD() {
    return m_D;
  }

  public void setP(double P) {
    m_P = P;
  }

  public void setI(double I) {
    m_I = I;
  }

  public void setD(double D) {
    m_D = D;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    TestingDashboard.getInstance().updateNumber(this, "topShooterDist", getTopDistance());
    TestingDashboard.getInstance().updateNumber(this, "topShooterOutputSpeed", getTopRpm());
    TestingDashboard.getInstance().updateNumber(this, "bottomShooterDist", getBottomDistance());
    TestingDashboard.getInstance().updateNumber(this, "bottomShooterOutputSpeed", getBottomRpm());
  }
}
