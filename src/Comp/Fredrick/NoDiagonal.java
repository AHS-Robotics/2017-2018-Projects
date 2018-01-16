

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="No Diagonal", group="Testing")
public class NoDiagonal{
  private DcMotor frontLeft;
  private DcMotor backLeft;
  private DcMotor frontRight;
  private DcMotor backRight;
  private boolean pause;
  private boolean buttonHeld;
  private double range;
  
  public DcMotor setMotor(String name){
        return hardwareMap.dcMotor.get(name);
    }

    /**
     * sets the power of a single motor
     * @param motor motor that we are setting the power for
     * @param pow the power level
     */
    public void setPower(DcMotor motor, double pow){
        motor.setPower(pow);
    }


    /**
     * sets motors to move the robot left
     * @param pow the power level
     * */
    public void moveLeft(double pow){
        frontLeft.setPower(-pow);
        backLeft.setPower(pow);
        frontRight.setPower(pow);
        backRight.setPower(-pow);
    }

    /**
     * sets motors to move the robot right
     * @param pow the power level
     */

    public void moveRight(double pow){
        frontRight.setPower(-pow);
        backRight.setPower(pow);
        frontLeft.setPower(pow);
        backLeft.setPower(-pow);
    }

    /**
     * turns the robot right
     * @param pow the power we set the motor to
     */

    public void turnRight(double pow){
        frontLeft.setPower(pow);
        frontRight.setPower(-pow);
        backRight.setPower(-pow);
        backLeft.setPower(pow);
    }

    /**
     * turns the robot left
     * @param pow the power we set the motor to
     */
    public void turnLeft(double pow){
        frontLeft.setPower(-pow);
        backLeft.setPower(-pow);
        backRight.setPower(pow);
        frontRight.setPower(pow);
    }

    /**
     * moves the robot in a left front diagonal direction
     * @param pow the power we set the motor to
     */
    public void leftFrontDiagonal(double pow){
        backLeft.setPower(pow);
        frontRight.setPower(pow);
    }

    /**
     * move the robot in a right front diagonal direction
     * @param pow the power we set the motor to
     */
    public void rightFrontDiagonal(double pow){
        frontLeft.setPower(pow);
        backRight.setPower(pow);
    }

    /**
     * move the robot in a left back diagonal direction
     * @param pow the power we set the motor to
     */
    public void leftBackDiagonal(double pow){
        rightFrontDiagonal(-pow);
    }

    /**
     * move the robot in a right back diagonal direction
     * @param pow the power we set the motor to
     */
    public void rightBackDiagonal(double pow){
        leftFrontDiagonal(-pow);
    }
  
    public void driveForward(double pow){
        frontLeft.setPower(pow);
        frontRight.setPower(pow);
        backLeft.setPower(pow);
        backRight.setPower(pow);
    }
  
    public void driveBack(double pow){
        frontLeft.setPower(-pow);
        frontRight.setPower(-pow);
        backLeft.setPower(-pow);
        backRight.setPower(-pow);
    }
  
    public void runOpMode(){
        pause = false;
        buttonHeld = false;
        range = 0.8;
        

        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        while(opModeIsActive()){
          if(gamepad1.start) pause = !pause;

          if(pause){
            if(gamepad1.dpad_left && !buttonHeld){
              buttonHeld = true;
              if(range > 0.0) range -= 0.01;           
            }else if(gamepad1.dpad_right && !buttonHeld){
              buttonHeld = true;
              if(range < 1.0) range += 0.01;
            }else if(!gamepad1.dpad_left && !gamepad1.dpad_left && buttonHeld) buttonHeld = false;        
            
            continue;
          }
          
          if(gamepad1.left_stick_y > 0) driveBack(0.5);
          if(gamepad1.left_stick_y < 0) driveForward(0.5);
          if(gamepad1.left_stick_x < 0) moveLeft(0.5);
          if(gamepad1.left_stick_x > 0) moveRight(0.5);
          if(gamepad1.left_stick_y > range && gamepad1.left_stick_x > range) leftFrontDiagonal(-0.5);
          if(gamepad1.left_stick_y > range && gamepad1.right_stick_x < -range) rightFrontDiagonal(-0.5);
          if(gamepad1.left_stick_y < -range && gamepad1.left_stick_x > range) leftBackDiagonal(-0.5);
          if(gamepad1.left_stick_y < -range && gamepad1.left_stick_x < -range) rightBackDiagonal(-0.5);

          if(gamepad1.right_trigger > 0){
                turnRight(0.5);
          }if(gamepad1.left_trigger > 0){
                turnLeft(0.5);
          }
      }
    
  }
}
