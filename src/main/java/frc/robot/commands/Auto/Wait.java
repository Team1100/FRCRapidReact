// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Auto;
import frc.robot.testingdashboard.TestingDashboard;

public class Wait extends CommandBase {
  private Auto m_auto;
  private Timer m_timer;
  private boolean m_timer_running = false;
  private double m_time;
  private boolean m_parametrized;
  /** Creates a new Wait. */
  public Wait(double waitTime, boolean parametrized) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_auto = Auto.getInstance();
    m_timer = new Timer();
    m_timer_running = false;
    m_time = waitTime;
    m_parametrized = parametrized;
  }

  public static void registerWithTestingDashboard() {
    Auto auto = Auto.getInstance();
    double time = Constants.DEFAULT_AUTO_WAIT_TIME;
    Wait cmd = new Wait(time, false);
    TestingDashboard.getInstance().registerCommand(auto, "Time", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (!m_parametrized) {
      m_time = TestingDashboard.getInstance().getNumber(m_auto, "DefaultAutoWaitTime");
    }
    m_timer.reset();
    m_timer.start();
    m_timer_running = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_timer.stop();
    m_timer_running = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_timer_running) {
      return m_timer.hasElapsed(m_time);
    } else {
      return false;
    }
  }
}
