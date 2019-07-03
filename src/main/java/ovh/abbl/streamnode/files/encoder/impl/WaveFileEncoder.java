package ovh.abbl.streamnode.files.encoder.impl;

import org.springframework.stereotype.Component;
import ovh.abbl.streamnode.files.definitions.ByteHeader;
import ovh.abbl.streamnode.files.definitions.WaveFileHeaders;
import ovh.abbl.streamnode.files.encoder.SoundFileEncoder;
import ovh.abbl.streamnode.models.impl.WaveFile;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Component
public class WaveFileEncoder extends SoundFileEncoder {

    @Override
    public byte[] encode(WaveFile waveFile) {
        System.out.println("Count:" + waveFile.getRiffHeader().getChunkSize());
        ByteBuffer byteBuffer = ByteBuffer.allocate(waveFile.getRiffHeader().getChunkSize() + 8);
        byteBuffer.put(encodeRiffHeader(waveFile.getRiffHeader()));
        byteBuffer.put(encodeFmtHeader(waveFile.getFmtHeader()));
        byteBuffer.put(encodeDataHeader(waveFile.getDataHeader()));

        byteBuffer.flip();

        return byteBuffer.array();
    }

    private byte[] encodeRiffHeader(WaveFile.RiffHeader riffHeader){
        ByteBuffer byteBuffer = ByteBuffer.allocate(WaveFileHeaders.getRiffHeaderLength());

        putDataInsideBuffer(byteBuffer, WaveFileHeaders.RIFF_CHUNK_ID, riffHeader.getChunkId());
        putDataInsideBuffer(byteBuffer, WaveFileHeaders.RIFF_CHUNK_SIZE, riffHeader.getChunkSize());
        putDataInsideBuffer(byteBuffer, WaveFileHeaders.RIFF_FORMAT, riffHeader.getChunkFormat());

        return byteBuffer.array();
    }

    private byte[] encodeFmtHeader(WaveFile.FmtHeader fmtHeader){
        ByteBuffer byteBuffer = ByteBuffer.allocate(WaveFileHeaders.getSubChunkHeaderLength());

        putDataInsideBuffer(byteBuffer, WaveFileHeaders.SUBCHUNK_1_ID, fmtHeader.getSubChunk1Id());
        putDataInsideBuffer(byteBuffer, WaveFileHeaders.SUBCHUNK_1_SIZE, fmtHeader.getSubChunk1Size());
        putDataInsideBuffer(byteBuffer, WaveFileHeaders.SUBCHUNK_1_AUDIO_FORMAT, fmtHeader.getAudioFormat());
        putDataInsideBuffer(byteBuffer, WaveFileHeaders.SUBCHUNK_1_CHANNELS_NUMBER, fmtHeader.getNumberOfChannels());
        putDataInsideBuffer(byteBuffer, WaveFileHeaders.SUBCHUNK_1_SAMPLE_RATE, fmtHeader.getSampleRate());
        putDataInsideBuffer(byteBuffer, WaveFileHeaders.SUBCHUNK_1_BYTE_RATE, fmtHeader.getByteRate());
        putDataInsideBuffer(byteBuffer, WaveFileHeaders.SUBCHUNK_1_BLOCK_ALIGN, fmtHeader.getBlockAlign());
        putDataInsideBuffer(byteBuffer, WaveFileHeaders.SUBCHUNK_1_BITS_PER_SAMPLE, fmtHeader.getBitsPerSample());

        return byteBuffer.array();
    }

    private byte[] encodeDataHeader(WaveFile.DataHeader dataHeader){
        ByteBuffer byteBuffer = ByteBuffer.allocate(WaveFileHeaders.getUncompletedDataHeaderLength() + dataHeader.getSubChunk2Size());

        putDataInsideBuffer(byteBuffer, WaveFileHeaders.SUBCHUNK_2_ID, dataHeader.getSubChunk2Id());
        putDataInsideBuffer(byteBuffer, WaveFileHeaders.SUBCHUNK_2_SIZE, dataHeader.getSubChunk2Size());
        byteBuffer.put(dataHeader.getSubChunk2Data());

        return byteBuffer.array();
    }

    private void putDataInsideBuffer(ByteBuffer byteBuffer, ByteHeader byteHeader, Object object){
        byteBuffer.put(convertToBytes(object, byteHeader), 0, byteHeader.getHeaderLength());
    }

    private byte[] convertToBytes(Object object, ByteHeader byteHeader){
        ByteBuffer byteBuffer = ByteBuffer.allocate(byteHeader.getHeaderLength());

        byteBuffer.order(byteHeader.getByteOrder());

        if(object instanceof Integer){
            byteBuffer.putInt((int)object);
        }else if(object instanceof Short){
            byteBuffer.putShort((short) object);
        }else if(object instanceof String){
            return ((String) object).getBytes(StandardCharsets.UTF_8);
        }
        byteBuffer.flip();

        return byteBuffer.array();
    }

}
