// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.testingdashboard.TestingDashboard;

public class Auto extends SubsystemBase {
  private static Auto m_auto;
  /** Creates a new Auto. */
  private Auto() {}

  public static Auto getInstance() {
    if (m_auto == null) {
      m_auto = new Auto();
      TestingDashboard.getInstance().registerSubsystem(m_auto, "Auto");
      TestingDashboard.getInstance().registerNumber(m_auto, "TimeConstants", "DefaultAutoWaitTime", Constants.DEFAULT_AUTO_WAIT_TIME);
    }
    return m_auto;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
