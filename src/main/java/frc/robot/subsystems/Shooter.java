// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.testingdashboard.TestingDashboard;

public class Shooter extends SubsystemBase {
  private static Shooter m_shooter;

  /** Creates a new Shooter. */
  public Shooter() {}

  public static Shooter getInstance() {
    if (m_shooter == null) {
      m_shooter = new Shooter();
      TestingDashboard.getInstance().registerSubsystem(m_shooter, "Vision");
    }
    return m_shooter;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
