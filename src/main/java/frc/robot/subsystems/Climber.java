// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.testingdashboard.TestingDashboard;

public class Climber extends SubsystemBase {
  private static Climber m_climber;
  
  /** Creates a new Climber. */
  public Climber() {}

  public static Climber getInstance() {
    if (m_climber == null) {
      m_climber = new Climber();
      TestingDashboard.getInstance().registerSubsystem(m_climber, "Vision");
    }
    return m_climber;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
