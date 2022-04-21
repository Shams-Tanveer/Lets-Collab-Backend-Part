package com.example.Web.Project.controller;

import com.example.Web.Project.model.Project;
import com.example.Web.Project.model.VideoCall;
import com.example.Web.Project.services.ProjectService;
import com.example.Web.Project.services.VideoCallService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @Autowired
    private ProjectService projectService;

    @GetMapping("/getToken")
    public TokenInfo getToken(HttpServletRequest request) throws ParseException {
        //VideoCall videoCall = videoCallService.getToken((String) request.getSession(false).getAttribute("curProjectID"));
        TokenInfo tokenInfo = new TokenInfo();
        HttpSession session = request.getSession(false);
        String projectID = (String) session.getAttribute("curProjectID");
        Project project = projectService.getOneProject(projectID);

        if (!videoCallService.isExistToken(projectID)) {
            tokenInfo.setResultCode(0);
            tokenInfo.setTokenID("Couldn't Found");
            tokenInfo.setProjectName(project.getProjectName());
        } else {
            VideoCall videoCall = videoCallService.getToken("project1840_00009");
            Date d1 = videoCall.getCreatedAt();
            Date d2 = new Date();
            long difference = d2.getTime()-d1.getTime();
            long dif_min = (long) Math.floor(((difference) /1000)/60);
            if(dif_min<60)
            {
                tokenInfo.setResultCode(1);
                tokenInfo.setTokenID(videoCall.getTokenId());
                tokenInfo.setProjectName(videoCall.getName());
            }
            else{
                tokenInfo.setResultCode(0);
                tokenInfo.setTokenID("TimeOut");
                tokenInfo.setProjectName(project.getProjectName());
            }
            System.out.println(dif_min);

        }
        return tokenInfo;
    }

    @PostMapping("/saveToken")
    public void saveToken(@RequestBody VideoCall videoCall, HttpServletRequest request) throws ParseException {
        Date date = new Date();
        videoCall.setCreatedAt(date);
        videoCall.setId((String) request.getSession(false).getAttribute("curProjectID"));
        videoCallService.saveToken(videoCall);
    }

    @Data
    class TokenInfo {
        private int resultCode;
        private String tokenID;
        private String projectName;
    }
}
