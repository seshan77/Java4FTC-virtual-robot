package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="First Mecanum Autonomous", group="Linear Opmode")
//@Disabled
public class LinearAuto extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();


    DcMotor fr, fl, br, bl;
    IMU imu;


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");

        initialize();                                                   // Initialization of hardware
        waitForStart();                                                 // Wait on START click
        drive(8000, .8, 0.0,0.0, 0.0);
        drive(1000, .0, 0.0,0.0, 0.0);// Drive forward for 3000 ticks at a starting power of 0.5
        turn(90, 0.5);


    }

    public void initialize(){

        // Set the hardwareMap
        Utils.setHardwareMap(hardwareMap);

        // Get motors
        fr = hardwareMap.get(DcMotor.class, "front_right_motor");
        fl = hardwareMap.get(DcMotor.class, "front_left_motor");
        br = hardwareMap.get(DcMotor.class, "back_right_motor");
        bl = hardwareMap.get(DcMotor.class, "back_left_motor");

        // Set to give us full control
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set directions
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);

        // Our IMU Wrapper class instantiation
        imu = new IMU("imu");

    }


    /**
     * @param degrees
     * @param marginOfError
     */
    public void turn(double degrees, double marginOfError){

        // Your code here:



    }


    public void drive(int ticks, double targetPower, Double targetAngle, double startPower, double endPower) {

        double gain = 0.0000005;
        double accelRate = 0.1;
        double leftPower = startPower;
        double rightPower = startPower;
        double endDiff = Math.pow(targetPower, 2) - Math.pow(endPower, 2);
        double startDiff = Math.pow(targetPower, 2) - Math.pow(startPower, 2);

        double i = 0;

        resetMotors();
        runtime.reset();


        if (targetAngle == null) {
            targetAngle = imu.getAngle();
        }

        boolean accelerate = true;
        if (startPower >= targetPower) {
            accelerate = false;
        }

        boolean decelerate = false;
        double accelDistance;
        double decelDistance = (Math.pow(targetPower * 2500, 2) - Math.pow(endPower * 2500, 2)) / (200000 * accelRate);
        double currentTicks = 0;
        double absPower = 0;
        boolean endDecel = false;

        while (!endDecel) {

            if (targetPower > 0 && decelerate) {
                if ((rightPower + leftPower) / 2 <= endPower) {
                    endDecel = true;
                }
            } else if (targetPower < 0 && decelerate) {
                if ((rightPower + leftPower) / 2 >= endPower) {
                    endDecel = true;
                }
            }


            currentTicks = (fr.getCurrentPosition() + br.getCurrentPosition() + fl.getCurrentPosition() + bl.getCurrentPosition()) / 4.0;

            double remainingDistance = Math.abs(ticks) - Math.abs(currentTicks);

            absPower = Math.abs((leftPower + rightPower) / 2);

            //ACCELERATE
            if (accelerate && absPower < Math.abs(targetPower)) {
                if (targetPower > 0) {
                    leftPower += accelRate / 100000;
                    rightPower += accelRate / 100000;
                } else {
                    leftPower -= accelRate / 100000;
                    rightPower -= accelRate / 100000;
                }

                accelDistance = Math.abs(currentTicks);

                if (endDiff / startDiff <= 4) {
                    decelDistance = accelDistance * (endDiff / startDiff);
                }

            }

            //DECELERATE
            if (remainingDistance < decelDistance && absPower > Math.abs(endPower)) {
                decelerate = true;
            }


            if (decelerate) {
                accelerate = false;
                if (targetPower < 0) {
                    leftPower += accelRate / 100000;
                    rightPower += accelRate / 100000;
                } else {
                    leftPower -= accelRate / 100000;
                    rightPower -= accelRate / 100000;
                }
            }

            //PID
            leftPower -= (targetAngle - imu.getAngle()) * gain;
            rightPower += (targetAngle - imu.getAngle()) * gain;

            fr.setPower(rightPower);
            fl.setPower(leftPower);
            br.setPower(rightPower);
            bl.setPower(leftPower);

        }
        telemetry.addData("Status", "Run Time: " + runtime.seconds());
        telemetry.addData("Status", "End Power: " + absPower);
        telemetry.addData("Status", "Ticks Traveled: " + currentTicks);
        telemetry.update();


        setPowerAll(0);
    }



    /**
     * Helper method
     */
    public void resetMotors(){

        // Stop and Reset all encoders
        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Set mode back to RUN_WITHOUT_ENCODER
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


    /**
     * Helper method
     * @param power
     */
    public void setPowerAll(double power){

        // Set all the power here!
        fr.setPower(power);
        fl.setPower(power);
        br.setPower(power);
        bl.setPower(power);

    }
}
