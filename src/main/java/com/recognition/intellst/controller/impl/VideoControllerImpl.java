package com.recognition.intellst.controller.impl;


import com.recognition.intellst.controller.VideoController;
import com.recognition.intellst.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VideoControllerImpl implements VideoController {

    private final VideoService videoService;

    @Override
    public void setVideoURL(String videoURL, boolean activeCamera) {
        videoService.startCamera(videoURL, activeCamera);
    }
}
