package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.LinearOpModePlus;

enum Diagonal{
    LEFT_FWD, LEFT_BCK, RIGHT_FWD, RIGHT_BCK, STOP
    // if stop is used that means all motor powers should be set to 0
}

@TeleOp(name="Base Module 2.1.b0", group="Building Block")
public class BaseModule extends LinearOpModePlus {
    private DcMotor motorLeftFront;
    private DcMotor motorLeftBack;
    private DcMotor motorRightFront;
    private DcMotor motorRightBack;
    private DcMotor motorLeftShaft;
    private DcMotor motorRightShaft;

    private double multiplierStorage = 0;
    private float lYAxis = 0, lXAxis = 0;

    private boolean leftRev = true; // if the value changes during updates different wheels will be reversed
    private boolean suspendAllMotors = false;


    /**
     * At the start of every run, variable information will be updated
     */
    public void update() {
        lYAxis = gamepad1.left_stick_y;
        lXAxis = gamepad1.left_stick_x;

        if (leftRev) {
            motorLeftBack.setDirection(DcMotorSimple.Direction.REVERSE);
            motorLeftFront.setDirection(DcMotorSimple.Direction.REVERSE);
            motorRightBack.setDirection(DcMotorSimple.Direction.FORWARD);
            motorRightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        } else {
            motorLeftBack.setDirection(DcMotorSimple.Direction.FORWARD);
            motorLeftFront.setDirection(DcMotorSimple.Direction.FORWARD);
            motorRightBack.setDirection(DcMotorSimple.Direction.REVERSE);
            motorRightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        }

        // This is experimental so tests are needed
        if (suspendAllMotors && multiplierStorage <= 0) {
            multiplierStorage = multiplier;
            multiplier = 0;
        } else if (!suspendAllMotors && multiplierStorage > 0) {
            multiplier = multiplierStorage;
            multiplierStorage = 0;
        }

    }

    private void turn(char dir) {
        dir = Character.toUpperCase(dir);
        DcMotor motorsWithChange[] = new DcMotor[2]; // this are the ones we'll have to reverse
        DcMotorSimple.Direction rev, fwd;

        if (dir == 'L') {
            motorsWithChange[0] = motorLeftBack;
            motorsWithChange[1] = motorLeftFront;
            rev = (leftRev) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE;
            fwd = (leftRev) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD;
            telemetry.addData("> ", "Turning Left");
            telemetry.update();
        } else if (dir == 'R') {
            motorsWithChange[0] = motorRightBack;
            motorsWithChange[1] = motorLeftFront;
            rev = (leftRev) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD;
            fwd = (leftRev) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE;
            telemetry.addData("> ", "Turning Left");
            telemetry.update();
        } else{
            telemetry.addData("> ", "Bad Input");
            telemetry.update();
            return;
        }

        for (DcMotor m : motorsWithChange) m.setDirection(rev); // setting the reverse directions of motors

        while (((dir == 'L' && dir != 'R') ? gamepad1.left_trigger : gamepad1.right_trigger) > 0) {
            motorLeftFront.setPower(MAX_CAP * multiplier);
            motorLeftBack.setPower(MAX_CAP * multiplier);
            motorRightFront.setPower(MAX_CAP * multiplier);
            motorRightBack.setPower(MAX_CAP * multiplier);
        }

        for (DcMotor m : motorsWithChange) m.setDirection(fwd); // setting directions back to normal and we should be good

    }

    public void moveForward(double power) {
        DcMotor motors[] = {motorLeftFront, motorLeftBack, motorRightFront, motorRightBack};
        DcMotorSimple.Direction leftDir = (leftRev) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD;
        DcMotorSimple.Direction rightDir = (leftRev) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE;

        motorLeftFront.setDirection(leftDir);
        motorLeftBack.setDirection(leftDir);
        motorRightFront.setDirection(rightDir);
        motorRightBack.setDirection(rightDir);

        for (DcMotor m : motors) m.setPower(power * multiplier);
    }

    public void moveBackward(double power) {
        moveForward(power);
    }

