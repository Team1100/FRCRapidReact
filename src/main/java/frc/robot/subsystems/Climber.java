// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.testingdashboard.TestingDashboard;
import frc.robot.Constants;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Climber extends SubsystemBase {
  private static Climber m_climber;
  public final static double INITIAL_TRAVEL_SPEED = 0.3;
  public final static double INITIAL_CANE_EXTENTION_SPEED = 0.7;
  public final static double INITIAL_CANE_ROTATION_SPEED = 0.3;

  public static final double GEAR_DIAMETER_IN_INCHES = 4;
  public static final double GEAR_CIRCUMFERENCE_IN_INCHES = GEAR_DIAMETER_IN_INCHES * Math.PI;
  public static final double ENCODER_INITIAL_POSITION = 0;
  public static final double GEAR_RATIO = 8.68; //number of times the motor rotates to rotate wheel once
  public static final double CONVERSION_FACTOR = 0.34; // 1 revolution of the motor will extend the cane by 0.34 inches
  public final static double DISTANCE = CONVERSION_FACTOR * GEAR_CIRCUMFERENCE_IN_INCHES;

  private CANSparkMax m_leftCaneMotor;
  private CANSparkMax m_rightCaneMotor;

  private RelativeEncoder m_leftCaneEncoder;
  private RelativeEncoder m_rightCaneEncoder;


  private double m_currentLeftCaneHeight;
  private double m_currentRightCaneHeight;
  private VictorSPX m_leftCaneRotationMotor;
  private VictorSPX m_rightCaneRotationMotor;
  private Encoder m_caneRotationEncoder;
  private static final double ROTATION_ECODER_PULSES_PER_REVOLUTION = 2048;
  private static final double ROTATION_ENCODER_DEGREES_PER_PULSE = 360 / ROTATION_ECODER_PULSES_PER_REVOLUTION; // Degrees

  private AnalogInput m_potentiometer;
  private double m_caneRotateSpeed;
  
  // TODO: Adjust the Pot min/max voltage, min/max angle, and degrees per volt for current potentiometer
  public static final double POTENTIOMETER_MIN = 1.1; // voltage
  public static final double POTENTIOMETER_MAX = 3.5; // voltage
  
  public static final double MIN_ANGLE = 110.0;
  public static final double MAX_ANGLE = 350.0;
  public static final double DEGREES_PER_VOLT = 100;

  ArrayList<Double> m_left_cane_motor_current_values;
  ArrayList<Double> m_right_cane_motor_current_values;

  public static final int MOTOR_CURRENT_INITIAL_CAPACITY = 50; // This is 1000 miliseconds divided in 20 millisecond chunks
  private int m_max_num_current_values;

  // limit switches on the top of the cane (one per cane) for detecting
  // contact with a bar
  private DigitalInput leftSwitch, rightSwitch;
  
  /** Creates a new Climber. */
  public Climber() {
    m_leftCaneRotationMotor = new VictorSPX(RobotMap.CL_LEFT_CANE_TURN_MOTOR);
    m_rightCaneRotationMotor = new VictorSPX(RobotMap.CL_RIGHT_CANE_TURN_MOTOR);
    m_leftCaneRotationMotor.setNeutralMode(NeutralMode.Brake);
    m_rightCaneRotationMotor.setNeutralMode(NeutralMode.Brake);
    
    m_leftCaneMotor = new CANSparkMax(RobotMap.CL_LEFT_MOTOR, MotorType.kBrushless);
    m_rightCaneMotor = new CANSparkMax(RobotMap.CL_RIGHT_MOTOR, MotorType.kBrushless);
    m_rightCaneMotor.setInverted(false);
    m_leftCaneMotor.setInverted(true);
    leftSwitch = new DigitalInput(RobotMap.CL_LEFT_LIMIT_SWITCH);
    rightSwitch = new DigitalInput(RobotMap.CL_RIGHT_LIMIT_SWITCH);
   
    m_leftCaneEncoder = m_leftCaneMotor.getEncoder();
    m_rightCaneEncoder = m_rightCaneMotor.getEncoder();

    m_caneRotationEncoder = new Encoder(RobotMap.CL_CANE_TURN_ENCODER_A, RobotMap.CL_CANE_TURN_ENCODER_B);
    m_caneRotationEncoder.setDistancePerPulse(ROTATION_ENCODER_DEGREES_PER_PULSE);

    m_caneRotateSpeed = 0;
    m_potentiometer = new AnalogInput(RobotMap.CL_POTENTIOMETER);

    // Initializes the arraylist for the motors that extend the cane
    m_left_cane_motor_current_values = new ArrayList<Double>(MOTOR_CURRENT_INITIAL_CAPACITY);
    for (int i = 0; i < MOTOR_CURRENT_INITIAL_CAPACITY; i++) {
      m_left_cane_motor_current_values.add(0.0);
    }
    m_right_cane_motor_current_values = new ArrayList<Double>(MOTOR_CURRENT_INITIAL_CAPACITY);
    for (int i = 0; i < MOTOR_CURRENT_INITIAL_CAPACITY; i++) {
      m_right_cane_motor_current_values.add(0.0);
    }


    m_max_num_current_values = MOTOR_CURRENT_INITIAL_CAPACITY;

    setExtensionEncoderConversionFactor(CONVERSION_FACTOR);
  }
  

  public static Climber getInstance() {
    if (m_climber == null) {
      m_climber = new Climber();
      TestingDashboard.getInstance().registerSubsystem(m_climber, "Climber");
      TestingDashboard.getInstance().registerNumber(m_climber, "Travel", "DistanceToTravelInInches", 12);
      TestingDashboard.getInstance().registerNumber(m_climber, "Travel", "SpeedToTravel", INITIAL_TRAVEL_SPEED);
      TestingDashboard.getInstance().registerNumber(m_climber, "Travel", "Sensor", Constants.NO_SENSOR);
      TestingDashboard.getInstance().registerNumber(m_climber, "CaneInputs", "ExtensionSpeed", INITIAL_CANE_EXTENTION_SPEED);
      TestingDashboard.getInstance().registerNumber(m_climber, "CaneInputs", "SlowerExtensionSpeed", INITIAL_CANE_EXTENTION_SPEED);
      TestingDashboard.getInstance().registerNumber(m_climber, "CaneInputs", "RotationSpeed", INITIAL_CANE_ROTATION_SPEED);
      TestingDashboard.getInstance().registerNumber(m_climber, "CaneInputs", "NumClimbCycles", Constants.DEFAULT_NUMBER_OF_CLIMB_CYCLES);
      TestingDashboard.getInstance().registerNumber(m_climber, "CaneOutputs", "CurrentLeftExtensionSpeed", 0);
      TestingDashboard.getInstance().registerNumber(m_climber, "CaneOutputs", "CurrentRightExtensionSpeed", 0);
      TestingDashboard.getInstance().registerNumber(m_climber, "CaneOutputs", "CurrentLeftRotationSpeed", 0);
      TestingDashboard.getInstance().registerNumber(m_climber, "CaneOutputs", "CurrentRightRotationSpeed", 0);
      TestingDashboard.getInstance().registerNumber(m_climber, "CaneOutputs", "CurrentLeftEncoderPostition", 0);
      TestingDashboard.getInstance().registerNumber(m_climber, "CaneOutputs", "CurrentRightEncoderPostition", 0);
      TestingDashboard.getInstance().registerNumber(m_climber, "CaneOutputs", "CurrentLeftEncoderVelocity", 0);
      TestingDashboard.getInstance().registerNumber(m_climber, "CaneOutputs", "CurrentRightEncoderVelocity", 0);
      TestingDashboard.getInstance().registerNumber(m_climber, "CaneOutputs", "CurrentRotationEncoderRate", 0);
      TestingDashboard.getInstance().registerNumber(m_climber, "Potentiometer", "CaneAngle", MIN_ANGLE);
      TestingDashboard.getInstance().registerNumber(m_climber, "PIDRotation", "CaneSetpoint", 180); 
      TestingDashboard.getInstance().registerNumber(m_climber, "PIDRotation", "CaneMotorSpeed", .3);
      TestingDashboard.getInstance().registerNumber(m_climber, "ExtensionMotorCurrents", "LeftCaneMotorCurrent", 0);
      TestingDashboard.getInstance().registerNumber(m_climber, "ExtensionMotorCurrents", "RightCaneMotorCurrent", 0);
      TestingDashboard.getInstance().registerNumber(m_climber, "ExtensionMotorCurrents", "MaxNumCurrentValues", MOTOR_CURRENT_INITIAL_CAPACITY);
    }
    return m_climber;
  }

  public void setExtensionEncoderConversionFactor(double conversionFactor) {

    if(m_leftCaneEncoder.setPositionConversionFactor(conversionFactor) != REVLibError.kOk){ 
      System.out.println("Could not set position conversion factor on left climber extension encoder");
    }
  
    if(m_rightCaneEncoder.setPositionConversionFactor(conversionFactor) != REVLibError.kOk){
      System.out.println("Could not set position conversion factor on right climber extension encoder");
    } 
  }

  void updateMotorCurrentAverages() {

    m_max_num_current_values = (int)TestingDashboard.getInstance().getNumber(m_climber, "MaxNumCurrentValues");
    // Extention motors
    double leftCaneMotorCurrent = m_leftCaneMotor.getOutputCurrent();
    double rightCaneMotorCurrent = m_rightCaneMotor.getOutputCurrent();
    m_left_cane_motor_current_values.add(leftCaneMotorCurrent + leftCaneMotorCurrent);
    m_right_cane_motor_current_values.add(rightCaneMotorCurrent + rightCaneMotorCurrent);
    TestingDashboard.getInstance().updateNumber(m_climber, "LeftCaneMotorCurrent", leftCaneMotorCurrent);
    TestingDashboard.getInstance().updateNumber(m_climber, "RightCaneMotorCurrent", rightCaneMotorCurrent);

    // Trim current buffers until they contain the correct number of entries.
    // Old entries are removed first.
    while (m_left_cane_motor_current_values.size() > m_max_num_current_values) {
      m_left_cane_motor_current_values.remove(0);
    }
    while (m_right_cane_motor_current_values.size() > m_max_num_current_values) {
      m_right_cane_motor_current_values.remove(0);
    }
    
  }

  public static double arrayListAverage(ArrayList<Double> arrayList) {
    double sum = 0;
    for (int i = 0; i < arrayList.size(); i++) {
      sum += arrayList.get(i);
    }
    return sum / arrayList.size();
  }

  public double getTotalAverageLeftCaneMotorCurrent() {
    return arrayListAverage(m_left_cane_motor_current_values);
  }

  public double getTotalAverageRightCaneMotorCurrent() {
    return arrayListAverage(m_right_cane_motor_current_values);
  }

  public void enableBrakeMode() {
    m_rightCaneMotor.setIdleMode(IdleMode.kBrake);
    m_leftCaneMotor.setIdleMode(IdleMode.kBrake);
  }

  public void disableBrakeMode() {
    m_rightCaneMotor.setIdleMode(IdleMode.kCoast);
    m_leftCaneMotor.setIdleMode(IdleMode.kCoast);
  }

  public double getLeftCaneHeight() {
    m_currentLeftCaneHeight = m_leftCaneEncoder.getPosition();
    return m_currentLeftCaneHeight;
  }

  public double getRightCaneHeight() {
    m_currentRightCaneHeight = m_rightCaneEncoder.getPosition();
    return m_currentRightCaneHeight;
  }

  public DigitalInput getLeftSwitch() {
    return leftSwitch;
  }

  public DigitalInput getRightSwitch() {
    return rightSwitch;
  }

  public void tankCane(double leftSpeed, double rightSpeed) {
    m_leftCaneMotor.set(leftSpeed);
    m_rightCaneMotor.set(rightSpeed);
  }

  public void extendCane(double speed) {
    m_leftCaneMotor.set(-speed);
    m_rightCaneMotor.set(-speed);
  }

  public CANSparkMax getLeftCaneMotor() {
    return m_leftCaneMotor;
  }

  public CANSparkMax getRightCaneMotor() {
    return m_rightCaneMotor;
  }

  public RelativeEncoder getLeftCaneEncoder() {
	  return m_leftCaneEncoder;
  }

  public RelativeEncoder getRightCaneEncoder() {
	  return m_rightCaneEncoder;
  }

  public Encoder getCaneRotationEncoder() {
	  return m_caneRotationEncoder;
  }

  public void rotateLeftCane(double speed) {
    m_leftCaneRotationMotor.set(VictorSPXControlMode.PercentOutput, -speed);
  }

  public void rotateRightCane(double speed) {
    m_rightCaneRotationMotor.set(VictorSPXControlMode.PercentOutput, speed);
  }

  public void rotateBothCanes(double speed) {
    m_caneRotateSpeed = speed;
    rotateLeftCane(speed);
    rotateRightCane(speed);
  }

  public double getRotationAngle() {
    double volt = m_potentiometer.getVoltage();
    double angle = MIN_ANGLE + (volt - POTENTIOMETER_MIN) * DEGREES_PER_VOLT;
    return angle;
  }


  public double getCurrentLeftCaneHeight() {
    return m_leftCaneEncoder.getPosition() * GEAR_CIRCUMFERENCE_IN_INCHES;
  }

  public double getCurrentRightCaneHeight() {
    return m_rightCaneEncoder.getPosition() * GEAR_CIRCUMFERENCE_IN_INCHES;
  }

  

  @Override
  public void periodic() {
    if (Constants.CLIMBER_PERIODIC_ENABLE) {
      // This method will be called once per scheduler run
      TestingDashboard.getInstance().updateNumber(m_climber, "CaneAngle", getRotationAngle());
      TestingDashboard.getInstance().updateNumber(m_climber, "CaneMotorSpeed", m_caneRotateSpeed);
      TestingDashboard.getInstance().updateNumber(m_climber, "CurrentLeftExtensionSpeed", m_leftCaneMotor.get());
      TestingDashboard.getInstance().updateNumber(m_climber, "CurrentRightExtensionSpeed", m_rightCaneMotor.get());
      TestingDashboard.getInstance().updateNumber(m_climber, "CurrentLeftEncoderPostition", m_leftCaneEncoder.getPosition());
      TestingDashboard.getInstance().updateNumber(m_climber, "CurrentRightEncoderPostition", m_rightCaneEncoder.getPosition());
      TestingDashboard.getInstance().updateNumber(m_climber, "CurrentLeftEncoderVelocity", m_leftCaneEncoder.getVelocity());
      TestingDashboard.getInstance().updateNumber(m_climber, "CurrentRightEncoderVelocity", m_rightCaneEncoder.getVelocity());
      TestingDashboard.getInstance().updateNumber(m_climber, "CurrentRotationEncoderRate", m_caneRotationEncoder.getRate());
      updateMotorCurrentAverages();
    }

  }
}

