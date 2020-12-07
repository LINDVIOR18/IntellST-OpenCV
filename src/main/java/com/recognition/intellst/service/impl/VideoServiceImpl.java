package com.recognition.intellst.service.impl;

import com.recognition.intellst.api.DataToSent;
import com.recognition.intellst.service.VideoService;
import com.recognition.intellst.utils.OpenCVImageUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.springframework.stereotype.Service;
import org.springframework.web.server.NotAcceptableStatusException;

import java.io.IOException;
import java.util.Objects;

import static com.recognition.intellst.recognition.face.CollectData.saveImage;
import static com.recognition.intellst.recognition.face.CollectData.uuid;
import static com.recognition.intellst.recognition.face.FaceDisplay.detectAndDisplay;
import static com.recognition.intellst.recognition.face.FaceDisplay.threadImage;
import static com.recognition.intellst.recognition.face.RecognitionConstants.VIDEO_HEIGHT;
import static com.recognition.intellst.recognition.face.RecognitionConstants.VIDEO_WIDTH;
import static com.recognition.intellst.recognition.temperature.ImageTemperatureReader.readTemperature;
import static com.recognition.intellst.recognition.temperature.ImageTemperatureReader.temperature;

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
                    readTemperature((Objects.requireNonNull(OpenCVImageUtils.matToBufferedImage(frame))));
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
        DataToSent dataToSent = new DataToSent();
        int label = 1;

        if (!uuid.equals(uuid)) {
            dataToSent.setTemperature(temperature);
            dataToSent.setUuid(uuid);
            dataToSent.setPhotoFilename("src/main/resources/training/" + uuid + "/" + ++label + "-" + uuid + "_" + "1" + ".png");
        }
    }

    @SneakyThrows
    @Override
    public void startCamera(String videoURL, boolean activeCamera) {
        log.info("method = startCamera");

        if (activeCamera && videoURL != null) {
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
        } else if (!activeCamera) {
            try {
                videoCapture.release();
                log.info("Camera is stopped");
            } catch (Exception e) {
                log.info("Camera is stopped error occurred, camera is already stopped");
                e.getCause();
            }
        } else {
            throw new NotAcceptableStatusException("Video URL is Empty or incorrect");
        }
    }
}
