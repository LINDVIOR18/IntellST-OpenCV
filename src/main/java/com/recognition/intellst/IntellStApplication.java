package com.recognition.intellst;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.recognition.intellst.utils.OpenCVLibraryUtils.prepareLib;

@SpringBootApplication
public class IntellStApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntellStApplication.class, args);

        prepareLib();
    }
}

