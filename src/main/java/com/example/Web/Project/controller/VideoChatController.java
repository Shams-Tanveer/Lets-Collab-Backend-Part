package com.example.Web.Project.controller;

import com.example.Web.Project.model.VideoCall;
import com.example.Web.Project.services.VideoCallService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@SessionScope
@RequestMapping("/videoChat")
@CrossOrigin(origins = {"http://localhost:3000"}, allowedHeaders = "*", allowCredentials = "true")
@RequiredArgsConstructor
public class VideoChatController {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    @Autowired
    private VideoCallService videoCallService;

    @GetMapping("/getToken")
    public TokenInfo getToken(HttpServletRequest request) throws ParseException {
        //VideoCall videoCall = videoCallService.getToken((String) request.getSession(false).getAttribute("curProjectID"));
        TokenInfo tokenInfo = new TokenInfo();

        if (!videoCallService.isExistToken("project1840_00009")) {
            tokenInfo.setResultCode(0);
            tokenInfo.setTokenID("Couldn't Found");
            tokenInfo.setProjectName("Coundn't Found");
        } else {
            VideoCall videoCall = videoCallService.getToken("project1840_00009");
            Date d1 = videoCall.getCreatedAt();
            Date d2 = new Date();
            long difference = d2.getTime()-d1.getTime();
            long dif_min = TimeUnit.MILLISECONDS.toMinutes(difference) % 60;

            if(dif_min<60)
            {
                tokenInfo.setResultCode(1);
                tokenInfo.setTokenID(videoCall.getTokenId());
                tokenInfo.setProjectName(videoCall.getName());
            }
            else{
                tokenInfo.setResultCode(0);
                tokenInfo.setTokenID("TimeOut");
                tokenInfo.setProjectName("Timeout");
            }
            System.out.println(dif_min);

        }
        return tokenInfo;
    }

    @PostMapping("/saveToken")
    public void saveToken(@RequestBody VideoCall videoCall, HttpServletRequest request) throws ParseException {
        Date date = new Date();
        videoCall.setCreatedAt(date);
        videoCall.setId("project1840_00009");
        videoCallService.saveToken(videoCall);
    }

    @Data
    class TokenInfo {
        private int resultCode;
        private String tokenID;
        private String projectName;
    }
}
