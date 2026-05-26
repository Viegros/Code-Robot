package org.firstinspires.ftc.teamcode.RobotController;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp 

public class TeleOp extends LinearOpMode {

    private DcMotor MotARD0, MotAVD1, MotARG2, MotAVG3;

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
            
                MotARD0.setPower(ARD);
                MotAVD1.setPower(AVD);
                MotARG2.setPower(ARG);
                MotAVG3.setPower(AVG);
        }
    }


    public void initMoteurs() {
        
        //attribution des moteurs à leur objet physiques à contrôler + détermination du comportement du moteur


        MotARD0 = hardwareMap.get(DcMotor.class, "MotARD0");
        MotAVD1 = hardwareMap.get(DcMotor.class, "MotAVD1");
        MotARG2 = hardwareMap.get(DcMotor.class, "MotARG2");
        MotAVG3 = hardwareMap.get(DcMotor.class, "MotAVG3");
        
        //déclaration des sens de rotation des moteurs liés à chaque roue
        MotARD0.setDirection(DcMotor.Direction.REVERSE);
        MotAVD1.setDirection(DcMotor.Direction.REVERSE);
        MotARG2.setDirection(DcMotor.Direction.FORWARD);
        MotAVG3.setDirection(DcMotor.Direction.FORWARD);

    }

}

public class TeleOp extends LinearOpMode {

    private DcMotor MotCanonGauche, MotCanonDroite;                                         // A rajouter, moteurs pour envoyer la balle

    while (opModeIsActive()) {

        if (this.gamepad1.b) {                                       // Touche Rond

            MotCanonDroite.setpower()
            MotCanonGauche.setpower()                                 // Mettre la puissance voulue entre -1 et 1 avec la puissance max  Position 1
        } 

        if (this.gamepad1.dpad_left) {                                 // Touche Fleche gauche

            MotCanonDroite.setpower()
            MotCanonDroite.setpower()                                                            // Mettre la puiissance du moteur pour la position 2
        }

// Par la suite, on voudra faire en sorte que la boule parte dans le canon.
    private DcMotor MotChoixCouleur;

    while(opModeIsActive())  {

        if (this.gamepad1.left_bumper) {                               // L1
            
            MotChoixCouleur.setpower()                                 // Pour les deux setpower suivants on mettra 1 ou -1 selon la direction du moteur de base pour envoyer la balle à gauche ou à droite

        if (this.gamepad1.right_bumper) {                              // R1

            MotChoixCouleur.setpower()

    private DcMotor MotBalleGauche, MotBalleDroit;

    while (opModeIsActive())  {

        if (this.gamepad1.left_trigger) {                              // L2

            MotBalleGauche.setpower()

        }                                                              // ENvoyer dans le canon

        if (this.gamepad1.right_trigger) {                             // R2

            MotBAlleDroite.setpower()

        }
    }
