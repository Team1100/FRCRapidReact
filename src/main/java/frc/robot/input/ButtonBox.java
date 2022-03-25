package frc.robot.input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is for a ButtonBox. All information about any given button
 * can be accessed through the functions written here.
 */
public class ButtonBox extends Joystick {
	/*buttons are named in numerical order, reading the buttonbox from left to right like a book
	  button1 would be the top-left button, button2 top-middle, and so-forth
	*/
	private JoystickButton button1;
	private JoystickButton button2;
	private JoystickButton button3;
	private JoystickButton button4;
	private JoystickButton button5;
	private JoystickButton button6;
	private JoystickButton button7;
	private JoystickButton button8;
	private JoystickButton button9;
	private JoystickButton button10;
	private JoystickButton button11;
	private JoystickButton button12;
	private JoystickButton button13;
	private JoystickButton button14;

	/**
	 * Initializes a ButtonBox Controller on a specific channel, mapping the buttons. The
	 * 
	 * @param channel the channel the ButtonBox is plugged into
	 */
	public ButtonBox(int channel) {
		super(channel);
		//buttons are named in numerical order, reading the buttonbox from left to right like a book

		button1 = new JoystickButton(this, 3);
		button2 = new JoystickButton(this, 2);
		button3 = new JoystickButton(this, 1);
		button4 = new JoystickButton(this, 12);
		button5 = new JoystickButton(this, 5);
		button6 = new JoystickButton(this, 4);
		//button 7 does not work
		button8 = new JoystickButton(this, 11);
		button9 = new JoystickButton(this, 8);
		button10 = new JoystickButton(this, 7);
		button11 = new JoystickButton(this, 10);
		button12 = new JoystickButton(this, 9);
		button13 = new JoystickButton(this, 16);
		button14 = new JoystickButton(this, 15);
	}

	/**
	 * Gets the Wide Focus button from the ButtonBox
	 * 
	 * @return Wide Focus button
	 */
	public JoystickButton getButton1() {
		return this.button1;
	}

	/**
	 * Gets the Fine Focus button from the ButtonBox
	 * 
	 * @return Fine Focus button
	 */
	public JoystickButton getButton2() {
		return this.button2;
	}

	/**
	 * Gets the Fire button from the ButtonBox
	 * 
	 * @return Fire button
	 */
	public JoystickButton getButton3() {
		return this.button3;
	}

	/**
	 * Gets the Intake In button from the ButtonBox
	 * 
	 * @return Intake In Button
	 */
	public JoystickButton getButton4() {
		return this.button4;
	}

	/**
	 * Gets the Intake Out button from the ButtonBox
	 * 
	 * @return Intake Out Button
	 */
	public JoystickButton getButton5() {
		return this.button5;
	}

	/**
	 * Gets the Hopper button from the ButtonBox
	 * 
	 * @return Hopper button
	 */
	public JoystickButton getButton6() {
		return this.button6;
	}

	/**
	 * Gets the Color Panel Deploy button from the ButtonBox
	 * 
	 * @return Color Panel Deploy button
	 */
	public JoystickButton getButton8() {
		return this.button8;
	}

	/**
	 * Gets the Color Panel Spin button from the ButtonBox
	 * 
	 * @return Color Panel Spin button
	 */
	public JoystickButton getButton9() {
		return this.button9;
	}

	/**
	 * Gets the Color Panel Yellow button from the ButtonBox
	 * 
	 * @return Color Panel Yellow button
	 */
	public JoystickButton getButton10() {
		return this.button10;
	}

	/**
	 * Gets the Color Panel Green button from the ButtonBox
	 * 
	 * @return Color Panel Green button
	 */
	public JoystickButton getButton11() {
		return this.button11;
	}

	/**
	 * Gets the Color Panel Red button from the ButtonBox
	 * 
	 * @return Color Panel Red button
	 */
	public JoystickButton getButton12() {
		return this.button12;
	}

	/**
	 * Gets the Color Panel Blue button from the ButtonBox
	 * 
	 * @return Color Panel Blue button
	 */
	public JoystickButton getButton13() {
		return this.button13;
	}

	/**
	 * Gets the Climber Deploy button from the ButtonBox
	 * 
	 * @return Climber Deploy button
	 */
	public JoystickButton getButton14() {
		return this.button14;
	}
}
