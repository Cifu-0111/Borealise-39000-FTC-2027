package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class Servos extends OpMode {
    MecanumFlower flower = new MecanumFlower();
    boolean flowerInPosition = false;
    boolean lastB = false;

    @Override
    public void init(){
        flower.init(hardwareMap);
    }

    @Override
    public void loop(){
        if(gamepad1.b && !lastB){
            flowerInPosition = !flowerInPosition;
        }
        lastB = gamepad1.b;
        if (flowerInPosition) {
            flower.setServoPosition(0.7);
        }
        else{
            flower.setServoPosition(0.0);
        }
    }
}
