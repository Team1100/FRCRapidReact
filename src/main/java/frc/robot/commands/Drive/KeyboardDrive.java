// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.input.KeyboardBox;
import frc.robot.input.XboxController;
import frc.robot.input.XboxController.XboxAxis;
import frc.robot.subsystems.Drive;
import frc.robot.testingdashboard.TestingDashboard;


public class KeyboardDrive extends CommandBase {
  /** Creates a new KeyboardDrive. */
  private final Drive m_drive;
  private static final boolean SQUARE_INPUTS = false;
  private static OI m_oi;
  private static KeyboardBox m_keyboardBox;
  private double m_speed;
  private double m_rotation;
  
  public KeyboardDrive() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = Drive.getInstance();
    
    addRequirements(m_drive);    
  }

  public KeyboardDrive(double speed, double rotation) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = Drive.getInstance();
    addRequirements(m_drive);

    m_speed = speed;
    m_rotation = rotation;
  }

  public static void registerWithTestingDashboard() {
    Drive drive = Drive.getInstance();
    KeyboardDrive cmd = new KeyboardDrive();
    TestingDashboard.getInstance().registerCommand(drive, "Experimental", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_oi = OI.getInstance();
    m_keyboardBox = m_oi.getKeyboardBox();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.arcadeDrive(m_speed, m_rotation, SQUARE_INPUTS);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
