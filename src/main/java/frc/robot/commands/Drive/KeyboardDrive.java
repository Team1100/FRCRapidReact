// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.input.KeyboardBox;
//import frc.robot.TestingDashboard;
import frc.robot.input.XboxController;
import frc.robot.input.XboxController.XboxAxis;
import frc.robot.subsystems.Drive;
import frc.robot.testingdashboard.TestingDashboard;


public class KeyboardDrive extends CommandBase {
  /** Creates a new KeyboardDrive. */
  private final Drive m_drive;
  private static OI oi;
  private static KeyboardBox m_keyboardBox;
  private double speed;
  private double rotation;
  
  public KeyboardDrive() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = Drive.getInstance();
    
    addRequirements(m_drive);    
  }

  public KeyboardDrive(double spd, double rtt) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = Drive.getInstance();
    
    addRequirements(m_drive);

    speed = spd;
    rotation = rtt;
    
  }

  public static void registerWithTestingDashboard() {
    Drive drive = Drive.getInstance();
    KeyboardDrive cmd = new KeyboardDrive();
    TestingDashboard.getInstance().registerCommand(drive, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    oi = OI.getInstance();
    m_keyboardBox = oi.getKeyboardBox();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    //double rotation = m_keyboardBox.getRot();
    //double speed = m_keyboardBox.getFwd();

    m_drive.keyboardDrive(speed, rotation);
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
