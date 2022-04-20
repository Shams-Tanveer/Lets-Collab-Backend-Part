package com.example.Web.Project.services;

import com.example.Web.Project.model.VideoCall;
import com.example.Web.Project.repository.VideoCallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoCallServiceImpl implements VideoCallService{

    @Autowired
    private VideoCallRepository videoCallRepository;

    @Override
    public void saveToken(VideoCall videoCall) {
        videoCallRepository.save(videoCall);
    }

    @Override
    public boolean isExistToken(String vCallID) {

        boolean check = videoCallRepository.existsByVCallID(vCallID);
        System.out.println(check);
        return check;
    }

    @Override
    public VideoCall getToken(String vCallID) {
        return videoCallRepository.getById(vCallID);
    }
}
