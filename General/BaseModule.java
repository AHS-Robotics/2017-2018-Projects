package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.LinearOpModePlus;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

enum Diagonal{
    RIGHT_FWRD, RIGHT_BACK, LEFT_FWRD, LEFT_BACK
}

@TeleOp(name="Base Module 1.0.0", group="Building Blocks")
public class BaseModule extends LinearOpModePlus{
    private final double SLEEP_TIME = 1000;
    private double power = 0;
    private double lXAxis, lYAxis;
    private double rXAxis, rYAxis;

    private DcMotor motorLeftBack;
    private DcMotor motorLeftFront;
    private DcMotor motorRightBack;
    private DcMotor motorRightFront;
    
    private boolean testDiagonal = true;
    private boolean updateTele = true;
    
    // all of the 'turnX' methods will be defined later
    public void turnLeft(){
        try{
            motorLeftFront.setPower(MAX_CAP * multiplier);
            motorLeftBack.setPower(MAX_CAP * multiplier);
            Thread.sleep(SLEEP_TIME);
            motorLeftFront.setPower(0);
            motorLeftBack.setPower(0);
            
        }catch(InterruptedException e){
            // we'll do something here but not now
        }
    }
    
    public void update(){
        // This always has to run
        lYAxis = gamepad1.left_stick_y;
        lXAxis = gamepad1.left_stick_y;
    }
    
    public void runDiagonal(Diagonal d){
        switch(d){
            case Diagonal.LEFT_FWRD:
                motorLeftBack.setPower(-gamepad1.left_stick_x * multiplier);
                motorRightFront.setPower(-gamepad1.left_stick_x * multiplier);
                motorLeftFront.setPower(-gamepad1.left_stick_y * multiplier);
                motorRightBack.setPower(-gamepad1.left_stick_y * multiplier);
                break;
            case Diagonal.LEFT_BACK:
                motorLeftFront.setPower(-gamepad1.left_stick_x * multiplier);
                motorRightBack.setPower(-gamepad1.left_stick_x * multiplier);
                motorLeftBack.setPower(-gamepad1.left_stick_y * multiplier);
                motorRightFront.setPower(-gamepad1.left
                break;
            case Diagonal.RIGHT_FWRD:
                motorLeftFront.setPower(-gamepad1.left_stick_x * multiplier);
                motorRightBack.setPower(-gamepad1.left_stick_x * multiplier);
                motorLeftBack.setPower(-gamepad1.left_stick_y * multiplier);
                motorRightFront.setPower(-gamepad1.left_stick_y * multiplier);
                break;
            case Diagonal.RIGHT_BACK:
                motorLeftBack.setPower(-gamepad1.left_stick_x * multiplier);
                motorRightFront.setPower(-gamepad1.left_stick_x * multiplier);
                motorLeftFront.setPower(-gamepad1.left_stick_y * multiplier);
                motorRightBack.setPower(-gamepad.left_stick_y * multiplier);
                break;
            default:
                // we'll print an error message
                break;
        }
    }

    @Override
    public void runOpMode(){
        // Initialization
        motorLeftBack = setMotor("motorLeftBack");
        motorLeftFront = setMotor("motorLeftFront");
        motorRightBack = setMotor("motorRightBack");
        motorRightFront = setMotor("motorRightFront");

        motorLeftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        motorLeftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive()){
            update();

            // changing some variables
            if(gamepad1.dpad_left) decPower();
            if(gamepad1.dpad_right) incPower();
            if(gamepad.a) turnLeft();

            if(lYAxis <= 1.0 || lYAxis >= -1.0 && lXAxis == 0.0) {
                // moving forward or backward
                motorLeftBack.setPower(-gamepad1.left_stick_y * multiplier);
                motorLeftFront.setPower(-gamepad1.left_stick_y * multiplier);
                motorRightBack.setPower(-gamepad1.left_stick_y * multiplier);
                motorRightFront.setPower(-gamepad1.left_stick_y * multiplier);
            }else if(lXAxis >= -1.0 && lXAxis < 0 && lYAxis == 0){
                // moving left
                motorLeftFront.setPower(-gamepad1.left_stick_x * multiplier);
                motorRightBack.setPower(-gamepad1.left_stick_x * multiplier);
            }else if(lXAxis <= 1.0 && lXAxis > 0 && lYAxis == 0){
                // moving right
                motorLeftBack.setPower(-gamepad1.left_stick_x * multiplier);
                motorRightFront.setPower(-gamepad1.left_stick_x * multiplier)
            }else{
                if(lXAxis > lYAxis && lYAxis > 0 && testDiagonal) runDiagonal(Diagonal.RIGHT_FWRD);
                else if(lXAxis < 0 && lYAxis > 0 && testDiagonal) runDiagonal(Diagonal.LEFT_FWRD);
                else if(lXAxis > 0 && lYAxis < 0 && testDiagonal) runDiagonal(Diagonal.RIGHT_BACK);
                else if(lXAxis < 0 && lYAxis > 0 && testDiagonal) runDiagonal(Diagonal.LEFT_BACK);
                else // here we'll print a message to the driver theres a bad diagonal   
            }

            idle();
        }
}
