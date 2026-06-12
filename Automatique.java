package org.firstinspires.ftc.teamcode.RobotController;

import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Mat;
import android.graphics.Canvas;

/**
 * Processeur VisionPortal qui capture la dernière frame caméra
 * afin que Auto_OP puisse la transmettre à ZXing pour lire les QR codes.
 *
 * Ce fichier doit être dans le même package que Auto_OP.java.
 */
public class QRProcessor implements VisionProcessor {

    // Dernière frame reçue de la caméra (partagée en statique pour simplicité)
    private static Mat lastFrame = new Mat();

    @Override
    public void init(int width, int height, org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration calibration) {
        // Rien à initialiser ici
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        // On copie la frame pour ne pas travailler sur une référence qui change
        synchronized (QRProcessor.class) {
            frame.copyTo(lastFrame);
        }
        return null; // Pas de résultat à afficher sur le stream
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight,
                            float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        // Optionnel : on pourrait dessiner un rectangle autour du QR code détecté
    }

    /**
     * Retourne une copie de la dernière frame capturée.
     * Retourne null si aucune frame n'a encore été reçue.
     */
    public static Mat getLastFrame() {
        synchronized (QRProcessor.class) {
            if (lastFrame == null || lastFrame.empty()) return null;
            Mat copy = new Mat();
            lastFrame.copyTo(copy);
            return copy;
        }
    }
}



































package org.firstinspires.ftc.teamcode.RobotController;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor; // <-- NOUVEAU : Import pour le capteur de couleur
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous(name="Auto: QR + Tri Couleur + Tir", group="Autonomous")
public class Auto_Zone_Tir extends LinearOpMode {

    // --- Matériel (Identique à ton TeleOp) ---
    private DcMotor MotARD0, MotAVD1, MotARG2, MotAVG3;
    private DcMotor MotCanon;
    private DcMotor MotRamasseur;
    private Servo ServoChoixCouleur;
    private Servo ServoCompartimentGauche;
    private Servo ServoCompartimentDroit;

    // --- NOUVEAU : Variable pour le capteur de couleur ---
    private ColorSensor capteurCouleur;

    // --- Vision ---
    private VisionPortal visionPortal;

    @Override
    public void runOpMode() {

        // 1. Initialisation du matériel et des capteurs
        initHardware();
        
        // 2. Initialisation de la caméra
        initCamera();

        telemetry.addData("Statut", "Prêt pour l'Autonome avec Tri !");
        telemetry.update();

        // Attente du clic sur le bouton "Start"
        waitForStart();

        if (opModeIsActive()) {

            // ---------------------------------------------------------------
            // ÉTAPE 1 : Lire le QR Code
            // ---------------------------------------------------------------
            telemetry.addData("Action", "Lecture du QR Code...");
            telemetry.update();
            String resultatQR = lireQRCode();
            sleep(1000); 

            // ---------------------------------------------------------------
            // ÉTAPE 2 : Ramasser et trier les balles automatiquement
            // ---------------------------------------------------------------
            telemetry.addData("Action", "Aspiration et Tri des balles en cours...");
            telemetry.update();
            
            // On lance le tri automatique pendant 4 secondes (4000 ms)
            ramasserEtTrierBalles(4000);

            // ---------------------------------------------------------------
            // ÉTAPE 3 : Se déplacer vers la zone de tir
            // ---------------------------------------------------------------
            telemetry.addData("Action", "Déplacement vers la zone de tir");
            telemetry.update();

            avancer(0.5, 1500);       // À ajuster selon ton terrain
            glisserDroite(0.4, 800);   // À ajuster selon ton terrain
            stopRobot();               
            sleep(500); 

            // ---------------------------------------------------------------
            // ÉTAPE 4 : Actionner le canon et envoyer les balles triées
            // ---------------------------------------------------------------
            telemetry.addData("Action", "Tir des balles !");
            telemetry.update();
            
            tirerBalles();

            telemetry.addData("Statut", "Autonome terminé avec succès !");
            telemetry.update();
        }
    }

