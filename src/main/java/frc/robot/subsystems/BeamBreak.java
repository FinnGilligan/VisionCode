package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BeamBreak {
    private DigitalInput input = new DigitalInput(2);

    public BeamBreak() {
    }

    public void periodic() {
        SmartDashboard.putBoolean("Beam Break", input.get());
    }
    
}
