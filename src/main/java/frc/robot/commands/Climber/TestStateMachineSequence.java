// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.Auto.Wait;
import frc.robot.commands.Drive.DriveDistance;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.testingdashboard.TestingDashboard;

public class TestStateMachineSequence extends CommandBase {
  enum State {
    INIT,
    FORWARDS,
    WAIT_1,
    BACKWARDS,
    WAIT_2,
    DONE
  }
  private DriveDistance m_driveForwards;
  private DriveDistance m_driveBackwards;
  private Wait m_waitThreeSeconds;
  private State m_state;
  private boolean m_isFinished;
  /** Creates a new TestStateMachineSequence. */
  public TestStateMachineSequence() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_driveForwards = new DriveDistance(12, 0.4, true);
    m_driveBackwards = new DriveDistance(-12, 0.4, true);
    m_waitThreeSeconds = new Wait(3, true);
    m_state = State.INIT;
    m_isFinished = false;
  }

  //Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    Climber climber = Climber.getInstance();
    TestStateMachineSequence cmd = new TestStateMachineSequence();
    TestingDashboard.getInstance().registerCommand(climber, "TestCommands", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_state = State.INIT;
    m_isFinished = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    switch (m_state) {
      case INIT:
        Drive.getInstance().setIdleMode(IdleMode.kBrake);
        m_state = State.FORWARDS;
        break;
      case FORWARDS:
        m_driveForwards.schedule();
        if (m_driveForwards.isFinished()) {
          m_state = State.WAIT_1;
        }
        break;
      case WAIT_1:
        m_waitThreeSeconds.schedule();
        if (m_waitThreeSeconds.isFinished()) {
          m_state = State.BACKWARDS;
        }
        break;
      case BACKWARDS:
        m_driveBackwards.schedule();
        if (m_driveBackwards.isFinished()) {
          m_state = State.WAIT_2;
        }
        break;
      case WAIT_2:
        m_waitThreeSeconds.schedule();
        if (m_waitThreeSeconds.isFinished()) {
          m_state = State.DONE;
        }
        break;
      case DONE:
        m_isFinished = true;
        break;
      default:
        break;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Drive.getInstance().setIdleMode(IdleMode.kCoast);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_isFinished;
  }
}
