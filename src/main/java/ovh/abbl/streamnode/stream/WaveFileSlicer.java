package ovh.abbl.streamnode.stream;

import ovh.abbl.streamnode.files.definitions.WaveFileHeaders;
import ovh.abbl.streamnode.files.encoder.impl.WaveFileEncoder;
import ovh.abbl.streamnode.models.AudioSlice;
import ovh.abbl.streamnode.models.impl.WaveFile;
import ovh.abbl.streamnode.tools.BinaryOperations;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class WaveFileSlicer {

    public static List<WaveFile> slice(WaveFile waveFile, int sliceDuration){
        int dataSize = getDataSizeForGivenDuration(waveFile, sliceDuration);
        int sliceNumber = waveFile.getDataHeader().getSubChunk2Size() / dataSize;
        ArrayList<WaveFile> slices = new ArrayList<>();

        for(int i = 0; i < sliceNumber; i++){
            int dataStartIndex = i * dataSize;
            int dataEndIndex = (i + 1) * dataSize - 1;

            System.out.println("i:" + i + " Start: " + dataStartIndex + " End: " + dataEndIndex);

            slices.add(modifyDataHeader(waveFile,
                    BinaryOperations.getPartOfTheArray(dataStartIndex, dataEndIndex, waveFile.getDataHeader().getSubChunk2Data())));
        }

        System.out.println("Slice bytes size: " + getDataSizeForGivenDuration(waveFile, sliceDuration) + " Slice number:" + sliceNumber);

        return slices;
    }

    private static WaveFile modifyDataHeader(WaveFile waveFile, byte[] slicedData){
        WaveFile modifiedWaveFile = waveFile.toBuilder().build();
        modifiedWaveFile.setRiffHeader(waveFile.getRiffHeader().toBuilder().build());
        modifiedWaveFile.setDataHeader(waveFile.getDataHeader().toBuilder().build());

        modifiedWaveFile.getRiffHeader().setChunkSize(slicedData.length + WaveFileHeaders.getAllHeadersLength());
        modifiedWaveFile.getDataHeader().setSubChunk2Size(slicedData.length);
        modifiedWaveFile.getDataHeader().setSubChunk2Data(slicedData);

        return modifiedWaveFile;
    }

    private static int getDataSizeForGivenDuration(WaveFile waveFile, int sliceDuration){
        return waveFile.getFmtHeader().getSampleRate() * waveFile.getFmtHeader().getNumberOfChannels() * (waveFile.getFmtHeader().getBitsPerSample() / 8) * sliceDuration;
    }

    public static List<AudioSlice> toAudioSlices(List<WaveFile> waveSlices, WaveFileEncoder waveFileEncoder){
        ArrayList<AudioSlice> sliced = new ArrayList<>();

        System.out.println(waveSlices.toString());

        for(int id = 0; id < waveSlices.size(); id++){
            WaveFile waveFile = waveSlices.get(id);

            sliced.add(new AudioSlice(id, Base64.getEncoder().encodeToString(waveFileEncoder.encode(waveFile))));
        }

        return sliced;
    }
}
