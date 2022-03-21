/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.Shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.testingdashboard.TestingDashboard;
import frc.robot.subsystems.Shooter;

public class RunShooter extends CommandBase {
  private Shooter m_shooter;
  private double m_topSpeed;
  private static final double DEF_SPEED = 0.5;
  private boolean m_parameterized = true;

  /**
   * Creates a new RunShooter.
   */
  public RunShooter(double topShooterSpeed, boolean parameterized) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Shooter.getInstance());
    m_shooter = Shooter.getInstance();
    m_topSpeed = topShooterSpeed;
    m_parameterized = parameterized;
  }

  public static void registerWithTestingDashboard() {
    Shooter shooter = Shooter.getInstance();
    double topSpeed = DEF_SPEED;
    RunShooter cmd = new RunShooter(topSpeed, false);
    TestingDashboard.getInstance().registerCommand(shooter, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double topSpeed = m_topSpeed;
    if (!m_parameterized) {
      topSpeed = TestingDashboard.getInstance().getNumber(m_shooter, "shooterInputSpeed");
    }
    m_shooter.setTop(topSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.setTop(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
