/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.testingdashboard.TestingDashboard;
import frc.robot.input.AttackThree;
import frc.robot.input.AttackThree.AttackThreeAxis;
import frc.robot.subsystems.Drive;

/**
 * A Default Drive commmand that passes joystick input to a TankDrive drivetrain.
 */
public class TankDrive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Drive m_drive;
  private final AttackThreeAxis yAxis = AttackThreeAxis.kY;
  private static OI oi;
  private int counter;

  /**
   * Creates a new TankDrive.
   *
   * @param drive the subsystem used by this command.
   */
  public TankDrive() {
    m_drive = Drive.getInstance();
    
    counter = 0;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drive);
  }

  public static void registerWithTestingDashboard() {
    Drive drive = Drive.getInstance();
    TankDrive cmd = new TankDrive();
    TestingDashboard.getInstance().registerCommand(drive, "Basic", cmd);
  }

  // Called when the command is initially scheduled. (Unused)
  @Override
  public void initialize() {
    oi = OI.getInstance();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // NOTE: Forward on the left and right sticks is negative
    //       Backwards is positive, hence the inversion below
    AttackThree leftJoystick = oi.getLeftStick();
    AttackThree rightJoystick = oi.getRightStick();
    double leftJoystickSpeed = -leftJoystick.getAxis(yAxis);
    double rightJoystickSpeed = -rightJoystick.getAxis(yAxis);
    
    // Reverses the direction of the drive train upon pressing 2 on the right joystick
    if (rightJoystick.getRawButtonPressed(2)) {
      counter++;
    }
    if (counter % 2 == 1) {
      leftJoystickSpeed = rightJoystick.getAxis(yAxis);
      rightJoystickSpeed = leftJoystick.getAxis(yAxis);
    } else {
      leftJoystickSpeed = -leftJoystick.getAxis(yAxis);
      rightJoystickSpeed = -rightJoystick.getAxis(yAxis);
    }
    m_drive.tankDrive(leftJoystickSpeed, rightJoystickSpeed);
  }

  // Called once the command ends or is interrupted. (Unused)
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  // Default command so will never finish running.
  @Override
  public boolean isFinished() {
    return false;
  }
}
