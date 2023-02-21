/*----------------------------------------------------------------------------*/
/* Copytop (c) 2019 FIRST. All tops Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.testingdashboard.TestingDashboard;

public class Shooter extends SubsystemBase {
  
  private static Shooter m_shooter;
  private CANSparkMax m_shooterMotor;
  private SparkMaxPIDController m_shooterPID;
  private RelativeEncoder m_shooterEncoder;
  private double m_P, m_I, m_D;

  /**
   * Creates a new Shooter.
   */
  private Shooter() {
    
    if (Constants.HW_ENABLE_SHOOTER) {
      m_shooterMotor = new CANSparkMax(RobotMap.S_MOTOR, MotorType.kBrushless);
      m_shooterMotor.setInverted(true);
      m_shooterPID = m_shooterMotor.getPIDController();
      m_shooterEncoder = m_shooterMotor.getEncoder();
    }

    m_P = 0.00125;
    m_I = 0.00045;
    m_D = 0;

    m_shooterPID.setP(m_P);
    m_shooterPID.setI(m_I);
    m_shooterPID.setD(m_D);
  }

  public static Shooter getInstance() {
    if (m_shooter == null) {
      m_shooter = new Shooter();
      TestingDashboard.getInstance().registerSubsystem(m_shooter, "Shooter");
      // Controlling shooter speeds
      TestingDashboard.getInstance().registerNumber(m_shooter, "Shooter", "ShooterInputSpeed", 0.2);
      TestingDashboard.getInstance().registerNumber(m_shooter, "Shooter", "Setpoint", 2000);
      TestingDashboard.getInstance().registerNumber(m_shooter, "Shooter", "ShooterDist", 0);
      TestingDashboard.getInstance().registerNumber(m_shooter, "Shooter", "ShooterOutputSpeed", 0);
      TestingDashboard.getInstance().registerNumber(m_shooter, "Shooter", "TargetDistance", 0);
    }
    return m_shooter;
  }


  public void setMotor(double speed) {
    if (Constants.HW_ENABLE_SHOOTER) {
      m_shooterMotor.set(speed);
    }
  }

  public void setMotorVoltage(double voltage) {
    if (Constants.HW_ENABLE_SHOOTER) {
      m_shooterMotor.setVoltage(voltage);
    }
  }

  public double getMotorRpm() {
    double velocity = 0;
    if (Constants.HW_ENABLE_SHOOTER) {
      velocity = m_shooterEncoder.getVelocity();
    }
    return velocity;
  }

  public void setPIDDistance(double distance)
  {
    m_shooterPID.setReference(distance, ControlType.kPosition);
  }

  public double getMotorDistance() {
    double distance = 0;
    if (Constants.HW_ENABLE_SHOOTER) {
      distance = m_shooterEncoder.getPosition();
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
    if (Constants.SHOOTER_PERIODIC_ENABLE) {
      // This method will be called once per scheduler run
      TestingDashboard.getInstance().updateNumber(this, "ShooterDist", getMotorDistance());
      TestingDashboard.getInstance().updateNumber(this, "ShooterOutputSpeed", getMotorRpm());
    }
  }
}
