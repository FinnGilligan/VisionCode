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
import frc.robot.Constants;
import frc.robot.LimelightHelpers;

public class Vision extends SubsystemBase {
  private final NetworkTable m_limelightTable;
  private double tv, tx, ta, ty, tid;
  private ArrayList<Double> m_targetList;
  private final int MAX_ENTRIES = 50;
  private final NetworkTableEntry m_led_entry;
  private final DoubleArraySubscriber botposeSigh;
  private int idIndex = 0;

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

    LimelightHelpers.LimelightResults llresults = LimelightHelpers.getLatestResults("");
    LimelightHelpers.LimelightTarget_Fiducial[] llArr = llresults.targetingResults.targets_Fiducials;


    if(llArr.length > 0) {
        SmartDashboard.putNumber("Distance", getDistance());
        SmartDashboard.putNumber("X", llArr[0].getRobotPose_FieldSpace().getX());
        SmartDashboard.putNumber("Y", llArr[0].getRobotPose_FieldSpace().getY());
        SmartDashboard.putNumber("Z", llArr[0].getRobotPose_FieldSpace().getZ());
        SmartDashboard.putNumber("Roll", llArr[0].getRobotPose_FieldSpace().getRotation().getX());
        SmartDashboard.putNumber("Pitch", llArr[0].getRobotPose_FieldSpace().getRotation().getY());
        SmartDashboard.putNumber("Yaw", llArr[0].getRobotPose_FieldSpace().getRotation().getZ());
    }
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

  public double getIDIndex(){
    return idIndex;
  }

  public boolean isTargetValid() {
    return (tv == 1.0); 
  }

  public void add(){
    idIndex++;
    idIndex %= Constants.OperatorConstants.idsLength;
  }

}