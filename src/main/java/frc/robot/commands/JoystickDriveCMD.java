package frc.robot.commands;
import java.util.function.Supplier;

import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class JoystickDriveCMD extends CommandBase {
    private Drivetrain runt;
    private Supplier<Double> driveSpd, turnSpd;

    public JoystickDriveCMD(Drivetrain runt, Supplier<Double> turnSpd, Supplier<Double> driveSpd) {
        this.runt = runt;
        this.driveSpd = driveSpd;
        this.turnSpd = turnSpd;
        addRequirements(runt);
    }

    public void initialize() {}

    @Override
    public void execute() {
        double left = driveSpd.get() + turnSpd.get();
        double right = driveSpd.get() - turnSpd.get();

        runt.setMotors(0.1 * left, 0.1 * right);
    }

    @Override
    public void end(boolean interrupted) {}

    @Override
    public boolean isFinished() {
        return false;
    }
}