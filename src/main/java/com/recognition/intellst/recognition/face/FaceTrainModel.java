package com.recognition.intellst.recognition.face;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FaceTrainModel {

    public void faceTrain() throws IOException {
        File folder = new File("src/main/resources/training/");
        FaceRecognizer faceRecognizer = LBPHFaceRecognizer.create();

        File[] files = folder.listFiles();

        for (File file : Objects.requireNonNull(files)) {
            File root = new File(file.getCanonicalPath());

            File[] imageFiles = Objects.requireNonNull(root).listFiles();
            List<Mat> images = new ArrayList<>();

            Mat labels = new Mat(Objects.requireNonNull(imageFiles).length, 1, CvType.CV_32SC1);

            int counter = 0;
            for (File image : imageFiles) {
                Mat img = Imgcodecs.imread(image.getCanonicalPath());

                Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
                Imgproc.equalizeHist(img, img);

                int label = Integer.parseInt(image.getName().split("-")[0]);

                String labnname = image.getName().split("_")[0];
                String name = labnname.split("-")[1];
                faceRecognizer.setLabelInfo(label, name);

                images.add(img);

                labels.put(counter, 0, label);
                counter++;
            }
            faceRecognizer.update(images, labels);
            faceRecognizer.save("src/main/resources/trainedmodel" + "/train.yml");
        }
    }
}


