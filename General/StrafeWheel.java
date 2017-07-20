// Package goes here

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/*
* For a robot that has three motors, two to drive and one to strafe left or right or forwards and backwards.
* Feel free to take this if you'll need it for another TeleOp or you need to modify left and right to up and down
* Left -> Minus Power
* Right -> Add Power
* */

@TeleOp(name="Strafe Wheel v1.0.0", group="General")
public class StrafeWheel extends LinearOpMode{
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor strafeMotor;
    private boolean debugInfo = false;
    private double strafeAccelerationRate = 0.1;

    public void runOpMode(){
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        strafeMotor = hardwareMap.dcMotor.get("strafeMotor");

        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        while(opModeIsActive()){
            boolean strafeLeft = gamepad1.dpad_left;
            boolean strafeRight = gamepad1.dpad_right;

            motorLeft.setPower(-gamepad1.left_stick_y);
            motorRight.setPower(-gamepad1.right_stick_y);

            double currentStrafePower = strafeMotor.getPower();
            if(strafeLeft){
                if(currentStrafePower <= -1.0){
                    continue;
                }else{
                    strafeMotor.setPower(currentStrafePower - strafeAccelerationRate);
                }
            } else if (strafeRight) {
                if(currentStrafePower >= 1.0){
                    continue;
                }else{
                    strafeMotor.setPower(currentStrafePower + strafeAccelerationRate);
                }
            }else{
                strafeMotor.setPower(0);
            }
        }

    }
}
