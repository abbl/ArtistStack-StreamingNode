package ovh.abbl.streamnode.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ovh.abbl.streamnode.files.decoder.WaveFileDecoder;
import ovh.abbl.streamnode.files.encoder.SoundFileEncoder;
import ovh.abbl.streamnode.models.AudioSlice;
import ovh.abbl.streamnode.models.impl.WaveFile;
import ovh.abbl.streamnode.repositories.AudioRepository;
import ovh.abbl.streamnode.services.FileStreamService;
import ovh.abbl.streamnode.stream.WaveFileSlicer;
import ovh.abbl.streamnode.tools.BinaryOperations;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class FileStreamServiceImpl implements FileStreamService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private AudioRepository audioRepository;
    @Autowired
    private SoundFileEncoder soundFileEncoder;

    @Override
    @Async
    public void stream(Principal principal) {
        byte[] bytes = audioRepository.load("test.mp3");
        //ArrayList<AudioSlice> slices = fileSlicer.slice(Base64.getEncoder().encodeToString(bytes));

        WaveFile waveFile = WaveFileDecoder.decode(audioRepository.getFile("test.wav"));
        System.out.println(waveFile.toString());
        List<WaveFile> waveFiles = WaveFileSlicer.slice(waveFile, 40);

        BinaryOperations.writeToFile(soundFileEncoder.encode(waveFiles.get(1)));

    }
}

/*
        for(AudioSlice slice : slices){
            simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/stream", slice);
        }
 */