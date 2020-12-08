package com.recognition.intellst.recognition.face;

import lombok.Setter;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import java.io.IOException;

import static com.recognition.intellst.recognition.face.RecognitionConstants.HAAR_RESOURCE;
import static com.recognition.intellst.recognition.face.RecognitionConstants.TRAINED_MODEL;
import static org.opencv.imgproc.Imgproc.equalizeHist;

public class FaceDisplay {

    @Setter
    public static Thread threadImage;
    private static int absoluteFaceSize;

    public static void detectAndDisplay(Mat frame) throws IOException {

        CollectData collectData = new CollectData();
        CascadeClassifier faceCascade = new CascadeClassifier(HAAR_RESOURCE.getFile().getAbsolutePath());

        FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();
        faceRecognizer.read(TRAINED_MODEL);

        MatOfRect faces = new MatOfRect();
        Mat grayFrame = new Mat();

        Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
        equalizeHist(grayFrame, grayFrame);

        if (absoluteFaceSize == 0) {
            int height = grayFrame.rows();
            if (Math.round(height * 0.2f) > 0) {
                absoluteFaceSize = Math.round(height * 0.2f);
            }
        }
        faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2,
                Objdetect.CASCADE_SCALE_IMAGE, new Size(absoluteFaceSize, absoluteFaceSize), new Size());

        Rect[] facesArray = faces.toArray();
        for (Rect rect : facesArray) {
            int[] label = new int[1];
            double[] confidence = new double[1];
            faceRecognizer.predict(grayFrame.submat(rect), label, confidence);
            StringBuilder name = new StringBuilder(faceRecognizer.getLabelInfo(label[0]));

            if (confidence[0] < 50) {
                CollectData.uuid = String.valueOf(name);
            } else {

                Runnable collect = new CollectData();
                threadImage = new Thread(collect);
                threadImage.start();
                setThreadImage(threadImage);

                collectData.imageData();
            }
        }
    }
}
