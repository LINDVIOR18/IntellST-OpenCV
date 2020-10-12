package com.recognition.intellst.recognition.temperature;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.tesseract.TessBaseAPI;
import org.bytedeco.tesseract.Tesseract;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.bytedeco.leptonica.global.lept.pixDestroy;
import static org.bytedeco.leptonica.global.lept.pixReadMem;
import static org.bytedeco.tesseract.global.tesseract.PSM_SINGLE_BLOCK;

@Slf4j
public class ImageTemperatureReader {

    //    static {
//        System.load("D:\\Projects\\some\\IntellST-OpenCV\\src\\main\\resources\\lib\\" + Core.NATIVE_LIBRARY_NAME + ".dll");
//    }

    public static String readTemperature(BufferedImage imgBuff) throws IOException {

        BufferedImage result = new BufferedImage(
                imgBuff.getWidth(),
                imgBuff.getHeight(),
                BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D graphic = result.createGraphics();
        graphic.drawImage(imgBuff, 0, 0, Color.WHITE, null);
        graphic.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(result, "png", baos);
        byte[] imageBytes = baos.toByteArray();

        TessBaseAPI api = new TessBaseAPI();
        String tessData = "src/main/resources/tessdata/";
        api.Init(tessData, "eng");
        ByteBuffer imgBB = ByteBuffer.wrap(imageBytes);

        PIX image = pixReadMem(imgBB, imageBytes.length);
        api.SetPageSegMode(PSM_SINGLE_BLOCK);
        api.SetImage(image);
        api.SetSourceResolution(300);

        BytePointer outText = api.GetUTF8Text();

        api.End();
        api.close();
        outText.deallocate();
        pixDestroy(image);

        // OCR corrections

        String parsedOut = outText.getString()
                .replaceAll("[ABCDEFGHIJKLMNOPQRSTUVWXYZ]", "")
                .replaceAll("[abcdefghijklmnopqrstuvwxyz]", "")
                .replaceAll("[~`()<>';_|—:-“!/’‘:]", "")
                .replaceAll("\\s", "").replaceAll("$", "")
                .replaceAll("|", "").replaceAll("\\.", "")
                .replaceAll("^", "").replaceAll("n+", "")
                .replaceAll("\\b", "").replaceAll("n?", "")
                .replaceAll("n*", "").replaceAll("[n{x}]", "")
                .replaceAll("[n{x,y}]", "").replaceAll("[n{x,}]", "");

        if (parsedOut.length() > 7) {
            parsedOut = parsedOut.substring(0, 7) + ":" + parsedOut.substring(8);
        }
        int iSpace = parsedOut.lastIndexOf("");
        if (iSpace != -1) {
            parsedOut = parsedOut.substring(0, iSpace);
        }
        return parsedOut;
    }
}
