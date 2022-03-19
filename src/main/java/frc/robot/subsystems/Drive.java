// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RoboRioAccelerometerHelper;
import frc.robot.RobotMap;
import frc.robot.testingdashboard.TestingDashboard;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

public class Drive extends SubsystemBase {
  private CANSparkMax m_frontLeft;
  private CANSparkMax m_frontRight;
  private RelativeEncoder m_frontLeftEncoder;
  private RelativeEncoder m_frontRightEncoder;
  private DifferentialDrive drivetrain;

  private BuiltInAccelerometer m_accelerometer;
  private RoboRioAccelerometerHelper m_accelHelper;
  private boolean m_measureVelocity;
  private boolean m_measureDistance;
  private double accelIntCount = 0;

  // Motor current variables
  ArrayList<Double> m_left_motor_current_values;
  ArrayList<Double> m_right_motor_current_values;
  public static final int MOTOR_CURRENT_INITIAL_CAPACITY = 50; // This is 1000 miliseconds divided in 20 millisecond chunks
  private int m_max_num_current_values;

  double m_rightSpeed;
  double m_leftSpeed;

  public static final double WHEEL_DIAMETER_IN_INCHES = 4; 
  public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER_IN_INCHES * Math.PI;
  public static final double GEAR_RATIO = 8.68; //number of times the motor rotates to rotate wheel once
  public static final double CONVERSION_FACTOR = WHEEL_CIRCUMFERENCE / GEAR_RATIO; //conversion factor * circumference = distance
  public final static double DISTANCE = CONVERSION_FACTOR * WHEEL_CIRCUMFERENCE;
  public final static double INITIAL_SPEED = 0.3;
  public final static double INITIAL_DISTANCE = 12;
  public final static double INITIAL_DEGREES = 90;
  

  private static Drive m_drive;
  

  /** Creates a new Drive. */
  private Drive() { 
    m_frontLeft = new CANSparkMax(RobotMap.D_FRONT_LEFT, MotorType.kBrushless);
    m_frontRight = new CANSparkMax(RobotMap.D_FRONT_RIGHT, MotorType.kBrushless);

    m_frontLeftEncoder = m_frontLeft.getEncoder();
    m_frontRightEncoder = m_frontRight.getEncoder();

    m_frontLeft.restoreFactoryDefaults();
    m_frontRight.restoreFactoryDefaults(); 

    drivetrain = new DifferentialDrive(m_frontLeft, m_frontRight);

    m_frontLeft.setInverted(true);
    m_frontRight.setInverted(false);

    setIdleMode(IdleMode.kBrake);
    setEncoderConversionFactor(CONVERSION_FACTOR);

    m_accelerometer = new BuiltInAccelerometer(); // unit: g
    m_accelHelper = new RoboRioAccelerometerHelper(m_accelerometer);

    m_measureVelocity = false;
    m_measureDistance = false;

    // initialize motor current variables
    m_left_motor_current_values = new ArrayList<Double>(MOTOR_CURRENT_INITIAL_CAPACITY);
    for (int i = 0; i < MOTOR_CURRENT_INITIAL_CAPACITY; i++) {
      m_left_motor_current_values.add(0.0);
    }
    m_right_motor_current_values = new ArrayList<Double>(MOTOR_CURRENT_INITIAL_CAPACITY);
    for (int i = 0; i < MOTOR_CURRENT_INITIAL_CAPACITY; i++) {
      m_right_motor_current_values.add(0.0);
    }

    m_max_num_current_values = MOTOR_CURRENT_INITIAL_CAPACITY;
  }

  public void setIdleMode(IdleMode mode) {
    
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

    if(m_frontLeftEncoder.setPositionConversionFactor(conversionFactor) != REVLibError.kOk){ 
      System.out.println("Could not set position conversion factor on front left encoder");
    }
  
    if(m_frontRightEncoder.setPositionConversionFactor(conversionFactor) != REVLibError.kOk){
      System.out.println("Could not set position conversion factor on front right encoder");
    } 
  }

