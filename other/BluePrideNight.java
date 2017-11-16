package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Blue Pride Night 0.1.b0", group="Special")
public class BluePrideNight extends LinearOpModePlus{

    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor motorRightBack;
    private DcMotor motorSweeper;
    private Servo arm;

    private final double DOWN_POSITION = 0.0;
    private final double MID_POSITION = 0.5;
    private final double UP_POSITION = 1.0;

    private boolean multistick;

    public void runOpMode(){
        motorLeft = setMotor("motorLeft");
        motorRight = setMotor("motorRight");
        motorSweeper = setMotor("motorSweeper");
        arm = setServo("arm");


        multistick = true;

        setDirection(motorLeft, DcMotorSimple.Direction.REVERSE);


        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.a) multistick = !multistick;

            if(gamepad1.b || gamepad1.dpad_right) motorSweeper.setPower(MAX_CAP * multiplier);
            else if(gamepad1.x || gamepad1.dpad_left) motorSweeper.setPower(-(MAX_CAP * multiplier));
            else motorSweeper.setPower(STOP);

            if(!multistick) {
                motorLeft.setPower(-gamepad1.left_stick_y);
                motorRight.setPower(-gamepad1.left_stick_y);
            }else{
                motorLeft.setPower(-gamepad1.left_stick_y);
                motorRight.setPower(-gamepad1.right_stick_y);
            }

            if(gamepad1.dpad_up) arm.setPosition(UP_POSITION);
            else if(gamepad1.dpad_down) arm.setPosition(DOWN_POSITION);

            idle();
        }
    }

}

