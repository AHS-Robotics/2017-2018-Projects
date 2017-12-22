package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Straight Four Pulley v0.0.1", group = "Cass")
public class StraightFourPulley extends LinearOpMode{
    private DcMotor motorFrontLeft;
    private DcMotor motorBackLeft;
    private DcMotor motorFrontRight;
    private DcMotor motorBackRight;
    private DcMotor pulleyLeft;
    private DcMotor pulleyRight;
    private final double MAX = 1.0;
    private final double MIN = -1.0;

    public void moveForward(double power){
        motorFrontLeft.setPower(power);
        motorBackLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorBackRight.setPower(power);
    }

    public void moveSingleForward(double power, DcMotor motor){
        motor.setPower(power);
    }
    public void moveSingleBackward(double power, DcMotor motor){
        moveSingleForward(-power, motor);
    }


    public void moveBackward(double power){
        moveForward(-power);
    }

    public void runOpMode(){
        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight");
        pulleyLeft = hardwareMap.dcMotor.get("pulleyLeft");
        pulleyRight = hardwareMap.dcMotor.get("pulleyRight");

        while(opModeIsActive()){
            double lXAxis = gamepad1.left_stick_x;
            double lYAxis = gamepad1.left_stick_y;
            double rXAxis = gamepad1.right_stick_x;
            double rYAxis = gamepad1.right_stick_y;

            waitForStart();

            if(lYAxis > 0 || lYAxis < 0){
                moveSingleForward((MAX * ((lYAxis < 0) ?  -1 : 1)), motorFrontLeft);
                moveSingleForward((MAX * ((lYAxis < 0) ?  -1 : 1)), motorBackLeft);
            }

            if(rYAxis > 0 || rYAxis < 0){
                moveSingleForward((MAX * ((rYAxis < 0) ?  -1 : 1)), motorFrontRight);
                moveSingleForward((MAX * ((rYAxis < 0) ?  -1 : 1)), motorBackRight);
            }

            // moving arm up and down
            if(gamepad1.left_bumper){
                double power = -MAX;
                pulleyRight.setDirection(DcMotorSimple.Direction.FORWARD);
                pulleyLeft.setDirection(DcMotorSimple.Direction.REVERSE);

                pulleyRight.setPower(power);
                pulleyLeft.setPower(power);
            }else if(gamepad1.right_bumper){
                double power = MAX;
                pulleyRight.setDirection(DcMotorSimple.Direction.REVERSE);
                pulleyLeft.setDirection(DcMotorSimple.Direction.FORWARD);

                pulleyRight.setPower(power);
                pulleyLeft.setPower(power);
            }else if(!gamepad1.left_bumper && !gamepad1.right_bumper){
                pulleyLeft.setPower(0);
                pulleyRight.setPower(0);
            }

            idle();
        }
    }
}