  public static Drive getInstance() {
    if (m_drive == null) {
      m_drive = new Drive();
      TestingDashboard.getInstance().registerSubsystem(m_drive, "Drive");
      TestingDashboard.getInstance().registerNumber(m_drive, "Encoders", "FrontLeftMotorDistance", 0);
      TestingDashboard.getInstance().registerNumber(m_drive, "Encoders", "FrontRightMotorDistance", 0);
      TestingDashboard.getInstance().registerNumber(m_drive, "Encoders", "FrontLeftMotorSpeed", 0);
      TestingDashboard.getInstance().registerNumber(m_drive, "Encoders", "FrontRightMotorSpeed", 0);
      TestingDashboard.getInstance().registerNumber(m_drive, "Accelerometer", "xInstantAccel", 0);
      TestingDashboard.getInstance().registerNumber(m_drive, "Accelerometer", "yInstantAccel", 0);
      TestingDashboard.getInstance().registerNumber(m_drive, "Accelerometer", "instantAccelMagnitudeInchesPerSecondSquared", 0);
      TestingDashboard.getInstance().registerNumber(m_drive, "Accelerometer", "xInstantAccelRaw", 0);
      TestingDashboard.getInstance().registerNumber(m_drive, "Accelerometer", "yInstantAccelRaw", 0);
      TestingDashboard.getInstance().registerNumber(m_drive, "Accelerometer", "xInstantVel", 0);
      TestingDashboard.getInstance().registerNumber(m_drive, "Accelerometer", "yInstantVel", 0);
      TestingDashboard.getInstance().registerNumber(m_drive, "Accelerometer", "xInstantDist", 0);
      TestingDashboard.getInstance().registerNumber(m_drive, "Accelerometer", "yInstantDist", 0);
      TestingDashboard.getInstance().registerNumber(m_drive, "Accelerometer", "currentTime", 0);
      TestingDashboard.getInstance().registerNumber(m_drive, "Motors", "FrontLeftMotorCurrent", 0); // in Amps
      TestingDashboard.getInstance().registerNumber(m_drive, "Motors", "FrontRightMotorCurrent", 0); // in Amps
      TestingDashboard.getInstance().registerNumber(m_drive, "Robot", "BatteryVoltage", 0);
      TestingDashboard.getInstance().registerNumber(m_drive, "Travel", "DistanceToTravelInInches", 12);
      TestingDashboard.getInstance().registerNumber(m_drive, "Travel", "SpeedToTravel", INITIAL_SPEED);
      TestingDashboard.getInstance().registerNumber(m_drive, "Travel", "SpeedOfTravel", 0);
      TestingDashboard.getInstance().registerNumber(m_drive, "MotorCurrent", "MaxNumCurrentValues", MOTOR_CURRENT_INITIAL_CAPACITY);
      TestingDashboard.getInstance().registerNumber(m_drive, "Motors", "FrontLeftMotorCurrentAverage", 0);
      TestingDashboard.getInstance().registerNumber(m_drive, "Motors", "FrontRightMotorCurrentAverage", 0);
    }
    return m_drive;
  }

  //Drive Methods:

  public static double integrate(double tInitial, double tFinal, double vInitial, double vFinal) { // v for value
    double tInterval = tFinal - tInitial;
    double area = (tInterval * (vInitial + vFinal)) / 2;
    return area;
  }

  public void startMeasuringVelocity() {
    m_measureVelocity = true;
  }

  
  public void stopMeasuringVelocity() {
    m_measureVelocity = false;
  }

  
  public void startMeasuringDistance() {
    m_measureDistance = true;
  }

  
  public void stopMeasuringDistance() {
    m_measureDistance = false;
  }

  public void setInitialVelocity(double velocity) {
    m_accelHelper.initializeVelocity(velocity);
  }

  public void setInitialDistance(double distance) {
    m_accelHelper.initializeDistance(distance);
  }

