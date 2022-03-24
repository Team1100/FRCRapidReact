package frc.robot.commands.Climber.CaneExtension;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ClimberCaneExtension;
import frc.robot.testingdashboard.TestingDashboard;

// This command uses the encoder distance to go at a fast speed until a certain distance away from 
// full extention/retraction, but then uses the ExtendCaneToLimit command at a lower speed to sense when to stop
// This requires the encoders to be zeroed when fully retracted
public class SmartExtendCaneToLimit extends CommandBase {

    private static final double START_DELAY = 1; // 1 second start delay to test if encoder rate is 0
    private static final double MINIMUM_ENCODER_VELOCITY = -0.2;
    private static final double MAXIMUM_ENCODER_VELOCITY = 0.2;
    private static final double MAXIMUM_ENCODER_DISTANCE = 23.0; // Distance of the encoder when fully extended (in)
    private static final double TOLERANCE = 1; // Sets the tolerance for when to stop (in)
    private static final double MINIMUM_TARGET = TOLERANCE;
    private static final double MAXIMUM_TARGET = MAXIMUM_ENCODER_DISTANCE - TOLERANCE;
    private static final double SLOW_ZONE_OFFSET = 7; // distance away from target where the motors are reduced in power.
    private ClimberCaneExtension m_climberCaneExtension;
    private Climber m_climber;
    private double m_caneSpeed;
    private boolean m_parameterized;
    private Timer m_timer;
    private boolean m_timer_running = false;
    RelativeEncoder m_leftEncoder;
    RelativeEncoder m_rightEncoder;
    ExtendCaneToLimit m_eLimit;

    public SmartExtendCaneToLimit(double caneSpeed, boolean parameterized) { 
        // Use addRequirements() here to declare subsystem dependencies.
        m_climberCaneExtension = ClimberCaneExtension.getInstance();
        m_climber = Climber.getInstance();
        m_eLimit = new ExtendCaneToLimit(caneSpeed/2, parameterized);
        addRequirements(m_climberCaneExtension);
        m_leftEncoder = m_climber.getLeftCaneEncoder();
        m_rightEncoder = m_climber.getRightCaneEncoder();
        m_caneSpeed = caneSpeed;
        m_parameterized = parameterized;
        m_timer = new Timer();
        m_timer_running = false;
      }

    public static void registerWithTestingDashboard() {
        Climber climber = Climber.getInstance();
        SmartExtendCaneToLimit cmdBoth = new SmartExtendCaneToLimit(Climber.INITIAL_CANE_EXTENTION_SPEED, false);
        TestingDashboard.getInstance().registerCommand(climber, "CaneExtensionBoth", cmdBoth);
    }

    void initializeTimer() {
      m_timer.reset();
      m_timer.start();
      m_timer_running = true;
    }

    void stopTimer() {
      m_timer.stop();
      m_timer_running = false;
    }

    boolean caneStoppedMoving() {
      double encoder_velocity = (m_leftEncoder.getVelocity() + m_rightEncoder.getVelocity()) / 2;
      if (m_timer_running) {
        boolean in_dead_zone = (encoder_velocity > MINIMUM_ENCODER_VELOCITY && encoder_velocity < MAXIMUM_ENCODER_VELOCITY);
        return (m_timer.hasElapsed(START_DELAY) && in_dead_zone);
      } else {
        return false;
      }
    }

    @Override
    public void initialize() {
      initializeTimer();
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
      if (!m_parameterized) {
        m_caneSpeed = TestingDashboard.getInstance().getNumber(m_climber, "ExtensionSpeed");
      }

      if (m_caneSpeed >= 0) {
        if (-m_leftEncoder.getPosition() >= MAXIMUM_TARGET - SLOW_ZONE_OFFSET || -m_rightEncoder.getPosition() >= MAXIMUM_TARGET - SLOW_ZONE_OFFSET) {
          m_climber.extendCane(0);
          m_eLimit.schedule();
        }
      } else if (m_caneSpeed < 0) {
        if (-m_leftEncoder.getPosition() <= SLOW_ZONE_OFFSET || -m_rightEncoder.getPosition() <= SLOW_ZONE_OFFSET) {
          m_climber.extendCane(0);
          m_eLimit.schedule();
        }
      } else {
        m_climber.extendCane(m_caneSpeed);
      }
      
      
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      stopTimer();
    }

   // Returns true when the command should end.
   @Override
   public boolean isFinished() {
      boolean finished = false;
      if (m_eLimit.isFinished()) {
        finished = true;
      }
      
      if (caneStoppedMoving()) {
        finished = true;
      }
      return finished;
    }
}
