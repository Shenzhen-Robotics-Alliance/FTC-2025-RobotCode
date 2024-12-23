package org.firstinspires.ftc.teamcode.autos;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.RobotContainer;
import org.firstinspires.ftc.teamcode.subsystems.superstruct.SuperStructurePose;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;

public class AutoUtils {
    public static Command driveForwardWhileScoring(RobotContainer robotContainer) {
        return robotContainer.driveSubsystem.drive(
                () -> new ChassisSpeeds(0.3, 0, 0),
                () -> false).withTimeout(300);
    }

    public static final Pose2d scoreSamplePose = new Pose2d(0.35, 0.78, Rotation2d.fromDegrees(135));
    public static Command driveToBasketAndScoreSample(RobotContainer robotContainer) {
        final SequentialCommandGroup sequence = new SequentialCommandGroup();
        Command moveToScoringSample = robotContainer.driveSubsystem.driveToPose(
                () -> scoreSamplePose,
                new Pose2d(0.02, 0.02, Rotation2d.fromDegrees(5)),
                1);
        Command prepareToScore = robotContainer.superStructCommandsFactory.passSampleToUpperArm()
                .andThen(robotContainer.superStructureSubsystem
                        .moveToPose(SuperStructurePose.SCORE_SAMPLE.withArmFlipPosition(0.7)));
        sequence.addCommands(moveToScoringSample.alongWith(prepareToScore));
        sequence.addCommands(robotContainer.superStructureSubsystem.moveToPose(SuperStructurePose.SCORE_SAMPLE)
                .raceWith(driveForwardWhileScoring(robotContainer)));
        sequence.addCommands(robotContainer.superStructureSubsystem.openArmClaw());
        sequence.addCommands(robotContainer.superStructureSubsystem
                .moveToPose(SuperStructurePose.SCORE_SAMPLE.withArmFlipPosition(0.7)));

        return sequence;
    }
}
