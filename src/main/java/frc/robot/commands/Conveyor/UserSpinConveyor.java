// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Conveyor;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.input.XboxController.XboxAxis;
import frc.robot.OI;
import frc.robot.subsystems.Conveyor;
import frc.robot.testingdashboard.TestingDashboard;

public class UserSpinConveyor extends CommandBase {

  private Conveyor m_conveyor;
  private OI m_oi;

  /** Creates a new UserSpinConveyorForward. */
  public UserSpinConveyor() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_conveyor = Conveyor.getInstance();
    addRequirements(m_conveyor);
  }

    //Register with TestingDashboard
    public static void registerWithTestingDashboard() {
      Conveyor conveyor = Conveyor.getInstance();
      UserSpinConveyor cmd = new UserSpinConveyor();
      TestingDashboard.getInstance().registerCommand(conveyor, "Basic", cmd);
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_oi = OI.getInstance();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    //Drives the ball Conveyor with the left and right triggers.
    double speedRight = m_oi.getXbox().getAxis(XboxAxis.kRightTrigger);
    double speedLeft = m_oi.getXbox().getAxis(XboxAxis.kLeftTrigger);
    double speed = 0;
    if(speedRight > 0) {
      speed = speedRight / 2;
    }
    else if(speedLeft > 0) {
      speed = -speedLeft / 2;
    }
    else {
      speed = 0;
    }
    m_conveyor.spinConveyor(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_conveyor.spinConveyor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
