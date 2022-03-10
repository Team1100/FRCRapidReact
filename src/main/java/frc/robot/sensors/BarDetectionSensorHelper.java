// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.sensors;

import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;

/** Add your docs here. */
public class BarDetectionSensorHelper {
    private Drive m_drive;
    private Climber m_climber;

    public BarDetectionSensorHelper() {
        m_drive = Drive.getInstance();
        m_climber = Climber.getInstance();
    }
    public boolean leftSensorActivated(int sensor) {
      boolean ret = false;
        switch (sensor) {
          case Constants.NO_SENSOR:
            break;
          case Constants.MOTOR_CURRENT:
            if (m_drive.getTotalAverageLeftMotorCurrent() > Constants.MOTOR_CURRENT_THRESHOLD) {
                ret = true;

            } else {
                ret = false;
            }
            break;
          case Constants.LIMIT_SWITCH:
          // Note that "true" indicates no contact, false indicates contact for the
          // limit switch
            if (!m_climber.getLeftSwitch().get()) {
              ret = true;
            } else {
              ret = false;
            }
            break;
          default:
            ret = false;
        }
        return ret;
      }
    
      public boolean rightSensorActivated(int sensor) {
        boolean ret = false;
        switch (sensor) {
          case Constants.NO_SENSOR:
            break;
          case Constants.MOTOR_CURRENT:
            if (m_drive.getTotalAverageRightMotorCurrent() > Constants.MOTOR_CURRENT_THRESHOLD) {
                ret = true;
            } else {
                ret = false;
            }
            break;
          case Constants.LIMIT_SWITCH:
            // Note that "true" indicates no contact, false indicates contact for the
            // limit switch
            if (!m_climber.getRightSwitch().get()) {
              ret = true;
            } else {
              ret = false;
            }
        }
        return ret;
      }

}