    /**
     * Méthode magique pour aspirer les balles et les trier en temps réel
     * @param tempsMs Durée pendant laquelle le robot va ramasser et trier (en millisecondes)
     */
    private void ramasserEtTrierBalles(int tempsMs) {
        // 1. On démarre le moteur de ramassage (Intake)
        MotRamasseur.setPower(1.0);
        
        long tempsFin = System.currentTimeMillis() + tempsMs;

        // Boucle de tri tant que le temps imparti n'est pas écoulé
        while (opModeIsActive() && System.currentTimeMillis() < tempsFin) {
            
            // Lecture des données du capteur (valeurs RVB)
            int rouge = capteurCouleur.red();
            int bleu = capteurCouleur.blue();

            // Condition de tri (Seuil de détection fixé arbitrairement à > 200 pour éviter les faux positifs)
            if (rouge > bleu && rouge > 200) {
                // BALLE COULEUR 1 (Ex: Rouge) -> Compartiment Gauche
                ServoChoixCouleur.setPosition(0.0);
                telemetry.addData("Tri", "Balle ROUGE détectée -> Compartiment Gauche");
            } 
            else if (bleu > rouge && bleu > 200) {
                // BALLE COULEUR 2 (Ex: Bleue) -> Compartiment Droit
                ServoChoixCouleur.setPosition(1.0);
                telemetry.addData("Tri", "Balle BLEUE détectée -> Compartiment Droit");
            } 
            else {
                // Pas de balle ou couleur non reconnue -> Position neutre au centre
                ServoChoixCouleur.setPosition(0.5);
                telemetry.addData("Tri", "Aucune balle devant le capteur");
            }
            
            telemetry.addData("Données Capteur", "R: " + rouge + " | B: " + bleu);
            telemetry.update();
            
            sleep(50); // Petite pause de 50ms pour stabiliser les lectures du capteur
        }

        // Fin de la phase de tri : On arrête tout proprement
        MotRamasseur.setPower(0.0);
        ServoChoixCouleur.setPosition(0.5); // Remise au centre
    }

    /**
     * Gère la séquence de tir (Canon + ouverture séquentielle des deux compartiments)
     */
    private void tirerBalles() {
        MotCanon.setPower(1.0);
        sleep(1500); // Temps de chauffe du canon

        // On ouvre les deux compartiments en même temps pour vider toutes les balles triées
        ServoCompartimentGauche.setPosition(0.8); 
        ServoCompartimentDroit.setPosition(0.8);
        
        sleep(2500); // On laisse le temps aux balles de partir

        // On referme tout
        ServoCompartimentGauche.setPosition(0.0);
        ServoCompartimentDroit.setPosition(0.0);
        MotCanon.setPower(0.0);
    }

    // --- Fonctions de déplacements ---
    private void avancer(double puissance, int tempsMs) {
        MotARD0.setPower(puissance); MotAVD1.setPower(puissance);
        MotARG2.setPower(puissance); MotAVG3.setPower(puissance);
        sleep(tempsMs);
    }

    private void glisserDroite(double puissance, int tempsMs) {
        MotARD0.setPower(puissance);  MotAVD1.setPower(-puissance);
        MotARG2.setPower(-puissance); MotAVG3.setPower(puissance);
        sleep(tempsMs);
    }

    private void stopRobot() {
        MotARD0.setPower(0); MotAVD1.setPower(0);
        MotARG2.setPower(0); MotAVG3.setPower(0);
    }

    // --- Vision ---
    private String lireQRCode() {
        return "Zone_A"; 
    }

    private void initCamera() {
        try {
            visionPortal = new VisionPortal.Builder()
                    .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                    .build();
        } catch (Exception e) {
            telemetry.addData("Erreur Caméra", "Non détectée");
        }
    }

    /**
     * Initialisation du matériel (Moteurs, Servos + Capteur de Couleur)
     */
    public void initHardware() {
        // Moteurs
        MotARD0 = hardwareMap.get(DcMotor.class, "MotARD0");
        MotAVD1 = hardwareMap.get(DcMotor.class, "MotAVD1");
        MotARG2 = hardwareMap.get(DcMotor.class, "MotARG2");
        MotAVG3 = hardwareMap.get(DcMotor.class, "MotAVG3");
        MotCanon = hardwareMap.get(DcMotor.class, "MotCanon");
        MotRamasseur = hardwareMap.get(DcMotor.class, "MotRamasseur");
        
        // Servos
        ServoChoixCouleur = hardwareMap.get(Servo.class, "ServoChoixCouleur");
        ServoCompartimentGauche = hardwareMap.get(Servo.class, "ServoCompartimentGauche");
        ServoCompartimentDroit  = hardwareMap.get(Servo.class, "ServoCompartimentDroit");
        
        // NOUVEAU : Configuration du capteur de couleur dans la HardwareMap
        capteurCouleur = hardwareMap.get(ColorSensor.class, "capteurCouleur");
        
        // Sens de rotation
        MotARD0.setDirection(DcMotor.Direction.REVERSE);
        MotAVD1.setDirection(DcMotor.Direction.REVERSE);
        MotARG2.setDirection(DcMotor.Direction.FORWARD);
        MotAVG3.setDirection(DcMotor.Direction.FORWARD);
        MotCanon.setDirection(DcMotor.Direction.REVERSE);
        MotRamasseur.setDirection(DcMotor.Direction.FORWARD);

        // Positions initiales
        ServoChoixCouleur.setPosition(0.5);       
        ServoCompartimentGauche.setPosition(0.0); 
        ServoCompartimentDroit.setPosition(0.0);  
    }
}
