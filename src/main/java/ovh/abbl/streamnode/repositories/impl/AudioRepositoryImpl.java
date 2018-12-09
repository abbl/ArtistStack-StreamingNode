package ovh.abbl.streamnode.repositories.impl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;
import ovh.abbl.streamnode.repositories.AudioRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Repository
public class AudioRepositoryImpl implements AudioRepository {
    private static final String REPOSITORY_PATH = "./repository/audio/";

    @Override
    public byte[] load(String fileName) {
        File audioFile = new File(REPOSITORY_PATH + fileName);
        byte[] bytes;

        try {
            FileInputStream inputStream = new FileInputStream(audioFile);
            bytes = IOUtils.toByteArray(inputStream);
            inputStream.close();

            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
