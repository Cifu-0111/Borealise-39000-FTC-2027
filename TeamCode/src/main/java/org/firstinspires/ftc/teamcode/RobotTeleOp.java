package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@TeleOp
public class RobotTeleOp extends OpMode{

    //MECANUM
    MecanumDrive drive = new MecanumDrive();
    MecanumIntake intake = new MecanumIntake();
    //Variables
    double forward, strafe, rotate;
    boolean intakeOn = false;
    boolean lastA = false;

    @Override
    public void init(){
        drive.init(hardwareMap);
        intake.init(hardwareMap);
    }
    @Override
    public void loop(){
        //Intake
        if (gamepad1.a && !lastA){ //si on a clique sur A: A = true & lastA = false, !lastA = true (true & true = compatible)
            intakeOn = !intakeOn; //intake = off, si on a clique intake = on
        }
        lastA = gamepad1.a;
        if (intakeOn) {
            intake.setMotorSpeed(1.0);//on
        }
        else {
            intake.setMotorSpeed(0.0);//off
        }


    //FieldOriented
    forward = gamepad1.left_stick_y;
    strafe = gamepad1.left_stick_x;
    rotate = gamepad1.right_trigger-gamepad1.left_trigger;

    drive.driveFieldRelative(forward,strafe,rotate);
}

}
