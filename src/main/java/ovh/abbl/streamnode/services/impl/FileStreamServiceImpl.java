package ovh.abbl.streamnode.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ovh.abbl.streamnode.models.AudioSlice;
import ovh.abbl.streamnode.repositories.AudioRepository;
import ovh.abbl.streamnode.services.FileStreamService;

import java.security.Principal;
import java.util.Arrays;
import java.util.Base64;

@Service
public class FileStreamServiceImpl implements FileStreamService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private AudioRepository audioRepository;

    @Override
    @Async
    public void stream(Principal principal) {
        byte[] bytes = audioRepository.load("test.mp3");

        AudioSlice audioSlice = new AudioSlice();
        audioSlice.setId(1);
        audioSlice.setEncodedBytes(Base64.getEncoder().encodeToString(bytes));

        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/stream", audioSlice);
    }
}