  public void arcadeDrive(double fwd, double rot, boolean sqInputs) {
    drivetrain.arcadeDrive(fwd, rot, sqInputs);
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    m_rightSpeed = rightSpeed;
    m_leftSpeed = leftSpeed;
    drivetrain.tankDrive(leftSpeed, rightSpeed);
    TestingDashboard.getInstance().updateNumber(m_drive, "SpeedOfTravel", leftSpeed);
  }

  //Encoder Methods
  public RelativeEncoder getLeftEncoder() {
	  return m_frontLeftEncoder;
  }

  public RelativeEncoder getRightEncoder() {
	  return m_frontRightEncoder;
  }

  public double getTotalAverageLeftMotorCurrent() {
    return arrayListAverage(m_left_motor_current_values);
  }

  public double getTotalAverageRightMotorCurrent() {
    return arrayListAverage(m_right_motor_current_values);
  }

  void updateMotorCurrentAverages() {
    m_max_num_current_values = (int)TestingDashboard.getInstance().getNumber(m_drive, "MaxNumCurrentValues");
    double frontLeftMotorCurrent = m_frontLeft.getOutputCurrent();
    double frontRightMotorCurrent = m_frontRight.getOutputCurrent();
    m_left_motor_current_values.add(frontLeftMotorCurrent);
    m_right_motor_current_values.add(frontRightMotorCurrent);
    TestingDashboard.getInstance().updateNumber(m_drive, "FrontLeftMotorCurrent", frontLeftMotorCurrent);
    TestingDashboard.getInstance().updateNumber(m_drive, "FrontRightMotorCurrent", frontRightMotorCurrent);

    // Trim current buffers until they contain the correct number of entries.
    // Old entries are removed first.
    while (m_left_motor_current_values.size() > m_max_num_current_values) {
      m_left_motor_current_values.remove(0);
    }
    while (m_right_motor_current_values.size() > m_max_num_current_values) {
      m_right_motor_current_values.remove(0);
    }
    TestingDashboard.getInstance().updateNumber(m_drive, "FrontLeftMotorCurrentAverage", getTotalAverageLeftMotorCurrent());
    TestingDashboard.getInstance().updateNumber(m_drive, "FrontRightMotorCurrentAverage", getTotalAverageRightMotorCurrent());
  }

  public static double arrayListAverage(ArrayList<Double> arrayList) {
    double sum = 0;
    for (int i = 0; i < arrayList.size(); i++) {
      sum += arrayList.get(i);
    }
    return sum / arrayList.size();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    m_accelHelper.captureTimeData();
    m_accelHelper.captureAccelerometerData();
    //accelIntCount += 1;

    if (m_accelHelper.getAccelIntCount() > 2) {
      m_measureVelocity = true;
    }

    if (m_accelHelper.getAccelIntCount() > 4) {
      m_measureDistance = true;
    }

    if (m_measureVelocity) {
      m_accelHelper.calculateVelocity();
    }

    if (m_measureDistance) {
      m_accelHelper.calculateDistance();
    }

    TestingDashboard.getInstance().updateNumber(m_drive, "FrontLeftMotorDistance", m_frontLeftEncoder.getPosition());
    TestingDashboard.getInstance().updateNumber(m_drive, "FrontRightMotorDistance", m_frontRightEncoder.getPosition());
    TestingDashboard.getInstance().updateNumber(m_drive, "FrontLeftMotorSpeed", m_frontLeftEncoder.getVelocity());
    TestingDashboard.getInstance().updateNumber(m_drive, "FrontRightMotorSpeed", m_frontRightEncoder.getVelocity());
    TestingDashboard.getInstance().updateNumber(m_drive, "currentTime", m_accelHelper.getCurrentTime());
    TestingDashboard.getInstance().updateNumber(m_drive, "instantAccelMagnitudeInchesPerSecondSquared", m_accelHelper.getAccelerometerMagnitudeInchesPerSecondSquared());
   
    // Publish motor current values
    updateMotorCurrentAverages();
  }
}
