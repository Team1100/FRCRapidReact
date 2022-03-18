// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.commands.Auto.Wait;
import frc.robot.commands.Climber.CaneExtendDistance.CanesToExtend;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.testingdashboard.TestingDashboard;



public class ClimbStatefully extends CommandBase {
  enum State {
    INIT,
    DRIVE_TO_BAR,
    RETRACT_CANE,
    REACH_FOR_NEXT_BAR,
    RELEASE_AND_STABALIZE,
    STOP
  }

  private static final double DRIVE_TO_BAR_DISTANCE = 24; // in inches
  private static final double DRIVE_TO_BAR_SPEED = 0.4; // in % power
  private static final double INITIAL_CANE_EXTENSION_DISTANCE = 21; // in inches
  private static final double INTIIAL_CANE_EXTENSION_SPEED = 0.3; // % power
  private static final double UPRIGHT_CANE_ROTATION_SPEED = 0.4; // % power
  private static final double CANE_RETRACTION_DISTANCE = -INITIAL_CANE_EXTENSION_DISTANCE;
  private static final double CANE_RETRACTION_SPEED = INTIIAL_CANE_EXTENSION_SPEED;
  private DriveToBar m_driveToBar;
  private CaneExtendDistance m_raiseCaneToBar;
  private CaneExtendDistance m_retractCane; // Add CaneRetractToBar that uses motor current? This command will lift the robot to the bar and "click in"
  private ConstantSpeedRotateCane m_forceUpright; // Forces cane and claw to stick together while lifting up
  private State m_state;
  private boolean m_isFinished;

  /** Creates a new ClimbStatefully. */
  public ClimbStatefully() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_driveToBar = new DriveToBar(DRIVE_TO_BAR_DISTANCE, DRIVE_TO_BAR_SPEED, Constants.MOTOR_CURRENT, true);
    m_raiseCaneToBar = new CaneExtendDistance(CanesToExtend.CANE_BOTH, INITIAL_CANE_EXTENSION_DISTANCE, INTIIAL_CANE_EXTENSION_SPEED, true);
    m_retractCane = new CaneExtendDistance(CanesToExtend.CANE_BOTH, CANE_RETRACTION_DISTANCE, CANE_RETRACTION_SPEED, true);
    m_forceUpright = new ConstantSpeedRotateCane(UPRIGHT_CANE_ROTATION_SPEED, true);
    m_state = State.INIT;
    m_isFinished = false;
  }

  //Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    Climber climber = Climber.getInstance();
    ClimbStatefully cmd = new ClimbStatefully();
    TestingDashboard.getInstance().registerCommand(climber, "Basic", cmd);
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
        m_state = State.DRIVE_TO_BAR;
        break;
      case DRIVE_TO_BAR:
        m_raiseCaneToBar.schedule();
        m_driveToBar.schedule();
        if (m_driveToBar.isFinished() && m_raiseCaneToBar.isFinished()) {
          m_state = State.RETRACT_CANE;
        }
        break;
      case RETRACT_CANE:
        m_forceUpright.schedule();
        m_retractCane.schedule();
        if (m_retractCane.isFinished()) {
          m_forceUpright.cancel();
          m_state = State.STOP;
        }
        break;
      case REACH_FOR_NEXT_BAR:
        break;
      case RELEASE_AND_STABALIZE:
        break;
      case STOP:
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
