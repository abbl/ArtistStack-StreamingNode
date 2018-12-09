package ovh.abbl.streamnode.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import ovh.abbl.streamnode.services.FileStreamService;

import java.security.Principal;

@RestController
public class StreamController {
    @Autowired
    public FileStreamService fileStreamService;

    @MessageMapping("/request/stream")
    public void requestAudioStream(Principal principal){
        fileStreamService.stream(principal);
    }
}
