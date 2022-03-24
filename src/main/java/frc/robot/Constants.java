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
    // Controls which software should run based on hardware availability
    public static final boolean HW_AVAILABLE_PNEUMATIC_CONTROL_MODULE = true;
    public static final boolean HW_ENABLE_SHOOTER = true;
    public static final boolean HW_ENABLE_CONVEYOR = true;
    public static final boolean HW_ENABLE_DRIVE = true;

    // Defines Shooter command constants
    public static final double DEFAULT_SHOOTER_SPIN_UP_TIME = 0.5; // seconds
    public static final double DEFAULT_SHOOTER_SPEED = 0.35;
    public static final double SHOOTER_SPEED_HIGH = 0.60;
    public static final double SHOOTER_SPEED_LOW = 0.25;

    // Defines Intake command constants
    public static final double DEFAULT_INTAKE_SPEED = 0.33; // positive goes into robot, negative expels

    // Defines Auto command constants
    public static final int DEFAULT_AUTO_WAIT_TIME = 3;

    // Defines climber sensor constants
    public static final int NO_SENSOR = 0;
    public static final int MOTOR_CURRENT = 1;
    public static final int LIMIT_SWITCH = 2;

    // Defines OI deadband constants
    public static final double XBOX_DEADBAND_LIMIT = 0.1;

    // Defines constants associated with sensors
    // 15 Amps for 1 second at a speed of 0.3 indicates that contact with
    // a bar has been detected
    public static final double MOTOR_CURRENT_THRESHOLD = 20; // Amps
    public static final double MOTOR_CURRENT_TIME_WINDOW = 1; // Seconds
    public static final double DRIVE_TO_BAR_SPEED = 0.3; // percentage
    public static final double MOTOR_CURRENT_TIME_THRESHOLD = 1; //seconds
    public static final double CANE_MOTOR_CURRENT_THRESHOLD = 15; // Amps

    // Subsystem periodic loops
    public static final boolean DRIVE_PERIODIC_ENABLE = true;
    public static final boolean CLIMBER_PERIODIC_ENABLE = true;
    public static final boolean SHOOTER_PERIODIC_ENABLE = true;

    // Joysticks enabled
    public static final boolean ATTACK_THREE_ENABLE = false;
    public static final boolean BUTTON_BOX_ENABLE = false;
    public static final boolean KEYBOARD_BOX_ENABLE = false;
    public static final boolean XBOX_CONTROLLER_DRIVER_ENABLE = true;
    public static final boolean XBOX_CONTROLLER_OPERATOR_ENABLE = true;
}
