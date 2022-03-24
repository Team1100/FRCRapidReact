// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.input.XboxController.XboxAxis;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.subsystems.Intake;
import frc.robot.testingdashboard.TestingDashboard;

public class UserSpinIntake extends CommandBase {

  private Intake m_intake;
  private OI m_oi;

  /** Creates a new UserSpinIntakeForward. */
  public UserSpinIntake() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intake = Intake.getInstance();
    addRequirements(m_intake);
  }

    //Register with TestingDashboard
    public static void registerWithTestingDashboard() {
      Intake intake = Intake.getInstance();
      UserSpinIntake cmd = new UserSpinIntake();
      TestingDashboard.getInstance().registerCommand(intake, "Basic", cmd);
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_oi = OI.getInstance();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = 0;
    if (Constants.XBOX_CONTROLLER_OPERATOR_ENABLE) {
      //Drives the ball intake with the left and right triggers.
      double speedRight = m_oi.getOperatorXboxController().getAxis(XboxAxis.kRightTrigger);
      double speedLeft = m_oi.getOperatorXboxController().getAxis(XboxAxis.kLeftTrigger);

      if (speedRight > 0) {
        speed = speedRight / 2;
      } else if (speedLeft > 0) {
        speed = -speedLeft / 2;
      }
    }
    m_intake.spinIntakeRoller(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.spinIntakeRoller(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
