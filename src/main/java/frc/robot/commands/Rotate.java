package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Vision;
import frc.robot.Constants;

public class Rotate extends Command{

    private Drivetrain drivetrain;
    private Vision vision;
    private Supplier<Double> tx;
    private Supplier<Double> ty;
    private Supplier<Double> ta;
    private Supplier<Double> tid;
    private final double kP = 1.0/104;
    private Double[] ids = Constants.OperatorConstants.id_Blue;

    public Rotate(Drivetrain drivetrain, Vision vision, Supplier<Double> tx, Supplier<Double> ty, Supplier<Double> ta, Supplier<Double> tid) {
        this.drivetrain = drivetrain;
        this.vision = vision;
        this.tx = tx;
        this.ty = ty;
        this.ta = ta;
        this.tid = tid;

        addRequirements(drivetrain);
    }

    public void initialize() {}

    public void execute() {
        double left = 0;
        double right = 0;

        if(tid.get() == ids[(int)(vision.getIDIndex())]){
            if(tx.get() < 0 && tx.get() > -26 && ta.get() > 0) {
                left += kP * tx.get();
                right += -kP * tx.get();
            }
    
            else if(tx.get() > 0 && tx.get() < 26 && ta.get() > 0) {
                left += kP * tx.get();
                right += -kP * tx.get(); 
            }
    
            if(ty.get() < 16 && ty.get() > -26 && ta.get() > 0) {
                left -= kP * (ty.get() - 16);
                right -= kP * (ty.get() - 16);
            }
    
            else if(ty.get() > 16 && ty.get() < 26 && ta.get() > 0) {
                left -= kP * (ty.get() - 16);
                right -= kP * (ty.get() - 16);
            }
        }

        if(ta.get() > 0) {
            drivetrain.setMotors(left, right);
        }

        else {
            drivetrain.setMotors(-0.15, 0.15);
        }
    }

    public void end(boolean interrupted) {}

    public boolean isFinished() {
        return (tx.get() > -1 && tx.get() < 1 && ty.get() > 15 && ty.get() < 17);
    }
    
}