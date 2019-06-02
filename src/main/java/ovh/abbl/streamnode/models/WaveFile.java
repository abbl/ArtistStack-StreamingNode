package ovh.abbl.streamnode.models;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
public class WaveFile {
    private RiffHeader riffHeader;
    private FmtHeader fmtHeader;
    private DataHeader dataHeader;

    @Builder
    @Getter
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

    @Builder
    @Getter
    @ToString
    public static class DataHeader{
        private String subChunk2Id;
        private int subChunk2Size;
        @ToString.Exclude
        private byte[] subChunk2Data;
    }
}
