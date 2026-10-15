package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp
public class RobotTeleOp extends OpMode{

    //DRIVE
    MecanumDrive drive = new MecanumDrive();
    MecanumIntake intake = new MecanumIntake();
    private OdometrySubsystem odometry;

    //Variables
    double forward, strafe, rotate;
    boolean intakeOn = false;
    boolean lastA = false;

    //Alliance
    boolean isRedAlliance = false;

    //AprilTag
    AprilTags  aprilTagWebcam = new AprilTags();

    @Override
    public void init(){
        odometry = new OdometrySubsystem(hardwareMap);
        drive.init(hardwareMap);
        intake.init(hardwareMap);
        aprilTagWebcam.init(hardwareMap, telemetry);
    }

    @Override
    public void init_loop(){
        //Selection de alliance pendant INIT
        if (gamepad1.x){
            isRedAlliance = true;
        }
        if (gamepad1.b){
            isRedAlliance = false;
        }

        telemetry.addData(
                "Alliance",
                isRedAlliance ? "RED" : "BLUE");
        telemetry.addLine("X = RED | B = BLUE");
        telemetry.update();
    }


    @Override
    public void loop(){
        //Odometry
        odometry.update();

        telemetry.addData("X (mm)", odometry.getX());
        telemetry.addData("Y (mm)", odometry.getY());
        telemetry.addData("Heading (deg)", Math.toDegrees(odometry.getHeading()));

        //AprilTags Webcam
        aprilTagWebcam.update();
        if (isRedAlliance){
            //Red Alliance: IDs du 30 au 37
            aprilTagWebcam.displayTagsInRange(30, 37);
        }
        else {
            //Blue Alliance: IDs du 38 au 45
            aprilTagWebcam.displayTagsInRange(38, 45);
        }
        telemetry.update();

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



