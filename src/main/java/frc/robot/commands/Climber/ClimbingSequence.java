package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.Climber.ElevatorCane.CanesToExtend;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drive;
import frc.robot.testingdashboard.TestingDashboard;

public class ClimbingSequence extends SequentialCommandGroup {
    public ClimbingSequence() {
        final double DRIVE_SPEED = .5;
        final double CANE_SPEED = .5;
        final double DRIVE_DISTANCE = 60;
        final double CANE_HEIGHT = 24;

        Climber m_climber;
        Drive m_drive;

        addCommands(
            new ElevatorCane(CanesToExtend.CANE_BOTH, CANE_HEIGHT, CANE_SPEED, true),
            new DriveToBar(DRIVE_DISTANCE, DRIVE_SPEED, Constants.NO_SENSOR, true),
            new ElevatorCane(CanesToExtend.CANE_BOTH, CANE_HEIGHT/2, -CANE_SPEED, true)
        );
    }

    public static void registerWithTestingDashboard() {
        Climber climber = Climber.getInstance();
        ClimbingSequence cmd = new ClimbingSequence();
        TestingDashboard.getInstance().registerCommand(climber, "Basic", cmd);
      }
}
