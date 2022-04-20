package com.example.Web.Project.services;

import com.example.Web.Project.model.VideoCall;
import org.springframework.stereotype.Service;


public interface VideoCallService {
    public void saveToken(VideoCall videoCall);
    public boolean isExistToken(String vCallID);
    public VideoCall getToken(String vCallID);
}
