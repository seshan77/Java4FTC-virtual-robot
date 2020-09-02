package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Utils {

    public static HardwareMap hardwareMap;

    public static void setHardwareMap(HardwareMap hardwareMap){
        Utils.hardwareMap = hardwareMap;
    }

    public static HardwareMap getHardwareMap(){
        return hardwareMap;
    }

}
