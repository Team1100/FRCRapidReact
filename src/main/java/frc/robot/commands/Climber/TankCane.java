// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.input.AttackThree;
import frc.robot.input.AttackThree.AttackThreeAxis;
import frc.robot.subsystems.Climber;


public class TankCane extends CommandBase {
  private Climber m_climber;
  private OI m_oi;
  /** Creates a new tankCane. **/
  public TankCane() {
    // Use addRequirements() here to declare subsystem dependencies.
    m_climber = Climber.getInstance();
    addRequirements(m_climber);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_climber.tankCane(0, 0);
    m_oi = OI.getInstance();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    AttackThree leftJoystick = m_oi.getLeftStick();
    AttackThree rightJoystick = m_oi.getRightStick();
    double leftJoystickSpeed = -leftJoystick.getAxis(AttackThreeAxis.kY);
    double rightJoystickSpeed = -rightJoystick.getAxis(AttackThreeAxis.kY);

    m_climber.tankCane(leftJoystickSpeed, rightJoystickSpeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_climber.tankCane(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
