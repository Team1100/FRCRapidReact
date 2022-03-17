// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.input.XboxController;
import frc.robot.input.XboxController.XboxAxis;
import frc.robot.subsystems.Climber;
import frc.robot.testingdashboard.TestingDashboard;

public class UserOperateCane extends CommandBase {
  private Climber m_climber;
  private OI m_oi;
  private double m_caneExtensionSpeed;
  private double m_caneRotationSpeed;

  /** Creates a new UserOperateCane. */
  public UserOperateCane() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climber = Climber.getInstance();
    addRequirements(m_climber);
    m_caneExtensionSpeed = Climber.INITIAL_CANE_EXTENTION_SPEED;
    m_caneRotationSpeed = Climber.INITIAL_CANE_ROTATION_SPEED;
  }

  public static void registerWithTestingDashboard() {
    Climber climber = Climber.getInstance();
    UserOperateCane cmd = new UserOperateCane();
    TestingDashboard.getInstance().registerCommand(climber, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_caneExtensionSpeed = Climber.INITIAL_CANE_EXTENTION_SPEED;
    m_caneRotationSpeed = Climber.INITIAL_CANE_ROTATION_SPEED;
    m_climber.tankCane(0, 0);
    m_oi = OI.getInstance();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_caneExtensionSpeed = TestingDashboard.getInstance().getNumber(m_climber, "ExtensionSpeed");
    m_caneRotationSpeed = TestingDashboard.getInstance().getNumber(m_climber, "RotationSpeed");

    extendCane();
    rotateCane();
  }

  private void extendCane() {
    XboxController xbox = m_oi.getXbox();
    
    double extensionSpeed = 0;
    if (xbox.getDPad().getDown().get()) {
      extensionSpeed = m_caneExtensionSpeed;
    } else if (xbox.getDPad().getUp().get()) {
      extensionSpeed = -m_caneExtensionSpeed;
    }

    m_climber.extendCane(extensionSpeed);
  }

  private void rotateCane() {
    double rotationSpeed = 0;
    XboxController xbox = m_oi.getXbox();
    if (xbox.getDPad().getRight().get()) {
      rotationSpeed = m_caneRotationSpeed;
    } else if (xbox.getDPad().getLeft().get()) {
      rotationSpeed = -m_caneRotationSpeed;
    }
    m_climber.rotateBothCanes(rotationSpeed);
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
