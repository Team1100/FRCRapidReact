package frc.robot.commands.Climber;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.input.XboxController;
import frc.robot.input.XboxController.XboxAxis;
import frc.robot.subsystems.Climber;
import frc.robot.testingdashboard.TestingDashboard;


public class ElevatorCane extends CommandBase {
    public static final double GEAR_DIAMETER_IN_INCHES = 4;
    public static final double GEAR_CIRCUMFERENCE_IN_INCHES = GEAR_DIAMETER_IN_INCHES * Math.PI;
    public static final double ENCODER_INITIAL_POSITION = 0;

    private Climber m_climber;
    private OI m_oi;
    private double m_caneSpeed;
    private boolean m_parameterized;
    private double m_leftCaneCurrentHeight;
    private double m_rightCaneCurrentHeight;
    private double m_caneHeightToTravel;
    private double m_currentDistance;
    private double m_currentCaneHeight;
    private double m_direction; // 1 indicates extension of the cane
    private CanesToExtend  m_sideToExtend; // true indicates right
    private CANSparkMax m_caneMotor;
    private RelativeEncoder m_caneEncoder;

    public enum CanesToExtend {
      CANE_LEFT(0),
     
      CANE_RIGHT(1),

      CANE_BOTH(2);
    
      public final int key;

		  private CanesToExtend(int key) {
			  this.key = key;
		  }
    }

    public ElevatorCane(CanesToExtend cane, double caneHeightToTravel, double caneSpeed, boolean parameterized) { 
        // Use addRequirements() here to declare subsystem dependencies.
        m_climber = Climber.getInstance();
        addRequirements(m_climber);
        m_caneSpeed = caneSpeed;
        m_caneHeightToTravel = caneHeightToTravel;
        m_parameterized = parameterized;
        m_direction = 1;
        m_sideToExtend = cane;
        if (cane == CanesToExtend.CANE_LEFT) {
          m_caneMotor = m_climber.getLeftCaneMotor();
          m_caneEncoder = m_climber.getLeftCaneEncoder();
        }
        else if (cane == CanesToExtend.CANE_RIGHT) {
          m_caneMotor = m_climber.getRightCaneMotor();
          m_caneEncoder = m_climber.getRightCaneEncoder();
        }
      }

    public static void registerWithTestingDashboard() {
        Climber climber = Climber.getInstance();
        ElevatorCane cmdRight = new ElevatorCane(CanesToExtend.CANE_RIGHT, 0, 0, false);
        ElevatorCane cmdLeft = new ElevatorCane(CanesToExtend.CANE_LEFT, 0, 0, false);
        ElevatorCane cmdBoth = new ElevatorCane(CanesToExtend.CANE_BOTH, 0, 0, false);
        TestingDashboard.getInstance().registerCommand(climber, "CaneExtensionRight", cmdRight);
        TestingDashboard.getInstance().registerCommand(climber, "CaneExtensionLeft", cmdLeft);
        TestingDashboard.getInstance().registerCommand(climber, "CaneExtensionBoth", cmdBoth);
        TestingDashboard.getInstance().registerNumber(climber, "ElevatorClimber", "CaneHeightToTravel", 0);
        TestingDashboard.getInstance().registerNumber(climber, "ElevatorClimber", "CurrentLeftCaneHeight", 0);
        TestingDashboard.getInstance().registerNumber(climber, "ElevatorClimber", "CurrentRightCaneHeight", 0);
      }

    @Override
    public void initialize() {
      m_caneEncoder.setPosition(ENCODER_INITIAL_POSITION);
      if (!m_parameterized) {
        m_caneSpeed = TestingDashboard.getInstance().getNumber(m_climber, "ExtensionSpeed");
        m_caneHeightToTravel = TestingDashboard.getInstance().getNumber(m_climber, "CaneHeightToTravel");
      }
      if (m_caneHeightToTravel < 0) {
        m_direction = -1;
        m_caneSpeed *= -1;
      }
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
      if (m_sideToExtend == CanesToExtend.CANE_BOTH) {
        m_climber.getLeftCaneMotor().set(m_caneSpeed);
        m_climber.getRightCaneMotor().set(m_caneSpeed);
      }
      else {
        m_caneMotor.set(m_caneSpeed);
      }
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      if (m_sideToExtend == CanesToExtend.CANE_BOTH) {
        m_climber.getLeftCaneMotor().set(0);
        m_climber.getRightCaneMotor().set(0);
      }
      else {
        m_caneMotor.set(0);
      }
    }

   // Returns true when the command should end.
   @Override
   public boolean isFinished() {
    boolean finished = false;
    if (m_sideToExtend == CanesToExtend.CANE_BOTH) {
      m_caneMotor = m_climber.getLeftCaneMotor();
      m_caneEncoder = m_climber.getLeftCaneEncoder();
    }
    double m_currentCaneHeight = m_caneEncoder.getPosition() * GEAR_CIRCUMFERENCE_IN_INCHES;
    if (m_direction == 1) {
      if (m_currentCaneHeight > m_caneHeightToTravel) {
        finished = true;
      }
    }
    else {
      if (m_currentCaneHeight < m_caneHeightToTravel) {
        finished = true;
      }
    }
    return finished;
   }

}
