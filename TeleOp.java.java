package org.firstinspires.ftc.teamcode.RobotController;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;



public class TeleOp extends LinearOpMode {

    private DcMotor MotAVD0, MotARD1, MotAVG2, MotARG3;

    public void runOpMode() {

        initMoteurs();
        

        telemetry.addData("Statut", "Initialisé");
        telemetry.update();


        waitForStart();


        while (opModeIsActive()) {
                //boutons de la manette inutilisés: Tous saud joysticks
            
                //récupération des joysticks de la manette

                double y = -gamepad1.left_stick_y;  // forward/back
                double x = gamepad1.left_stick_x;   // strafe
                double rx = gamepad1.right_stick_x; // rotate
                
                //formule mouvement Mecanum 

                double AVG = y + x + rx;
                double AVD = y - x - rx;
                double ARG = y - x + rx;
                double ARD = y + x - rx;

                //contrôle moteurs mouvement
            
                MotAVD0.setPower(AVD);
                MotARD1.setPower(ARD);
                MotAVG2.setPower(AVG);
                MotARG3.setPower(ARG);
        }
    }


    public void initMoteurs() {
        
        //attribution des moteurs à leur objet physiques à contrôler + détermination du comportement du moteur


        MotAVD0 = hardwareMap.get(DcMotor.class, "MotAVD0");
        MotARD1 = hardwareMap.get(DcMotor.class, "MotARD1");
        MotAVG2 = hardwareMap.get(DcMotor.class, "MotAVG2");
        MotARG3 = hardwareMap.get(DcMotor.class, "MotARG3");
        
        //déclaration des sens de rotation des moteurs liés à chaque roue
        MotAVD0.setDirection(DcMotor.Direction.REVERSE);
        MotARD1.setDirection(DcMotor.Direction.REVERSE);
        MotAVG2.setDirection(DcMotor.Direction.FORWARD);
        MotARG3.setDirection(DcMotor.Direction.FORWARD);

    }
}