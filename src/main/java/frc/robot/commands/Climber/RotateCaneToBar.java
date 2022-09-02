// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.sensors.BarDetectionSensorHelper;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ClimberCaneRotation;
import frc.robot.testingdashboard.TestingDashboard;

public class RotateCaneToBar extends CommandBase {
  private Climber m_climber;
  private ClimberCaneRotation m_climberCaneRotation;
  private double m_caneSpeed;
  private boolean m_parameterized;
  private static final double START_DELAY = .25; // .25 second start delay to test if encoder rate is 0
  private static final double RATE_TOLERANCE = 5; // Used to detect if cane has essentially stopped moving
  private Timer m_timer;

  /** Creates a new RotateCaneToBar. */
  public RotateCaneToBar(double caneSpeed, boolean parameterized) {
    m_climber = Climber.getInstance();
    m_climberCaneRotation = ClimberCaneRotation.getInstance();
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_climberCaneRotation);
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
    RotateCaneToBar cmd = new RotateCaneToBar(Climber.INITIAL_CANE_ROTATION_SPEED, false);
    TestingDashboard.getInstance().registerCommand(climber, "CaneRotation", cmd);
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
      m_caneSpeed = TestingDashboard.getInstance().getNumber(m_climber, "RotationSpeed");
    }
    m_climber.rotateBothCanes(m_caneSpeed);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    stopTimer();
    m_climber.rotateBothCanes(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    Encoder leftEncoder = m_climber.getCaneRotationEncoder();
    // TODO: figure out which tolerance is approprate for the command using the rate posted on TestingDashboard
    double encoderRate = leftEncoder.getRate();
    if (m_timer.hasElapsed(START_DELAY)) {
      if (encoderRate > 0 - RATE_TOLERANCE && encoderRate < 0 + RATE_TOLERANCE) {
        return true;
      }
    }
    return false;
  }
}
