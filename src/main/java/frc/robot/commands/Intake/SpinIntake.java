// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.input.XboxController.XboxAxis;
import frc.robot.OI;
import frc.robot.subsystems.Intake;
import frc.robot.testingdashboard.TestingDashboard;

public class SpinIntake extends CommandBase {

  private Intake m_intake;
  private OI m_oi;
  private double m_speed;
  private boolean m_parameterized;

  /** Creates a new SpinIntakeForward. */
  public SpinIntake(double speed, boolean forward) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_intake = Intake.getInstance();
    addRequirements(m_intake);
    m_speed = speed;
  }

    //Register with TestingDashboard
    public static void registerWithTestingDashboard() {
      Intake intake = Intake.getInstance();
      SpinIntake cmd = new SpinIntake(Intake.DEFAULT_ROLLER_SPEED, true);
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

    if (!m_parameterized) {
      m_speed = TestingDashboard.getInstance().getNumber(m_intake, "RollerSpeed");
    }

    //Drives the ball intake with the right trigger.
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
