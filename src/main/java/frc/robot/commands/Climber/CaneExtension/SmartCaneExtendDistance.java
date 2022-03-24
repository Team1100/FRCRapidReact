package frc.robot.commands.Climber.CaneExtension;

import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ClimberCaneExtension;
import frc.robot.testingdashboard.TestingDashboard;

// This command extends the cane a certain distance without reseting the encoder distance.
public class SmartCaneExtendDistance extends CommandBase {

    private static final double START_DELAY = 1; // 1 second start delay to test if encoder rate is 0
    private static final double MINIMUM_ENCODER_VELOCITY = -0.2;
    private static final double MAXIMUM_ENCODER_VELOCITY = 0.2;
    private ClimberCaneExtension m_climberCaneExtension;
    private Climber m_climber;
    private double m_caneSpeed;
    private boolean m_parameterized;
    private double m_caneHeightToTravel;
    private Timer m_timer;
    private boolean m_timer_running = false;
    private double initialLeftEncoderDistance;
    private double initialRightEncoderDistance;
    RelativeEncoder m_leftEncoder;
    RelativeEncoder m_rightEncoder;

    public SmartCaneExtendDistance(double caneHeightToTravel, double caneSpeed, boolean parameterized) { 
        // Use addRequirements() here to declare subsystem dependencies.
        m_climberCaneExtension = ClimberCaneExtension.getInstance();
        m_climber = Climber.getInstance();
        addRequirements(m_climberCaneExtension);
        m_leftEncoder = m_climber.getLeftCaneEncoder();
        m_rightEncoder = m_climber.getRightCaneEncoder();
        m_caneSpeed = caneSpeed;
        m_caneHeightToTravel = caneHeightToTravel;
        m_parameterized = parameterized;
        m_timer = new Timer();
        m_timer_running = false;
      }

    public static void registerWithTestingDashboard() {
        Climber climber = Climber.getInstance();
        double distance = 12;
        SmartCaneExtendDistance cmdBoth = new SmartCaneExtendDistance(distance, Climber.INITIAL_CANE_EXTENTION_SPEED, false);
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
      initialRightEncoderDistance = -m_rightEncoder.getPosition();
      initialLeftEncoderDistance = -m_leftEncoder.getPosition();
      initializeTimer();
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
      if (!m_parameterized) {
        m_caneSpeed = TestingDashboard.getInstance().getNumber(m_climber, "ExtensionSpeed");
        m_caneHeightToTravel = TestingDashboard.getInstance().getNumber(m_climber, "CaneHeightToTravel");
      }
      
      if (m_caneHeightToTravel > 0) {
        m_climber.extendCane(m_caneSpeed);
      } else {
        m_climber.extendCane(-m_caneSpeed);
      }
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      stopTimer();
      m_climber.extendCane(0);
    }

   // Returns true when the command should end.
   @Override
   public boolean isFinished() {
      boolean finished = false;
      
      if (m_caneHeightToTravel >= 0) {
        if (-m_leftEncoder.getPosition() >= m_caneHeightToTravel + initialLeftEncoderDistance || -m_rightEncoder.getPosition() >= m_caneHeightToTravel + initialRightEncoderDistance) {
          finished = true;
        }
      } else if (m_caneHeightToTravel < 0) {
        if (-m_leftEncoder.getPosition() <= initialLeftEncoderDistance + m_caneHeightToTravel || -m_rightEncoder.getPosition() <= initialRightEncoderDistance + m_caneHeightToTravel) {
          finished = true;
        }
      }
      if (caneStoppedMoving()) {
        finished = true;
      }
      return finished;
    }
}
