package com.recognition.intellst.recognition.face;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class RecognitionConstants {
    public static final int VIDEO_WIDTH = 1280;
    public static final int VIDEO_HEIGHT = 980;
    public static final int CAPTURE_IMAGE_TIME = 5000;
    public static final String TRAINED_MODEL = "src/main/resources/trainedmodel/train.yml";
    public static final Resource HAAR_RESOURCE = new ClassPathResource("haarcascades/haarcascade_frontalface_alt2.xml");
}
