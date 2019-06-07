package ovh.abbl.streamnode.files.definitions;

import java.nio.ByteOrder;

/**
 * All supported headers by now.
 */
public abstract class WaveFileHeaders {
    public static final ByteHeader RIFF_CHUNK_ID = new ByteHeader(4, 0, ByteOrder.BIG_ENDIAN);
    public static final ByteHeader RIFF_CHUNK_SIZE = new ByteHeader(4, 4,ByteOrder.LITTLE_ENDIAN);
    public static final ByteHeader RIFF_FORMAT = new ByteHeader(4, 8, ByteOrder.BIG_ENDIAN);

    public static final ByteHeader SUBCHUNK_1_ID = new ByteHeader(4, 0, ByteOrder.BIG_ENDIAN);
    public static final ByteHeader SUBCHUNK_1_SIZE = new ByteHeader(4, 4, ByteOrder.LITTLE_ENDIAN);
    public static final ByteHeader SUBCHUNK_1_AUDIO_FORMAT = new ByteHeader(2, 8, ByteOrder.LITTLE_ENDIAN);
    public static final ByteHeader SUBCHUNK_1_CHANNELS_NUMBER = new ByteHeader(2, 10, ByteOrder.LITTLE_ENDIAN);
    public static final ByteHeader SUBCHUNK_1_SAMPLE_RATE = new ByteHeader(4, 12, ByteOrder.LITTLE_ENDIAN);
    public static final ByteHeader SUBCHUNK_1_BYTE_RATE = new ByteHeader(4, 16, ByteOrder.LITTLE_ENDIAN);
    public static final ByteHeader SUBCHUNK_1_BLOCK_ALIGN = new ByteHeader(2, 20, ByteOrder.LITTLE_ENDIAN);
    public static final ByteHeader SUBCHUNK_1_BITS_PER_SAMPLE = new ByteHeader(2, 22, ByteOrder.LITTLE_ENDIAN);

    public static final ByteHeader SUBCHUNK_2_ID = new ByteHeader(4, 0, ByteOrder.BIG_ENDIAN);
    public static final ByteHeader SUBCHUNK_2_SIZE = new ByteHeader(4, 4, ByteOrder.LITTLE_ENDIAN);
    public static final ByteHeader SUBCHUNK_2_DATA = new ByteHeader(-1, 8, ByteOrder.LITTLE_ENDIAN);

    public static int getRiffHeaderLength(){
        return summarizeHeaderLength(RIFF_CHUNK_ID, RIFF_CHUNK_SIZE, RIFF_FORMAT);
    }

    public static int getSubChunkHeaderLength(){
        return summarizeHeaderLength(SUBCHUNK_1_ID, SUBCHUNK_1_SIZE, SUBCHUNK_1_AUDIO_FORMAT, SUBCHUNK_1_CHANNELS_NUMBER,
                 SUBCHUNK_1_SAMPLE_RATE, SUBCHUNK_1_BYTE_RATE, SUBCHUNK_1_BLOCK_ALIGN, SUBCHUNK_1_BITS_PER_SAMPLE);
    }

    public static int getUncompletedDataHeaderLength(){
        return summarizeHeaderLength(SUBCHUNK_2_ID, SUBCHUNK_2_SIZE);
    }

    public static int getAllHeadersLength(){
        return getRiffHeaderLength() + getSubChunkHeaderLength() + getUncompletedDataHeaderLength();
    }

    private static int summarizeHeaderLength(ByteHeader... byteHeaders){
        int length = 0;

        for(ByteHeader byteHeader : byteHeaders){
            length += byteHeader.getHeaderLength();
        }

        return length;
    }
}
