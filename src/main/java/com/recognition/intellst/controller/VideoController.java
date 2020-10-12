package com.recognition.intellst.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/video")
@Api(value = "Video Controller REST Endpoint")
public interface VideoController {

    @ApiOperation(value = "Get video URL")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Video URL is accepted"),
            @ApiResponse(code = 406, message = "Video URL is not accepted")
    })
    @GetMapping
    void setVideoURL(String videoURL, boolean activeCamera);
}
