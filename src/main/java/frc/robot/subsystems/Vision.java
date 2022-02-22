// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.testingdashboard.TestingDashboard;

public class Vision extends SubsystemBase {
  private static Vision m_vision;
  /** Creates a new Vision. */
  private Vision() {
  }

  public static Vision getInstance() {
    if (m_vision == null) {
      m_vision = new Vision();
      TestingDashboard.getInstance().registerSubsystem(m_vision, "Vision");
    }
    return m_vision;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
