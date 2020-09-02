package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class IMU {

    private BNO055IMU imu;
    private HardwareMap hardwareMap;

    public IMU(String deviceName){

        hardwareMap = Utils.getHardwareMap();                           // Access static hardwareMap for easier accessibility
        imu = hardwareMap.get(BNO055IMU.class, deviceName);             // Retrieve the device from the default IMU Hardware Class
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();   // Retrieve necessary parameters
        imu.initialize(parameters);                                     // Initialize it with the given parameters


    }

    /**
     * @return angle
     */
    public double getAngle(){

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);      // Retrieve ZYX angles in that order
        double angle = angles.firstAngle;                                                                               // Only grab the Z angle

        angle = wrap(angle);

        return angle;
    }

    public double wrap(double angle){

        // Your code here!!

        return angle;
    }

}
