/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;

import edu.wpi.first.networktables.DoubleArraySubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
  private final NetworkTable m_limelightTable;
  private double tv, tx, ta, ty, tid;
  private ArrayList<Double> m_targetList;
  private final int MAX_ENTRIES = 50;
  private final NetworkTableEntry m_led_entry;
  private final DoubleArraySubscriber botposeSigh;

  /**
   * Creates a new Vision.
   */
  public Vision() {
    m_limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
    m_targetList = new ArrayList<Double>(MAX_ENTRIES);
    m_led_entry = m_limelightTable.getEntry("ledMode");
    botposeSigh = m_limelightTable.getDoubleArrayTopic("botpose").subscribe(new double[] {});
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    tv = m_limelightTable.getEntry("tv").getDouble(0);
    tx = m_limelightTable.getEntry("tx").getDouble(0);
    ty = m_limelightTable.getEntry("ty").getDouble(0);
    ta = m_limelightTable.getEntry("ta").getDouble(0);
    tid = m_limelightTable.getEntry("tid").getDouble(0);
    double[] botposeArray = botposeSigh.get();
    //x, y, z, roll, pitch, yaw


    if (m_targetList.size() >= MAX_ENTRIES) {
      m_targetList.remove(0);
    }
    
    m_targetList.add(ta);

    SmartDashboard.putNumber("Distance", getDistance());
    SmartDashboard.putNumber("X", botposeArray[0]);
    SmartDashboard.putNumber("Y", botposeArray[1]);
    SmartDashboard.putNumber("Z", botposeArray[2]);
    SmartDashboard.putNumber("Roll", botposeArray[3]);
    SmartDashboard.putNumber("Pitch", botposeArray[4]);
    SmartDashboard.putNumber("Yaw", botposeArray[5]);
  }

  public double getTX() {
    return tx;
  }

  public double getTY() {
    return ty;
  }

  public double getTA() {
    double sum = 0;

    for (Double num : m_targetList) { 		      
      sum += num.doubleValue();
    }
    return sum/m_targetList.size();
  }

  public double getDistance() {
    return 38 * Math.tan((90-26-getTY()) * Math.PI/180) - 2;
  }

  public double getTID() {
    return tid;
  }

  public boolean isTargetValid() {
    return (tv == 1.0); 
  }
}