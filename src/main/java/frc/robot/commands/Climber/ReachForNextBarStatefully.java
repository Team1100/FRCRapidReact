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

public class ReachForNextBarStatefully extends CommandBase {
  enum State {
    INIT,
    SCHEDULE_EXTEND_1,
    EXTEND_1,
    SCHEDULE_ROTATE_BACK,
    ROTATE_BACK,
    SCHEDULE_EXTEND_2,
    EXTEND_2,
    SCHEDULE_ROTATE_TO_BAR,
    ROTATE_TO_BAR,
    DONE
  }
  static final double CANE_EXTENSION_SPEED = .5;
    static final double CANE_HEIGHT = 12;
    static final double CANE_ROTATION_SPEED = .2;


  private CaneExtendDistance m_liftOffBar;
  private RotateCaneToBar m_rotateBack;
  private CaneExtendDistance m_extendFully;
  private RotateCaneToBar m_rotateToBar;
  
  private boolean m_isFinished;
  private State m_state;
  /** Creates a new ReachForNextBarStatefully. */
  public ReachForNextBarStatefully(double caneExtensionSpeed, double caneHeight,  double caneRotationSpeed) {
    // Use addRequirements() here to declare subsystem dependencies.

    m_liftOffBar = new CaneExtendDistance(caneHeight, caneExtensionSpeed, true);
    m_rotateBack = new RotateCaneToBar(caneRotationSpeed, true);
    m_extendFully = new CaneExtendDistance(caneHeight, caneExtensionSpeed, true);
    m_rotateToBar = new RotateCaneToBar(caneRotationSpeed, true);

    m_state = State.INIT;
    m_isFinished = false;
  }

  //Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    Climber climber = Climber.getInstance();
    ReachForNextBarStatefully cmd = new ReachForNextBarStatefully(CANE_EXTENSION_SPEED, CANE_HEIGHT, CANE_ROTATION_SPEED);
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
        m_state = State.SCHEDULE_EXTEND_1;
        break;
      case SCHEDULE_EXTEND_1:
        m_liftOffBar.schedule();
        m_state = State.EXTEND_1;
        break;
      case EXTEND_1:
        if (m_liftOffBar.isFinished())
          m_state = State.SCHEDULE_ROTATE_BACK;
        break;
      case SCHEDULE_ROTATE_BACK:
        m_rotateBack.schedule();
        m_state = State.ROTATE_BACK;
        break;
      case ROTATE_BACK:
        if (m_rotateBack.isFinished())
          m_state = State.SCHEDULE_EXTEND_2;
        break;
      case SCHEDULE_EXTEND_2:
        m_extendFully.schedule();
        m_state = State.EXTEND_2;
        break;
      case EXTEND_2:
        if (m_extendFully.isFinished())
          m_state = State.SCHEDULE_ROTATE_TO_BAR;
        break;
      case SCHEDULE_ROTATE_TO_BAR:
        m_rotateToBar.schedule();
        m_state = State.ROTATE_TO_BAR;
        break;
      case ROTATE_TO_BAR:
        if (m_rotateToBar.isFinished())
          m_state = State.DONE;
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
