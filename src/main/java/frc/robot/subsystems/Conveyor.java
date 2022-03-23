// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.testingdashboard.TestingDashboard;

public class Conveyor extends SubsystemBase {
  private static Conveyor m_conveyor;
  private CANSparkMax m_conveyorRight;
  private CANSparkMax m_conveyorLeft;
  private DoubleSolenoid m_gatePiston;

  /** Creates a new Conveyor. */
  private Conveyor() {
    if (Constants.HW_ENABLE_CONVEYOR == true){
      m_conveyorLeft = new CANSparkMax(RobotMap.C_LEFT_MOTOR,MotorType.kBrushless);
      m_conveyorRight = new CANSparkMax(RobotMap.C_RIGHT_MOTOR,MotorType.kBrushless);
    }
    if (Constants.HW_AVAILABLE_PNEUMATIC_CONTROL_MODULE) {
      m_gatePiston = new DoubleSolenoid(RobotMap.PCM_CAN, PneumaticsModuleType.CTREPCM, RobotMap.C_GATE_PISTON_PORT1, RobotMap.C_GATE_PISTON_PORT2);
    }
  }

  public static Conveyor getInstance() {
    if (m_conveyor == null) {
      m_conveyor = new Conveyor();
      TestingDashboard.getInstance().registerSubsystem(m_conveyor, "Conveyor");
      TestingDashboard.getInstance().registerNumber(m_conveyor, "Inputs", "RollerSpeed", 0.15);
    }
    return m_conveyor;
  }

  public void openGate() {
    if (Constants.HW_AVAILABLE_PNEUMATIC_CONTROL_MODULE) {
      m_gatePiston.set(DoubleSolenoid.Value.kReverse);
    }
  }

  public void closeGate() {
    if (Constants.HW_AVAILABLE_PNEUMATIC_CONTROL_MODULE) {
      m_gatePiston.set(DoubleSolenoid.Value.kForward);
    }
  }
  
  public void spinConveyor(double speed) {
    if (Constants.HW_ENABLE_CONVEYOR == true){
      m_conveyorLeft.set(speed);
      m_conveyorRight.set(-speed);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
