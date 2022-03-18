// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberCaneRotation extends SubsystemBase {
  private static ClimberCaneRotation m_climberCaneRotation;
  /** Creates a new ClimberCaneRotation. */
  public ClimberCaneRotation() {}

  public static ClimberCaneRotation getInstance() {
    if (m_climberCaneRotation == null) {
      m_climberCaneRotation = new ClimberCaneRotation();
    }
    return m_climberCaneRotation;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
