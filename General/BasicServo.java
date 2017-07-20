// Package here

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


public class BasicServo extends LinearOpMode{
    private Servo servo;
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private double positionAccelerator = 0.1;


    public void runOpMode(){
        servo = hardwareMap.servo.get("servo");
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");

        servo.getController().pwmEnable();
        motorLeft.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive()){
            double currentPosition = servo.getPosition();

            if(currentPosition == 1.0){
                servo.setPosition(0.0);
            }else{
                servo.setPosition(currentPosition + positionAccelerator);
            }

            motorLeft.setPower(-gamepad1.left_stick_y);
            motorRight.setPower(-gamepad1.right_stick_y);

        }


    }

}
