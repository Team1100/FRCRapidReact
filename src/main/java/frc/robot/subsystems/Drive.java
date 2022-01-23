// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drive extends SubsystemBase {
  private CANSparkMax m_backLeft;
  private CANSparkMax m_backRight;
  private CANSparkMax m_frontLeft;
  private CANSparkMax m_frontRight;
  private RelativeEncoder m_backLeftEncoder;
  private RelativeEncoder m_backRightEncoder;
  private RelativeEncoder m_frontLeftEncoder;
  private RelativeEncoder m_frontRightEncoder;

  public static final double WHEEL_DIAMETER_IN_INCHES = 4; 
  public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER_IN_INCHES * Math.PI;
  public static final double GEAR_RATIO = 6.85; //number of times the motor rotates to rotate wheel once
  public static final double CONVERSION_FACTOR = WHEEL_CIRCUMFERENCE / GEAR_RATIO; //conversion factor * circumference = distance
  public final static double DISTANCE = CONVERSION_FACTOR * WHEEL_CIRCUMFERENCE;
  

  private static Drive m_drive;
  

  /** Creates a new Drive. */
  public Drive() {
    m_backLeft = new CANSparkMax(RobotMap.D_BACK_LEFT, MotorType.kBrushless);
    m_backRight = new CANSparkMax(RobotMap.D_BACK_RIGHT, MotorType.kBrushless);
    m_frontLeft = new CANSparkMax(RobotMap.D_FRONT_LEFT, MotorType.kBrushless);
    m_frontRight = new CANSparkMax(RobotMap.D_FRONT_RIGHT, MotorType.kBrushless);

    m_backLeftEncoder = m_backLeft.getEncoder();
    m_backRightEncoder = m_backRight.getEncoder();
    m_frontLeftEncoder = m_frontLeft.getEncoder();
    m_frontRightEncoder = m_frontRight.getEncoder();

    m_backLeft.restoreFactoryDefaults();
    m_backRight.restoreFactoryDefaults();
    m_frontLeft.restoreFactoryDefaults();
    m_frontRight.restoreFactoryDefaults();

    setIdleMode(IdleMode.kBrake);
    setEncoderConversionFactor(CONVERSION_FACTOR);

  }

  public void setIdleMode(IdleMode mode) {
    if(m_backLeft.setIdleMode(mode) != REVLibError.kOk){
      System.out.println("Could not set idle mode on back left motor");
      System.exit(1);
    }
  
    if(m_backRight.setIdleMode(mode) != REVLibError.kOk){
      System.out.println("Could not set idle mode on back right motor");
      System.exit(1);
    }
    
    if(m_frontLeft.setIdleMode(mode) != REVLibError.kOk){
      System.out.println("Could not set idle mode on front left motor");
      System.exit(1);
    }
  
    if(m_frontRight.setIdleMode(mode) != REVLibError.kOk){
      System.out.println("Could not set idle mode on front right motor");
      System.exit(1);
    }
  }

  public void setEncoderConversionFactor(double conversionFactor) {
    if(m_backLeftEncoder.setPositionConversionFactor(conversionFactor) != REVLibError.kOk){ 
      System.out.println("Could not set position conversion factor on back left encoder");
    }
  
    if(m_backRightEncoder.setPositionConversionFactor(conversionFactor) != REVLibError.kOk){
      System.out.println("Could not set position conversion factor on back right encoder");
    } 

    if(m_frontLeftEncoder.setPositionConversionFactor(conversionFactor) != REVLibError.kOk){ 
      System.out.println("Could not set position conversion factor on front left encoder");
    }
  
    if(m_frontRightEncoder.setPositionConversionFactor(conversionFactor) != REVLibError.kOk){
      System.out.println("Could not set position conversion factor on front right encoder");
    } 
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
