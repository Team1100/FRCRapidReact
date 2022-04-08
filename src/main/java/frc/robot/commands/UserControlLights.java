// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.time.OffsetDateTime;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.robot.subsystems.Climber;

public class UserControlLights extends CommandBase {
  private int m_lightOption;
  private Solenoid m_lightsPower;
  private Solenoid m_lightsRed;
  private Solenoid m_lightsBlue;
  private Climber m_climber;
  /** Creates a new UserControlLights. 
   * 0 is off
   * 1 is blue
   * 2 is red
   * 3 is purple (both)
  */
  public UserControlLights(int lightOption) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_lightOption = lightOption;
    m_climber = Climber.getInstance();
    m_lightsPower = m_climber.getLightPowers();
    m_lightsBlue = m_climber.getLightBlue();
    m_lightsRed = m_climber.getLightRed();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (m_lightOption == Constants.LIGHTS_OFF) {
      m_lightsPower.set(false);
      m_lightsBlue.set(false);
      m_lightsRed.set(false);
    } else if (m_lightOption == Constants.LIGHTS_BLUE) {
      m_lightsPower.set(true);
      m_lightsBlue.set(true);
      m_lightsRed.set(false);
    } else if (m_lightOption == Constants.LIGHTS_RED) {
      m_lightsPower.set(true);
      m_lightsBlue.set(false);
      m_lightsRed.set(true);
    } else if (m_lightOption == Constants.LIGHTS_PURPLE) {
      m_lightsPower.set(true);
      m_lightsBlue.set(true);
      m_lightsRed.set(true);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
