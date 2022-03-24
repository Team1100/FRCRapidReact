// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.OI;
import frc.robot.subsystems.Conveyor;
import frc.robot.testingdashboard.TestingDashboard;

public class SpinConveyorForwards extends CommandBase {

  private Conveyor m_conveyor;
  private boolean m_parameterized;
  private OI m_oi;
  private boolean m_finished;
  private final double F_SPEED = 0.15;
  private final double B_SPEED = -0.15;

  /** Creates a new SpinConveyor. */
  public SpinConveyorForwards() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_conveyor = Conveyor.getInstance();
    m_finished = false;
  }

  //Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    Conveyor conveyor = Conveyor.getInstance();
    SpinConveyorForwards cmd = new SpinConveyorForwards();
    TestingDashboard.getInstance().registerCommand(conveyor, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_oi = OI.getInstance();
    m_finished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_conveyor.spinConveyor(F_SPEED);
    m_finished = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_finished = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_finished;
  }
}
