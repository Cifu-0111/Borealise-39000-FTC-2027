package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.ArrayList;
import java.util.List;

public class AprilTags {
    private static final String WEBCAM_NAME = "webcam_1";

    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;
    private List<AprilTagDetection> detectedTags = new ArrayList<>();

    private Telemetry telemetry;

    public void init(HardwareMap hwMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
                .build();

        // Decimation mas alta = mas FPS pero menos alcance. 2 es un buen balance.
        aprilTagProcessor.setDecimation(2);

        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hwMap.get(WebcamName.class, WEBCAM_NAME));
        builder.setCameraResolution(new Size(640, 480));
        builder.addProcessor(aprilTagProcessor);

        visionPortal = builder.build();
    }

    // Llamar UNA vez por ciclo del loop()
    public void update() {
        detectedTags = aprilTagProcessor.getDetections();
    }

    public List<AprilTagDetection> getDetectedTags() {
        return detectedTags;
    }

    // ---------- Busqueda ----------

    // Tag con un ID exacto. Devuelve null si no esta a la vista.
    public AprilTagSingleDetection getSpecificTagId(int id) {
        for (AprilTagDetection detection : detectedTags) {
            if (detection instanceof AprilTagSingleDetection) {
                AprilTagSingleDetection single = (AprilTagSingleDetection) detection;
                if (single.id == id) {
                    return single;
                }
            }
        }
        return null;
    }

    // Todos los tags visibles cuyo ID esta entre minId y maxId (inclusive)
    public List<AprilTagSingleDetection> getTagsInRange(int minId, int maxId) {
        List<AprilTagSingleDetection> result = new ArrayList<>();
        for (AprilTagDetection detection : detectedTags) {
            if (detection instanceof AprilTagSingleDetection) {
                AprilTagSingleDetection single = (AprilTagSingleDetection) detection;
                if (single.id >= minId && single.id <= maxId) {
                    result.add(single);
                }
            }
        }
        return result;
    }

    // El tag mas cercano del rango. Devuelve null si no hay ninguno con pose.
    // (ftcPose es null en tags que no estan en la libreria del juego)
    public AprilTagSingleDetection getClosestTagInRange(int minId, int maxId) {
        AprilTagSingleDetection closest = null;
        for (AprilTagSingleDetection tag : getTagsInRange(minId, maxId)) {
            if (tag.ftcPose == null) {
                continue;
            }
            if (closest == null || tag.ftcPose.range < closest.ftcPose.range) {
                closest = tag;
            }
        }
        return closest;
    }

    // ---------- Telemetry ----------

    // cuantos tags de mi alianza veo y cual es el mas cercano
    public void displayTagsInRange(int minId, int maxId) {
        List<AprilTagSingleDetection> tags = getTagsInRange(minId, maxId);
        telemetry.addData("Tags de mi alianza", tags.size());

        AprilTagSingleDetection closest = getClosestTagInRange(minId, maxId);
        if (closest == null) {
            telemetry.addLine("Ningun tag visible");
            return;
        }
        telemetry.addData("Mas cercano", "ID %d | %.0f cm | bearing %.1f deg",
                closest.id, closest.ftcPose.range, closest.ftcPose.bearing);
    }

    // Detalle completo de un tag (para depurar)
    public void displayDetectionTelemetry(AprilTagDetection detection) {
        if (detection == null) {
            return;
        }
        if (detection instanceof AprilTagSingleDetection) {
            AprilTagSingleDetection singleDet = (AprilTagSingleDetection) detection;

            if (singleDet.metadata != null) {
                telemetry.addLine(String.format("\n==== (ID %d) %s", singleDet.id, singleDet.metadata.name));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (cm)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (cm, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            } else {
                telemetry.addLine(String.format("\n==== (ID %d) Unknown", singleDet.id));
                telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", singleDet.center.x, singleDet.center.y));
            }
        } else {
            AprilTagClusterDetection clusterDet = (AprilTagClusterDetection) detection;
            telemetry.addLine(String.format("\n==== Tag Cluster (%s)", clusterDet.metadata.name));
            telemetry.addLine(String.format("Percent tags found: %d", clusterDet.percentClusterFound));
            telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (cm)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
            telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
            telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (cm, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
        }
    }

    public void stop() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }
}