package frc.robot.input;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is for a ButtonBox. All information about any given button
 * can be accessed through the functions written here.
 */
public class ButtonBox extends Joystick {
	private JoystickButton WideFocus;
	private JoystickButton FineFocus;
	private JoystickButton Fire;
	private JoystickButton IntakeIn;
	private JoystickButton IntakeOut;
	private JoystickButton Hopper;
	private JoystickButton CPDeploy;
	private JoystickButton CPSpin;
	private JoystickButton CPYellow;
	private JoystickButton CPGreen;
	private JoystickButton CPRed;
	private JoystickButton CPBlue;
	private JoystickButton ClimberDeploy;
	private JoystickButton Climb;
	private JoystickButton ClimberLeft;
	private JoystickButton ClimberRight;

	/**
	 * Initializes a ButtonBox Controller on a specific channel, mapping the buttons. The
	 * 
	 * @param channel the channel the ButtonBox is plugged into
	 */
	public ButtonBox(int channel) {
		super(channel);
		
		WideFocus = new JoystickButton(this, 3);
		FineFocus = new JoystickButton(this, 2);
		Fire = new JoystickButton(this, 1);
		IntakeIn = new JoystickButton(this, 5);
		IntakeOut = new JoystickButton(this, 12);
		Hopper = new JoystickButton(this, 4);
		CPDeploy = new JoystickButton(this, 6);
		CPSpin = new JoystickButton(this, 11);
		CPYellow = new JoystickButton(this, 10);
		CPGreen = new JoystickButton(this, 9);
		CPRed = new JoystickButton(this, 8);
		CPBlue = new JoystickButton(this, 7);
		ClimberDeploy = new JoystickButton(this, 16);
		Climb = new JoystickButton(this, 15);
		ClimberLeft = new JoystickButton(this, 14);
		ClimberRight = new JoystickButton(this, 13);
	}

	/**
	 * Gets the Wide Focus button from the ButtonBox
	 * 
	 * @return Wide Focus button
	 */
	public JoystickButton getWideFocus() {
		return this.WideFocus;
	}

	/**
	 * Gets the Fine Focus button from the ButtonBox
	 * 
	 * @return Fine Focus button
	 */
	public JoystickButton getFineFocus() {
		return this.FineFocus;
	}

	/**
	 * Gets the Fire button from the ButtonBox
	 * 
	 * @return Fire button
	 */
	public JoystickButton getFire() {
		return this.Fire;
	}

	/**
	 * Gets the Intake In button from the ButtonBox
	 * 
	 * @return Intake In Button
	 */
	public JoystickButton getIntakeIn() {
		return this.IntakeIn;
	}

	/**
	 * Gets the Intake Out button from the ButtonBox
	 * 
	 * @return Intake Out Button
	 */
	public JoystickButton getIntakeOut() {
		return this.IntakeOut;
	}

	/**
	 * Gets the Hopper button from the ButtonBox
	 * 
	 * @return Hopper button
	 */
	public JoystickButton getHopper() {
		return this.Hopper;
	}

	/**
	 * Gets the Color Panel Deploy button from the ButtonBox
	 * 
	 * @return Color Panel Deploy button
	 */
	public JoystickButton getCPDeploy() {
		return this.CPDeploy;
	}

	/**
	 * Gets the Color Panel Spin button from the ButtonBox
	 * 
	 * @return Color Panel Spin button
	 */
	public JoystickButton getCPSpin() {
		return this.CPSpin;
	}

	/**
	 * Gets the Color Panel Yellow button from the ButtonBox
	 * 
	 * @return Color Panel Yellow button
	 */
	public JoystickButton getCPYellow() {
		return this.CPYellow;
	}

	/**
	 * Gets the Color Panel Green button from the ButtonBox
	 * 
	 * @return Color Panel Green button
	 */
	public JoystickButton getCPGreen() {
		return this.CPGreen;
	}

	/**
	 * Gets the Color Panel Red button from the ButtonBox
	 * 
	 * @return Color Panel Red button
	 */
	public JoystickButton getCPRed() {
		return this.CPRed;
	}

	/**
	 * Gets the Color Panel Blue button from the ButtonBox
	 * 
	 * @return Color Panel Blue button
	 */
	public JoystickButton getCPBlue() {
		return this.CPBlue;
	}

	/**
	 * Gets the Climber Deploy button from the ButtonBox
	 * 
	 * @return Climber Deploy button
	 */
	public JoystickButton getClimberDeploy() {
		return this.ClimberDeploy;
	}

	/**
	 * Gets the Climb button from the ButtonBox
	 * 
	 * @return Climb button
	 */
	public JoystickButton getClimb() {
		return this.Climb;
	}

	/**
	 * Gets the Climber Left button from the ButtonBox
	 * 
	 * @return Climber Left button
	 */
	public JoystickButton getClimberLeft() {
		return this.ClimberLeft;
	}

	/**
	 * Gets the Climber Right button from the ButtonBox
	 * 
	 * @return Climber Right button
	 */
	public JoystickButton getClimberRight() {
		return this.ClimberRight;
	}
}
