package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;
import frc.robot.Constants;

public class Add extends Command{
    private Vision vision;

    public Add(Vision vision) {
        this.vision = vision;
    }

    public void initialize() {}

    public void execute() {
        vision.add();
    }

    public void end(boolean interrupted) {}

    public boolean isFinished() {
        return true;
    }
    
}