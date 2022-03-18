// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberCaneExtension extends SubsystemBase {
  private static ClimberCaneExtension m_climberCaneExtension;
  /** Creates a new ClimberCaneExtension. */
  public ClimberCaneExtension() {}

  public static ClimberCaneExtension getInstance() {
    if (m_climberCaneExtension == null) {
      m_climberCaneExtension = new ClimberCaneExtension();
    }
    return m_climberCaneExtension;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
