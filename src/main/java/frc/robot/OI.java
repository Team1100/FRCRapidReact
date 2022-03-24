/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.Auto.ExpelBalls;
import frc.robot.commands.Auto.IntakeBalls;
import frc.robot.commands.Auto.ShootBallsHigh;
import frc.robot.commands.Auto.ShootBallsLow;
import frc.robot.commands.Drive.ArcadeDrive;
import frc.robot.commands.Drive.KeyboardDrive;
import frc.robot.commands.Drive.TankDrive;
import frc.robot.commands.Intake.LowerIntake;
import frc.robot.commands.Intake.RaiseIntake;
import frc.robot.commands.Shooter.ShootBall;
import frc.robot.input.AttackThree;
import frc.robot.input.ButtonBox;
import frc.robot.input.XboxController;
import frc.robot.input.KeyboardBox;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Shooter;
import frc.robot.testingdashboard.TestingDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  private static OI oi;

  public static AttackThree leftStick;
  public static AttackThree rightStick;
  private static XboxController DriverXboxController;
  private static XboxController OperatorXboxController;
  private static ButtonBox buttonBox;
  private static KeyboardBox keyboardBox;


  /**
   * Used outside of the OI class to return an instance of the class.
   * @return Returns instance of OI class formed from constructor.
   */
  public static OI getInstance() {
    if (oi == null) {
      oi = new OI();
    }
    return oi;
  }

  public OI() {
    // User Input
    // TODO: Tune deadband
    if (Constants.ATTACK_THREE_ENABLE) {
      leftStick = new AttackThree(RobotMap.U_JOYSTICK_LEFT, 0.01);
      rightStick = new AttackThree(RobotMap.U_JOYSTICK_RIGHT, 0.01);
    }
    if (Constants.BUTTON_BOX_ENABLE) {
      buttonBox = new ButtonBox(RobotMap.U_BUTTON_BOX);
    }
    if (Constants.KEYBOARD_BOX_ENABLE) {
      keyboardBox = new KeyboardBox(RobotMap.U_KEYBOARD_BOX);
    }
    if (Constants.XBOX_CONTROLLER_DRIVER_ENABLE) {
      DriverXboxController = new XboxController(RobotMap.U_DRIVER_XBOX_CONTROLLER, Constants.XBOX_DEADBAND_LIMIT);
    }
    if (Constants.XBOX_CONTROLLER_OPERATOR_ENABLE) {
      OperatorXboxController = new XboxController(RobotMap.U_OPERATOR_XBOX_CONTROLLER, Constants.XBOX_DEADBAND_LIMIT);
    }
    
    ////////////////////////////////////////////////////
    // Now Mapping Commands to XBox
    ////////////////////////////////////////////////////
    if (Constants.XBOX_CONTROLLER_DRIVER_ENABLE) {
      DriverXboxController.getButtonBack().toggleWhenPressed(new ArcadeDrive());
    }
    if (Constants.XBOX_CONTROLLER_OPERATOR_ENABLE) {
      OperatorXboxController.getButtonY().whenPressed(new LowerIntake());
      OperatorXboxController.getButtonB().whenPressed(new RaiseIntake());
      OperatorXboxController.getButtonRightBumper().whileHeld(new IntakeBalls());
      OperatorXboxController.getButtonLeftBumper().whileHeld(new ExpelBalls());
      OperatorXboxController.getButtonX().whileHeld(new ShootBallsHigh());
    }
    


    ////////////////////////////////////////////////////
    // Now Mapping Commands to AttackThree controllers
    ////////////////////////////////////////////////////
    if (Constants.ATTACK_THREE_ENABLE) {
      leftStick.getButton(2).toggleWhenPressed(new TankDrive());
    }

    ////////////////////////////////////////////////////
    // Now Mapping Commands to Button Box
    ////////////////////////////////////////////////////


    ////////////////////////////////////////////////////
    // Now Mapping Commands to Keyboard Box
    ////////////////////////////////////////////////////

    if (Constants.KEYBOARD_BOX_ENABLE) {
      keyboardBox.getKForward().whileHeld(new KeyboardDrive(0.75, 0));
      keyboardBox.getKBack().whileHeld(new KeyboardDrive(-0.75, 0));
      keyboardBox.getKLeft().whileHeld(new KeyboardDrive(0, 0.67));
      keyboardBox.getKRight().whileHeld(new KeyboardDrive(0, -0.67));
    }
    
  }

  /**
   * Returns the left Joystick
   * @return the leftStick
   */
  public AttackThree getLeftStick() {
    return leftStick;
  }

  /**
   * Returns the right Joystick
   * @return the rightStick
   */
  public AttackThree getRightStick() {
    return rightStick;
  }

  /**
   * Returns the Xbox Controller
   * @return the Xbox Controller
   */
  public XboxController getDriverXboxController() {
      return DriverXboxController;
  }

  public XboxController getOperatorXboxController() {
    return OperatorXboxController;
}

  /**
   * Returns the KeyboardBox
   * @return the KeyboardBox
   */
  public KeyboardBox getKeyboardBox() {
    return keyboardBox;
  }
  
}
