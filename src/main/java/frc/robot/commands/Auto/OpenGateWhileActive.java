// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Auto;
import frc.robot.subsystems.Conveyor;
import frc.robot.testingdashboard.TestingDashboard;

public class OpenGateWhileActive extends CommandBase {
  Conveyor m_conveyor;
  /** Creates a new OpenGateWhileActive. */
  public OpenGateWhileActive() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_conveyor = Conveyor.getInstance();
  }

  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    OpenGateWhileActive cmd = new OpenGateWhileActive();
    TestingDashboard.getInstance().registerCommand(auto, "AutoPieces", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_conveyor.openGate();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_conveyor.closeGate();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
