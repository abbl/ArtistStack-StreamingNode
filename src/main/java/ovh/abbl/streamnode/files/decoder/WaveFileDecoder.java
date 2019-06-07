package ovh.abbl.streamnode.files.decoder;

import ovh.abbl.streamnode.files.definitions.ByteHeader;
import ovh.abbl.streamnode.files.definitions.WaveFileHeaders;
import ovh.abbl.streamnode.models.impl.WaveFile;
import ovh.abbl.streamnode.tools.BinaryOperations;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class WaveFileDecoder {
    public static WaveFile decode(File waveFile){
        byte[] waveFileInBytes = readWaveFile(waveFile);

        if(waveFileInBytes != null){
            WaveFile.RiffHeader riffHeader = readRiffHeader(getHeaderOffset("RIFF", waveFileInBytes), waveFileInBytes);
            WaveFile.FmtHeader fmtHeader = readFmtHeader(getHeaderOffset("fmt ", waveFileInBytes), waveFileInBytes);
            WaveFile.DataHeader dataHeader = readDataHeader(getHeaderOffset("data", waveFileInBytes), waveFileInBytes);

            return WaveFile.builder()
                    .riffHeader(riffHeader)
                    .fmtHeader(fmtHeader)
                    .dataHeader(dataHeader)
                    .build();
        }else{
            //PRINT SOME ERROR ABOUT NOT FINDING A FILE.
        }

        return null;
    }

    private static byte[] readWaveFile(File waveFile){
        try {
            return Files.readAllBytes(waveFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int getHeaderOffset(String header, byte[] waveFileInBytes){
        String bytesToString = new String(BinaryOperations.getPartOfTheArray(0, 512, waveFileInBytes), StandardCharsets.UTF_8);

        return bytesToString.indexOf(header);
    }

    private static WaveFile.RiffHeader readRiffHeader(int riffHeaderOffset, byte[] waveFileInBytes){
        return WaveFile.RiffHeader.builder()
                .chunkId(new String(byteBufferWrap(riffHeaderOffset, WaveFileHeaders.RIFF_CHUNK_ID, waveFileInBytes).array(), StandardCharsets.UTF_8))
                .chunkSize(byteBufferWrap(riffHeaderOffset, WaveFileHeaders.RIFF_CHUNK_SIZE, waveFileInBytes).getInt())
                .chunkFormat(new String(byteBufferWrap(riffHeaderOffset, WaveFileHeaders.RIFF_FORMAT, waveFileInBytes).array(), StandardCharsets.UTF_8))
                .build();
    }

    private static WaveFile.FmtHeader readFmtHeader(int fmtHeaderOffset, byte[] waveFileInBytes){
        return WaveFile.FmtHeader.builder()
                .subChunk1Id(new String(byteBufferWrap(fmtHeaderOffset, WaveFileHeaders.SUBCHUNK_1_ID, waveFileInBytes).array(), StandardCharsets.UTF_8))
                .subChunk1Size(byteBufferWrap(fmtHeaderOffset, WaveFileHeaders.SUBCHUNK_1_SIZE, waveFileInBytes).getInt())
                .audioFormat(byteBufferWrap(fmtHeaderOffset, WaveFileHeaders.SUBCHUNK_1_AUDIO_FORMAT, waveFileInBytes).getShort())
                .numberOfChannels(byteBufferWrap(fmtHeaderOffset, WaveFileHeaders.SUBCHUNK_1_CHANNELS_NUMBER, waveFileInBytes).getShort())
                .sampleRate(byteBufferWrap(fmtHeaderOffset, WaveFileHeaders.SUBCHUNK_1_SAMPLE_RATE, waveFileInBytes).getInt())
                .byteRate(byteBufferWrap(fmtHeaderOffset, WaveFileHeaders.SUBCHUNK_1_BYTE_RATE, waveFileInBytes).getInt())
                .blockAlign(byteBufferWrap(fmtHeaderOffset, WaveFileHeaders.SUBCHUNK_1_BLOCK_ALIGN, waveFileInBytes).getShort())
                .bitsPerSample(byteBufferWrap(fmtHeaderOffset, WaveFileHeaders.SUBCHUNK_1_BITS_PER_SAMPLE, waveFileInBytes).getShort())
                .build();
    }

    private static WaveFile.DataHeader readDataHeader(int dataHeaderOffset, byte[] waveFileInBytes){
        return WaveFile.DataHeader.builder()
                .subChunk2Id(new String(byteBufferWrap(dataHeaderOffset, WaveFileHeaders.SUBCHUNK_2_ID, waveFileInBytes).array(), StandardCharsets.UTF_8))
                .subChunk2Size(byteBufferWrap(dataHeaderOffset, WaveFileHeaders.SUBCHUNK_2_SIZE, waveFileInBytes).getInt())
                .subChunk2Data(byteBufferWrap(dataHeaderOffset, WaveFileHeaders.SUBCHUNK_2_DATA, waveFileInBytes).array())
                .build();
    }

    /*
     * Tools
     */

    private static ByteBuffer byteBufferWrap(int headerOffset, ByteHeader byteHeader, byte[] waveFileInBytes){
        ByteBuffer byteBuffer;

        if(byteHeader.getHeaderLength() != -1){ // -1 stands for length to the end of the file.
            byteBuffer = ByteBuffer.wrap(BinaryOperations.getPartOfTheArray(headerOffset + byteHeader.getOffset(), headerOffset + byteHeader.getOffset() + byteHeader.getHeaderLength(), waveFileInBytes));
        }else{
            byteBuffer = ByteBuffer.wrap(BinaryOperations.getPartOfTheArray(headerOffset + byteHeader.getOffset(), waveFileInBytes.length - 1, waveFileInBytes));
        }
        byteBuffer.order(byteHeader.getByteOrder());

        return byteBuffer;
    }

}
