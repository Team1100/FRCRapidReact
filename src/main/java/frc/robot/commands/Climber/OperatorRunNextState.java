// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.Climber.Sequences.ClimbStatefully;
import frc.robot.subsystems.Climber;
import frc.robot.testingdashboard.TestingDashboard;

public class OperatorRunNextState extends CommandBase {

  private Climber m_climber;

  /** Creates a new SpinClimber. */
  public OperatorRunNextState() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climber = Climber.getInstance();
    }

  //Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    Climber climber = Climber.getInstance();
   OperatorRunNextState cmd = new OperatorRunNextState();
    TestingDashboard.getInstance().registerCommand(climber, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_climber.moveToNextState();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
