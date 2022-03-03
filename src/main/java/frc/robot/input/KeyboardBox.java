package frc.robot.input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Drive.ArcadeDrive;
import frc.robot.commands.Drive.KeyboardDrive;

/**
 * This class is for a KeyboardBox. It is based on a class for a ButtonBox.
 * All information about any given button can be accessed through the
 * functions written here.
 */
public class KeyboardBox extends Joystick {
  private JoystickButton KLeft;
  private JoystickButton KForward;
  private JoystickButton KBack;
  private JoystickButton KRight;

  /**
   * Initializes a ButtonBox Controller on a specific channel, mapping the buttons. The
   *
   * @param channel the channel the ButtonBox is plugged into
   */
  public KeyboardBox(int channel) {
    super(channel);

    KLeft = new JoystickButton(this, 10);
    KForward = new JoystickButton(this, 9);
    KBack = new JoystickButton(this, 8);
    KRight = new JoystickButton(this, 7);
  }

  /**
   * Gets the left button from the ButtonBox
   *
   * Note that the left button is the Yellow button from the ButtonBox.
   *
   * @return left button
   */
  public JoystickButton getKLeft() {
    return this.KLeft;
  }

  /**
   * Gets the forward button from the ButtonBox
   *
   * Note that the forward button is the Green button from the ButtonBox.
   *
   * @return forward button
   */
  public JoystickButton getKForward() {
    return this.KForward;
  }

  /**
   * Gets the back button from the ButtonBox
   *
   * Note that the back button is the Red button from the ButtonBox.
   *
   * @return back button
   */
  public JoystickButton getKBack() {
    return this.KBack;
  }

  /**
   * Gets the right button from the ButtonBox
   *
   * Note that the right button is the Blue button from the ButtonBox.
   *
   * @return right button
   */
  public JoystickButton getKRight() {
    return this.KRight;
  }
}
