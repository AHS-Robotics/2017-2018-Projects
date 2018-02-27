package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name = "Color Sensor", group = "Testing")
public class ColorSenors extends LinearOpMode{
    ColorSensor sensor;
    public void runOpMode(){
        sensor = hardwareMap.colorSensor.get("sensor");
        sensor.enableLed(true);
        waitForStart();
        while(opModeIsActive()){
            telemetry.addData(">> ", "ALPHA: " + sensor.alpha() +
            "\nRED: " + sensor.red() +
            "\nGREEN: " + sensor.green()+
            "\nBLUE: " + sensor.blue());
            telemetry.update();
        }

    }
}
