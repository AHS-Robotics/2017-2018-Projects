package org.firstinspires.ftc.teamcode;
/*
* LinearOpModePlus v1.0.0
*
* The following code is not a working TeleOp programming. It adds in some extra methods
* and variables that may provide extra functionality to your program.
* */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

public class LinearOpModePlus extends LinearOpMode{

    // Magic numbers defined
    protected final double MAX_CAP = 1.0;
    protected final double MIN_CAP = -1.0;
    protected final double INCREMENTER = 0.1; // controls how fast the multiplier changes by

    protected double multiplier = 1.0; // the power cap

    protected String output = ""; // variable that will store output to the console allows for easier manipulation of data

    /* Experimental hardware declarations */
    public DcMotor setMotor(String name){return hardwareMap.dcMotor.get(name);}

    /* All the methods that have to do with power multipliers */
    /**
     * increments the power cap as long as it is not already maxed out
     * */
    public void incPower(){
        if(multiplier < MAX_CAP) multiplier += INCREMENTER;
        else{
            addData("\nError: multiplier is capped at its max");
            print(output);
        }
    }

    /**
     * decrements the power cap as long as it is not already at its lowest value
     * */
    public void decPower(){
        if(multiplier > MIN_CAP) multiplier -= INCREMENTER;
        else{
            addData("\nError: multiplier is lowest value");
            print(output);
        }
    }

    /* All the methods that do with printing */
    /**
     * prints content using telemetry at the bottom of the FTC Driver Station
     * @param s the string of text that you want to add into the telemetry
     * */
    public void print(String s){
        telemetry.addData("> ", s);
        telemetry.update();
    }

    /**
     * clears the telemetry and depending on argument will also the output variable
     * @param wipeOutput if true instance variable output will be cleared
     * */
    public void clear(boolean wipeOutput){
        if(wipeOutput) output = "";
        telemetry.clear();
    }

    /**
     * Will clear the value of output and clear telemetry
     * */
    public void clear(){
        clear(true);
    }

    /* All of the following deals with moving data into the output variable
     * covers strings and primitive data types*/
    public void addData(String dat){output += dat;}
    public void addData(char dat){output += dat;}
    public void addData(byte dat){output += dat;}
    public void addData(short dat){output += dat;}
    public void addData(int dat){output += dat;}
    public void addData(long dat){output += dat;}
    public void addData(float dat){output += dat;}
    public void addData(double dat){output += dat;}

    /**
     * adds a new line to the output variable so future text will be written on a new line
     * */
    public void addLine(){output += '\n';}


    public void refreshTelemetry(){
        clear(false);
        print(output);
    }


    public void runOpMode() {
        ; // only here so the class can extend LinearOpMode
    }
}