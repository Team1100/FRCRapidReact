// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Climber;
import frc.robot.testingdashboard.TestingDashboard;
import frc.robot.input.XboxController;

public class TankRotateCane extends CommandBase {
  /** Creates a new TankRotateCane. */
  Climber m_climber;
  OI m_oi;
  private double m_caneSpeed;

  public TankRotateCane() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climber = Climber.getInstance();
    addRequirements(m_climber);
    m_caneSpeed = Climber.INITIAL_CANE_ROTATION_SPEED;
  }

  public static void registerWithTestingDashboard() {
    Climber climber = Climber.getInstance();
    TankRotateCane cmd = new TankRotateCane();
    TestingDashboard.getInstance().registerCommand(climber, "CaneRotation", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_oi = OI.getInstance();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double rotationSpeed = 0;
    XboxController xbox = m_oi.getXbox();
    if (xbox.getDPad().getRight().get()) {
      rotationSpeed = m_caneSpeed;
    }
    else if (xbox.getDPad().getLeft().get()) {
      rotationSpeed = -m_caneSpeed;
    }
    m_climber.rotateBothCanes(rotationSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climber.rotateBothCanes(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
