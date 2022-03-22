/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.Drive.ArcadeDrive;
import frc.robot.commands.Drive.KeyboardDrive;
import frc.robot.commands.Drive.TankDrive;
import frc.robot.input.AttackThree;
import frc.robot.input.ButtonBox;
import frc.robot.input.XboxController;
import frc.robot.input.KeyboardBox;
import frc.robot.subsystems.Drive;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  private static OI oi;

  public static AttackThree leftStick;
  public static AttackThree rightStick;
  private static XboxController xbox;
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
    leftStick = new AttackThree(RobotMap.U_JOYSTICK_LEFT, 0.01);
    rightStick = new AttackThree(RobotMap.U_JOYSTICK_RIGHT, 0.01);
    xbox = new XboxController(RobotMap.U_XBOX_CONTROLLER, 0.1);
    buttonBox = new ButtonBox(RobotMap.U_BUTTON_BOX);
    keyboardBox = new KeyboardBox(RobotMap.U_KEYBOARD_BOX);

    ////////////////////////////////////////////////////
    // Now Mapping Commands to XBox
    ////////////////////////////////////////////////////
    xbox.getButtonBack().toggleWhenPressed(new ArcadeDrive());

    ////////////////////////////////////////////////////
    // Now Mapping Commands to AttackThree controllers
    ////////////////////////////////////////////////////
    leftStick.getButton(2).toggleWhenPressed(new TankDrive());
   
    ////////////////////////////////////////////////////
    // Now Mapping Commands to Button Box
    ////////////////////////////////////////////////////


    ////////////////////////////////////////////////////
    // Now Mapping Commands to Keyboard Box
    ////////////////////////////////////////////////////

    keyboardBox.getKForward().whileHeld(new KeyboardDrive(0.75, 0));
    keyboardBox.getKBack().whileHeld(new KeyboardDrive(-0.75, 0));
    keyboardBox.getKLeft().whileHeld(new KeyboardDrive(0, 0.67));
    keyboardBox.getKRight().whileHeld(new KeyboardDrive(0, -0.67));
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
  public XboxController getXbox() {
      return xbox;
  }

  /**
   * Returns the KeyboardBox
   * @return the KeyboardBox
   */
  public KeyboardBox getKeyboardBox() {
    return keyboardBox;
  }
  
}
