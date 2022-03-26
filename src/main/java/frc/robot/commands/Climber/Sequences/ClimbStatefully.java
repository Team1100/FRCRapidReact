// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber.Sequences;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.commands.Auto.Wait;
import frc.robot.commands.Climber.ConstantSpeedRotateCane;
import frc.robot.commands.Climber.DriveToBar;
import frc.robot.commands.Climber.CaneExtension.ExtendCaneToLimit;
import frc.robot.commands.Climber.CaneExtension.SmartExtendCaneToLimit;
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

  public static final double DRIVE_TO_BAR_DISTANCE = 24; // in inches
  public static final double DRIVE_TO_BAR_SPEED = 0.4; // in % power
  public static final double INITIAL_CANE_EXTENSION_DISTANCE = 5; // in inches
  public static final double INTIIAL_CANE_EXTENSION_SPEED = 0.45; // % power
  public static final double SLOWER_CANE_EXTENSION_SPEED = 0.3; // % power
  public static final double UPRIGHT_CANE_ROTATION_SPEED = 0.4; // % power
  public static final double CANE_RETRACTION_SPEED = -INTIIAL_CANE_EXTENSION_SPEED;
  public static final double SLOWER_CANE_RETRACTION_SPEED = -SLOWER_CANE_EXTENSION_SPEED;
  public static final double CANE_BACKWARDS_ROTATION_SPEED = 0.15;
  public static final double CANE_FORWARDS_ROTATION_SPEED = 0.4;
  private DriveToBar m_driveToBar;
  private SmartExtendCaneToLimit m_raiseCaneToBar;
  private SmartExtendCaneToLimit m_retractCane; // Add CaneRetractToBar that uses motor current? This command will lift the robot to the bar and "click in"
  private ConstantSpeedRotateCane m_forceUpright; // Forces cane and claw to stick together while lifting up
  private ReachForNextBarStatefully m_reachForNextBarStatefully;
  private Wait m_wait;
  private State m_state;
  private boolean m_isFinished;
  private boolean m_commandsHaveBeenScheduled;
  private int m_cycle;
  private int m_numCycles;
  private boolean m_parametrized;

  /** Creates a new ClimbStatefully. */
  public ClimbStatefully(int numCycles, boolean parametrized) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_driveToBar = new DriveToBar(DRIVE_TO_BAR_DISTANCE, DRIVE_TO_BAR_SPEED, Constants.MOTOR_CURRENT, true);
    m_raiseCaneToBar = new SmartExtendCaneToLimit(INTIIAL_CANE_EXTENSION_SPEED, SLOWER_CANE_EXTENSION_SPEED, true);
    m_retractCane = new SmartExtendCaneToLimit(CANE_RETRACTION_SPEED, SLOWER_CANE_RETRACTION_SPEED, true);
    m_forceUpright = new ConstantSpeedRotateCane(UPRIGHT_CANE_ROTATION_SPEED, true);
    m_reachForNextBarStatefully = new ReachForNextBarStatefully(INTIIAL_CANE_EXTENSION_SPEED, SLOWER_CANE_EXTENSION_SPEED, INITIAL_CANE_EXTENSION_DISTANCE, CANE_FORWARDS_ROTATION_SPEED, CANE_BACKWARDS_ROTATION_SPEED);
    m_wait = new Wait(5, true);
    m_state = State.INIT;
    m_isFinished = false;
    m_commandsHaveBeenScheduled = false;
    m_cycle = 0;
    m_numCycles = numCycles;
    m_parametrized = parametrized;
  }

  // Register with TestingDashboard
  public static void registerWithTestingDashboard() {
    Climber climber = Climber.getInstance();
    int numCycles = Constants.DEFAULT_NUMBER_OF_CLIMB_CYCLES;
    ClimbStatefully cmd = new ClimbStatefully(numCycles, true);
    TestingDashboard.getInstance().registerCommand(climber, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_state = State.RETRACT_CANE;
    m_isFinished = false;
    m_cycle = 0;
    m_commandsHaveBeenScheduled = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_parametrized) {
      m_numCycles = (int)TestingDashboard.getInstance().getNumber(Climber.getInstance(), "NumClimbCycles");
    }
    switch (m_state) {
      case INIT:
        if (!m_commandsHaveBeenScheduled) {
          m_forceUpright.schedule();
          m_raiseCaneToBar.schedule();
          m_commandsHaveBeenScheduled = true;
          Drive.getInstance().setIdleMode(IdleMode.kBrake);
          Climber.getInstance().enableBrakeMode();
        } 
        if (m_raiseCaneToBar.isFinished()) {
          m_state = State.DRIVE_TO_BAR;
          m_commandsHaveBeenScheduled = false;
        }
        break;
      case DRIVE_TO_BAR:
        if (!m_commandsHaveBeenScheduled) {
          m_driveToBar.schedule();
          m_commandsHaveBeenScheduled = true;
        }
        if (m_driveToBar.isFinished()) {
          m_state = State.RETRACT_CANE;
          m_commandsHaveBeenScheduled = false;
        }
        break;
      case RETRACT_CANE:
        if (!m_commandsHaveBeenScheduled) {
          m_retractCane.schedule();
          m_commandsHaveBeenScheduled = true;
        }
        if (m_retractCane.isFinished()) {
          if (m_cycle == m_numCycles) {
            m_state = State.STOP;
            m_commandsHaveBeenScheduled = false;
          } else {
            m_state = State.REACH_FOR_NEXT_BAR;
            m_commandsHaveBeenScheduled = false;
          }
        }
        break;
      case REACH_FOR_NEXT_BAR:
        if (!m_commandsHaveBeenScheduled) {
          m_forceUpright.cancel();
          m_reachForNextBarStatefully.schedule();
          m_commandsHaveBeenScheduled = true;
        }
        if (m_reachForNextBarStatefully.isFinished()) {
          m_state = State.RELEASE_AND_STABALIZE;
          m_commandsHaveBeenScheduled = false;
          m_cycle++;
        }
        break;
        // TODO: add wait before repeating the sequence. Could possibly move contents of RELEASE_AND_STABALIZE state into ReachForNextBarStatefully and then have RELEASE_AND_STABALIZE state simply as a settling period
      case RELEASE_AND_STABALIZE:
        if (!m_commandsHaveBeenScheduled) {
          m_wait.schedule();
          m_commandsHaveBeenScheduled = true;
        }
        
        if (m_wait.isFinished()) {
          if (m_cycle == m_numCycles) {
            m_state = State.STOP;
            m_commandsHaveBeenScheduled = false;
          } else {
            m_state = State.REACH_FOR_NEXT_BAR;
            m_commandsHaveBeenScheduled = false;
          }
        }
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
    Climber.getInstance().disableBrakeMode();
    Climber.getInstance().extendCane(0);
    Climber.getInstance().rotateBothCanes(0);
    m_cycle = 0;
    m_state = State.RETRACT_CANE;
    m_isFinished = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_isFinished;
  }
}
