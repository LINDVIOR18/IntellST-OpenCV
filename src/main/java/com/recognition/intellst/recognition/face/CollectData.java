package com.recognition.intellst.recognition.face;

import lombok.SneakyThrows;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.recognition.intellst.recognition.face.FaceDisplay.threadImage;
import static com.recognition.intellst.recognition.face.RecognitionConstants.CAPTURE_IMAGE_TIME;
import static com.recognition.intellst.recognition.face.RecognitionConstants.HAAR_RESOURCE;

public class CollectData implements Runnable {

    public static String uuid;
    public static String path;
    private static int sample = 0;
    private static int labelSet;

    public static void saveImage(Mat image) throws IOException {
        CascadeClassifier faceCascade = new CascadeClassifier(HAAR_RESOURCE.getFile().getAbsolutePath());
        MatOfRect faces = new MatOfRect();
        Mat grayFrame = new Mat();

        Imgproc.cvtColor(image, grayFrame, Imgproc.COLOR_BGR2GRAY);

        faceCascade.detectMultiScale(grayFrame, faces);


        Rect[] facesArray = faces.toArray();
        if (facesArray.length >= 1) {
            sample++;
            System.out.println("Image:" + sample);
            Imgcodecs.imwrite(path + "/" + labelSet + "-" + uuid + "_" + (sample) + ".png",
                    image.submat(facesArray[0]));
        }
    }

    public void imageData() throws IOException {
        uuid = UUID.randomUUID().toString().replace("-", "");
        File file = new File("src/main/resources/training/" + uuid);
        file.mkdir();
        path = file.getCanonicalPath();
        labelSet++;
    }

    @SneakyThrows
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(CAPTURE_IMAGE_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.currentThread().interrupt();
            if (threadImage.isAlive()) {
                FaceTrainModel faceTrainModel = new FaceTrainModel();
                faceTrainModel.faceTrain();
                sample = 0;
                threadImage = null;
            }
        }
    }
}

