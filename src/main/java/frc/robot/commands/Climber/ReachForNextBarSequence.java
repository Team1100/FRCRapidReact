package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Climber;
import frc.robot.testingdashboard.TestingDashboard;

public class ReachForNextBarSequence extends SequentialCommandGroup {
    static final double CANE_EXTENSION_SPEED = .5;
    static final double CANE_HEIGHT = 12;
    static final double CANE_ROTATION_SPEED = .2;

    public ReachForNextBarSequence(double caneExtensionSpeed, double caneHeight,  double caneRotationSpeed) {
        final double m_caneSpeed = caneExtensionSpeed;
        final double m_caneHeight = caneHeight;
        final double m_caneRotationSpeed = caneRotationSpeed;
        
        addCommands(
            new CaneExtendDistance(-m_caneHeight, m_caneSpeed, true),
            new RotateCaneToBar(m_caneRotationSpeed, true),
            new CaneExtendDistance(-m_caneHeight, m_caneSpeed, true),
            new RotateCaneToBar(-m_caneRotationSpeed, true)
        );
    }

    public static void registerWithTestingDashboard() {
        Climber climber = Climber.getInstance();
        ReachForNextBarSequence cmd = new ReachForNextBarSequence(CANE_EXTENSION_SPEED, CANE_HEIGHT, CANE_ROTATION_SPEED);
        TestingDashboard.getInstance().registerCommand(climber, "Basic", cmd);
      }
}
