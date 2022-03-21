package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.testingdashboard.TestingDashboard;

public class ClimbingSequence extends SequentialCommandGroup {
    public ClimbingSequence() {
        final double DRIVE_SPEED = .5;
        final double CANE_SPEED = .5;
        final double DRIVE_DISTANCE = 60;
        final double CANE_HEIGHT = 24;

        addCommands(
            new CaneExtendDistance(CANE_HEIGHT, CANE_SPEED, true),
            new DriveToBar(DRIVE_DISTANCE, DRIVE_SPEED, Constants.MOTOR_CURRENT, true),
            new CaneExtendDistance(-CANE_HEIGHT, CANE_SPEED, true)
        );
    }

    public static void registerWithTestingDashboard() {
        Climber climber = Climber.getInstance();
        ClimbingSequence cmd = new ClimbingSequence();
        TestingDashboard.getInstance().registerCommand(climber, "Basic", cmd);
      }
}
