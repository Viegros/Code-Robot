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
