package com.recognition.intellst.service.impl;

import com.recognition.intellst.service.VideoService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;

import java.io.IOException;

import static com.recognition.intellst.recognition.face.CollectData.saveImage;
import static com.recognition.intellst.recognition.face.FaceDisplay.detectAndDisplay;
import static com.recognition.intellst.recognition.face.FaceDisplay.threadImage;
import static com.recognition.intellst.recognition.face.RecognitionConstants.VIDEO_HEIGHT;
import static com.recognition.intellst.recognition.face.RecognitionConstants.VIDEO_WIDTH;

@Slf4j
@Service
public class VideoServiceImpl implements VideoService {

    private static VideoCapture videoCapture;

    private static void grabFrame() {
        Mat frame = new Mat();
        if (videoCapture.isOpened()) {
            videoCapture.read(frame);
            try {
                if (!frame.empty()) {
//                    Mat test = imread("src/main/resources/training/spanish-numbers.jpg");
//                    String temperature = ImageTemperatureReader
//                            .readTemperature((Objects.requireNonNull(OpenCVImageUtils.matToBufferedImage(test))));
//                    System.out.println(temperature);
                    if (threadImage == null) {
                        detectAndDisplay(frame);
                    } else {
                        if (threadImage.isAlive()) {
                            saveImage(frame);
                        } else {
                            detectAndDisplay(frame);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.gc();
        }
    }


    @SneakyThrows
    @Override
    public void startCamera(String videoURL, boolean activeCamera) {
        log.info("method = startCamera");

        try {
            if (activeCamera) {
                videoCapture = new VideoCapture();
                videoCapture.open(videoURL, Videoio.CAP_ANY);
                videoCapture.set(Videoio.CAP_PROP_FRAME_WIDTH, VIDEO_WIDTH);
                videoCapture.set(Videoio.CAP_PROP_FRAME_HEIGHT, VIDEO_HEIGHT);
                videoCapture.set(Videoio.CAP_PROP_FPS, 30);

                log.info("VideoCapture is Active");

                Mat frame = new Mat();
                while (true) {
                    if (videoCapture.isOpened()) {
                        videoCapture.read(frame);
                        if (!frame.empty()) {
                            grabFrame();
                        }
                    }
                }
            } else {
                try {
                    videoCapture.release();
                    log.info("Camera is stopped");
                } catch (Exception e) {
                    log.info("Camera is stopped error occurred, camera is already stopped");
                    e.getCause();
                }
            }
        } catch (NotAcceptableStatusException e) {
            log.error("The URL is no't acceptable");
        }
    }
}