    public void moveLeft(double power) {
        motorLeftBack.setDirection((motorLeftBack.getDirection() == DcMotorSimple.Direction.REVERSE) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE);
        motorLeftFront.setPower(power);
        motorLeftBack.setPower(power);
        
        motorRightFront.setPower(0);
        motorRightBack.setPower(0);
    }

    public void moveRight(double power) {
        motorLeftFront.setPower(0);
        motorLeftBack.setPower(0);
        motorRightFront.setDirection((motorRightFront.getDirection() == DcMotor.Direction.FORWARD) ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.REVERSE);
        motorRightFront.setPower(power);
        motorRightBack.setPower(power);
    }

    public void diagonal(Diagonal d, double power){
        switch(d){
            case LEFT_BCK:
                motorLeftFront.setPower(power);
                motorRightBack.setPower(power);
                break;
            case LEFT_FWD:
                motorLeftBack.setPower(power);
                motorRightFront.setPower(power);
                break;
            case RIGHT_BCK:
                motorRightFront.setPower(power);
                motorLeftBack.setPower(power);
                break;
            case RIGHT_FWD:
                motorLeftFront.setPower(power);
                motorRightBack.setPower(power);
                break;
            default:
                moveForward(0);
                break;
        }
    }
    
    public void moveShaft(char dir){
        if(dir == 'S'){
            motorLeftShaft.setPower(0);
            motorRightShaft.setPower(0);
        }else if(dir == 'U'){
            motorLeftShaft.setPower(MAX_CAP * multiplier);
            motorRightShaft.setPower(MAX_CAP * multiplier);
        }else if(dir == 'D'){
            motorLeftShaft.setPower((MAX_CAP * multiplier) * -1.0);
            motorRightShaft.setPower((MAX_CAP * multiplier) * -1.0);
        }else{
            telemetry.addData("> ", "Unknown direction");
            telemetry.update();
        }
    }

    @Override
    public void runOpMode(){
        motorLeftBack = setMotor("motorLeftBack");
        motorLeftFront = setMotor("motorLeftFront");
        motorRightBack = setMotor("motorRightBack");
        motorRightFront = setMotor("motorRightFront");
        motorLeftShaft = setMotor("motorLeftShaft");
        
        telemetry.addData("> ", "Ready to go!");
        telemetry.update();
        waitForStart();

        while(opModeIsActive()){
            update(); // keeping data up to date

            // here are some control options by reversing booleans
            if(gamepad1.a) leftRev = (leftRev) ? false:true;
            if(gamepad1.back) suspendAllMotors = (suspendAllMotors) ? false:true;

            if(gamepad1.left_trigger > 0) turn('L');
            else if(gamepad1.right_trigger > 0) turn('R');
            else{
                if(lYAxis > 0 && lXAxis == 0) moveForward(lYAxis * multiplier);
                else if(lYAxis < 0 && lXAxis == 0) moveBackward(lYAxis * multiplier);
                else if(lYAxis > 0 && lXAxis < 0) diagonal(Diagonal.LEFT_FWD, MAX_CAP * multiplier);
                else if(lYAxis > 0 && lXAxis > 0) diagonal(Diagonal.RIGHT_FWD, MAX_CAP * multiplier);
                else if(lYAxis < 0 && lXAxis < 0) diagonal(Diagonal.LEFT_BCK, MAX_CAP * multiplier);
                else if(lYAxis < 0 && lXAxis > 0) diagonal(Diagonal.RIGHT_BCK, MAX_CAP * multiplier);
                else if(lXAxis < 0 && lYAxis == 0) moveLeft(MAX_CAP * multiplier);
                else if(lXAxis > 0 && lYAxis == 0) moveRight(MAX_CAP * multiplier);
                else moveForward(0);

            }
            
            if(gamepad2.dpad_up) moveShaft('U');
            else if(gamepad2.dpad_down) moveShaft('D');
            else if(!gamepad2.dpad_down || !gamepad2.dpad_up) moveShaft('S');
            else ;

            idle();
        }
    }
}

/*
* TODO
* [] Test the left and right direction
* [] Refactor the code so that all the reversing of directions can be done in a method instead of a long ass statement
* */
