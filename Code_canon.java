package org.firstinspires.ftc.teamcode.RobotController;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

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
