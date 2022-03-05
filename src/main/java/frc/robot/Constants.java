// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    // Defines climber sensor constants
    public static final int NO_SENSOR = 0;
    public static final int MOTOR_CURRENT = 1;
    public static final int LIMIT_SWITCH = 2;

    // Defines constants associated with sensors
    // 15 Amps for 1 second at a speed of 0.3 indicates that contact with
    // a bar has been detected
    public static final double MOTOR_CURRENT_THRESHOLD = 15; // Amps
    public static final double MOTOR_CURRENT_TIME_WINDOW = 1; // Seconds
    public static final double DRIVE_TO_BAR_SPEED = 0.3; // percentage
    public static final double MOTOR_CURRENT_TIME_THRESHOLD = 1; //seconds
}
