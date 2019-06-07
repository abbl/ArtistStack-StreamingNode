package ovh.abbl.streamnode.models.impl;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import ovh.abbl.streamnode.files.encoder.impl.WaveFileEncoder;
import ovh.abbl.streamnode.models.SoundFile;

@Builder(toBuilder = true)
@Data
@ToString
public class WaveFile implements SoundFile {
    @Autowired
    private static WaveFileEncoder waveFileEncoder;
    private RiffHeader riffHeader;
    private FmtHeader fmtHeader;
    private DataHeader dataHeader;



    @Override
    public WaveFileEncoder getEncoder() {
        return waveFileEncoder;
    }

    @Builder(toBuilder = true)
    @Data
    @ToString
    public static class RiffHeader {
        private String chunkId;
        private int chunkSize;
        private String chunkFormat;
    }

    @Builder
    @Getter
    @ToString
    public static class FmtHeader{
        private String subChunk1Id;
        private int subChunk1Size;
        private short audioFormat;
        private short numberOfChannels;
        private int sampleRate;
        private int byteRate;
        private short blockAlign;
        private short bitsPerSample;
    }

    @Builder(toBuilder = true)
    @Data
    @ToString
    public static class DataHeader{
        private String subChunk2Id;
        private int subChunk2Size;
        @ToString.Exclude
        private byte[] subChunk2Data;
    }
}
