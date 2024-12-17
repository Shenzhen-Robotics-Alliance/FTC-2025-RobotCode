package org.firstinspires.ftc.teamcode.autos;

import com.arcrobotics.ftclib.command.Command;

import org.firstinspires.ftc.teamcode.RobotContainer;

import edu.wpi.first.math.kinematics.ChassisSpeeds;

public class AutoUtils {
    public static Command driveForwardWhileScoring(RobotContainer robotContainer) {
        return robotContainer.driveSubsystem.drive(
                () -> new ChassisSpeeds(0.3, 0, 0),
                () -> false).withTimeout(400);
    }
}