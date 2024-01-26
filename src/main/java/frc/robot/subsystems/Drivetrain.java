package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase{

    private CANSparkMax frontLeft = new CANSparkMax(2, MotorType.kBrushless);
    private CANSparkMax backLeft = new CANSparkMax(1, MotorType.kBrushless);
    private CANSparkMax frontRight = new CANSparkMax(4, MotorType.kBrushless);
    private CANSparkMax backRight = new CANSparkMax(3, MotorType.kBrushless);

    public Drivetrain() {
    }

    public void setMotors(double left, double right) {
        frontLeft.set(left);
        backLeft.set(left);
        frontRight.set(-right);
        backRight.set(right);
    }
}
