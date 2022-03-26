// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber.CaneExtension;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ClimberCaneExtension;
import frc.robot.testingdashboard.TestingDashboard;

public class ExtendCaneToLimit extends CommandBase {
  private Climber m_climber;
  private ClimberCaneExtension m_climberCaneExtension;
  private double m_caneSpeed; 
  private boolean m_parameterized;

  private static final double START_DELAY = 1; // 1 second start delay to test if encoder rate is 0
  private static final double RATE_TOLERANCE = 50; // Used to detect if cane has essentially stopped moving
  private Timer m_timer;
  
  /** Creates a new ExtendCaneToLimit. */
  public ExtendCaneToLimit(double caneSpeed, boolean parameterized) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climber = Climber.getInstance();
    m_climberCaneExtension = ClimberCaneExtension.getInstance();
    addRequirements(m_climberCaneExtension);
    m_caneSpeed = caneSpeed;
    m_parameterized = parameterized;
    m_timer = new Timer();
  }

  
  void initializeTimer() {
    m_timer.reset();
    m_timer.start();
  }

  void stopTimer() {
    m_timer.stop();
  }

  public static void registerWithTestingDashboard() {
    Climber climber = Climber.getInstance();
    ExtendCaneToLimit cmd = new ExtendCaneToLimit(Climber.INITIAL_CANE_EXTENTION_SPEED, false);
    TestingDashboard.getInstance().registerCommand(climber, "CaneExtension", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initializeTimer();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_parameterized) {
      m_caneSpeed = TestingDashboard.getInstance().getNumber(m_climber, "ExtensionSpeed");
    }
    m_climber.extendCane(m_caneSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    stopTimer();
    m_climber.extendCane(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    RelativeEncoder leftEncoder = m_climber.getLeftCaneEncoder();
    RelativeEncoder rightEncoder = m_climber.getRightCaneEncoder();
    // TODO: figure out which tolerance is approprate for the command using the rate posted on TestingDashboard
    double leftEncoderVelocity = leftEncoder.getVelocity();
    double rightEncoderVelocity = rightEncoder.getVelocity();
    if (m_timer.hasElapsed(START_DELAY)) {
      if (leftEncoderVelocity > 0 - RATE_TOLERANCE && leftEncoderVelocity < 0 + RATE_TOLERANCE) {
        if (rightEncoderVelocity > 0 - RATE_TOLERANCE && rightEncoderVelocity < 0 + RATE_TOLERANCE) {
          return true;
        }
      }
    }
    return false;
  }
}
