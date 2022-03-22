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

public class SpinShooter extends CommandBase {
  private Shooter m_shooter;
  private double m_speed;
  private static final double DEFAULT_SHOOTER_SPEED = 0.5;
  private boolean m_parameterized = true;

  /**
   * Creates a new SpinShooter.
   */
  public SpinShooter(double shooterSpeed, boolean parameterized) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Shooter.getInstance());
    m_shooter = Shooter.getInstance();
    m_speed = shooterSpeed;
    m_parameterized = parameterized;
  }

  public static void registerWithTestingDashboard() {
    Shooter shooter = Shooter.getInstance();
    double speed = DEFAULT_SHOOTER_SPEED;
    SpinShooter cmd = new SpinShooter(speed, false);
    TestingDashboard.getInstance().registerCommand(shooter, "Basic", cmd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    double speed = m_speed;
    if (!m_parameterized) {
      speed = TestingDashboard.getInstance().getNumber(m_shooter, "ShooterInputSpeed");
    }
    m_shooter.setMotor(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.setMotor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
