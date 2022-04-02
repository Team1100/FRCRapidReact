/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import frc.robot.commands.Auto.DriveBackAndShootHigh;
import frc.robot.commands.Auto.ExpelBalls;
import frc.robot.commands.Auto.IntakeBalls;
import frc.robot.commands.Auto.LowerCaneShootBallsHigh;
import frc.robot.commands.Auto.LowerCaneShootBallsLow;
import frc.robot.commands.Climber.UserOperateCane;
import frc.robot.commands.Climber.CaneExtension.ZeroCaneEncoders;
import frc.robot.commands.Climber.CaneExtension.ZeroCaneEncoders;
import frc.robot.commands.Climber.Sequences.ClimbStatefully;
import frc.robot.commands.Climber.Sequences.ReachForNextBarStatefully;
import frc.robot.commands.Conveyor.SpinConveyorBackwards;
import frc.robot.commands.Conveyor.SpinConveyorForwards;
import frc.robot.commands.Drive.ArcadeDrive;
import frc.robot.commands.Drive.KeyboardDrive;
import frc.robot.commands.Drive.SwitchDriveIdleMode;
import frc.robot.commands.Drive.TankDrive;
import frc.robot.commands.Intake.LowerIntake;
import frc.robot.commands.Intake.RaiseIntake;
import frc.robot.input.AttackThree;
import frc.robot.input.ButtonBox;
import frc.robot.input.XboxController;
import frc.robot.input.KeyboardBox;

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
      DriverXboxController.getButtonRightBumper().whileHeld(new IntakeBalls());
      DriverXboxController.getButtonLeftBumper().whileHeld(new ExpelBalls());
      DriverXboxController.getButtonB().whileHeld(new LowerCaneShootBallsHigh());
      DriverXboxController.getButtonA().whileHeld(new LowerCaneShootBallsLow());
      DriverXboxController.getButtonY().whenPressed(new LowerIntake());
      DriverXboxController.getButtonX().whenPressed(new RaiseIntake());
      DriverXboxController.getButtonBack().whenPressed(new UserOperateCane());
      DriverXboxController.getButtonStart().whenPressed(new DriveBackAndShootHigh());
    }
    if (Constants.XBOX_CONTROLLER_OPERATOR_ENABLE) {
      OperatorXboxController.getButtonBack().toggleWhenPressed(new ClimbStatefully(Constants.DEFAULT_NUMBER_OF_CLIMB_CYCLES, true));
      OperatorXboxController.getDPad().getLeft().toggleWhenPressed(new ZeroCaneEncoders(ClimbStatefully.INTIIAL_CANE_EXTENSION_SPEED, true));
      OperatorXboxController.getButtonStart().toggleWhenPressed(new ReachForNextBarStatefully(ClimbStatefully.INTIIAL_CANE_EXTENSION_SPEED, ClimbStatefully.SLOWER_CANE_EXTENSION_SPEED, ClimbStatefully.INITIAL_CANE_EXTENSION_DISTANCE, ClimbStatefully.CANE_FORWARDS_ROTATION_SPEED, ClimbStatefully.CANE_BACKWARDS_ROTATION_SPEED));
      //OperatorXboxController.getButtonA().toggleWhenPressed(new ClimbStatefully(Constants.CLIMBER_LEVEL_2_CLIMB, true));
      //OperatorXboxController.getButtonB().toggleWhenPressed(new ClimbStatefully(Constants.CLIMBER_LEVEL_3_CLIMB, true));
      //OperatorXboxController.getButtonY().toggleWhenPressed(new ClimbStatefully(Constants.CLIMBER_LEVEL_4_CLIMB, true));
    }
    
    // Shootlow right trigger
    // shoot hight left trigger
    // intakein right bumper
    // expell left bumper
    // 

    ////////////////////////////////////////////////////
    // Now Mapping Commands to AttackThree controllers
    ////////////////////////////////////////////////////
    if (Constants.ATTACK_THREE_ENABLE) {
      leftStick.getButton(2).toggleWhenPressed(new TankDrive());
    }

    ////////////////////////////////////////////////////
    // Now Mapping Commands to Button Box
    ////////////////////////////////////////////////////
    if (Constants.BUTTON_BOX_ENABLE) {
      buttonBox.getButton1().whenHeld(new ClimbStatefully(Constants.CLIMBER_LEVEL_2_CLIMB, true));
      buttonBox.getButton2().whenHeld(new ClimbStatefully(Constants.CLIMBER_LEVEL_3_CLIMB, true));
      buttonBox.getButton3().whenHeld(new ClimbStatefully(Constants.CLIMBER_LEVEL_4_CLIMB, true));
      buttonBox.getButton4().whenPressed(new RaiseIntake());
      buttonBox.getButton5().whenPressed(new LowerIntake());
      buttonBox.getButton6().whenHeld(new IntakeBalls());
      buttonBox.getButton8().whenPressed(new ClimbStatefully(Constants.DEFAULT_NUMBER_OF_CLIMB_CYCLES, true));
      buttonBox.getButton9().whenPressed(new ReachForNextBarStatefully(ClimbStatefully.INTIIAL_CANE_EXTENSION_SPEED, ClimbStatefully.SLOWER_CANE_EXTENSION_SPEED, ClimbStatefully.INITIAL_CANE_EXTENSION_DISTANCE, ClimbStatefully.CANE_FORWARDS_ROTATION_SPEED, ClimbStatefully.CANE_BACKWARDS_ROTATION_SPEED));
      buttonBox.getButton10().whenPressed(new SwitchDriveIdleMode());
      buttonBox.getButton14().toggleWhenPressed(new ArcadeDrive());
    }


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
