package com.recognition.intellst.utils;

import org.opencv.core.Mat;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class OpenCVImageUtils {

    public static BufferedImage matToBufferedImage(Mat frame) {
        if (!frame.empty()) {
            int type = BufferedImage.TYPE_BYTE_GRAY;
            if (frame.channels() > 1) {
                type = BufferedImage.TYPE_3BYTE_BGR;
            }
            int bufferSize = frame.channels() * frame.cols() * frame.rows();
            byte[] b = new byte[bufferSize];
            frame.get(0, 0, b); // get all the pixels
            BufferedImage image = new BufferedImage(frame.cols(), frame.rows(), type);
            final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            System.arraycopy(b, 0, targetPixels, 0, b.length);
            return image;
        }

        return null;
    }
}