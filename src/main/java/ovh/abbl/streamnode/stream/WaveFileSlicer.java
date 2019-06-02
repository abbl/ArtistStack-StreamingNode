package ovh.abbl.streamnode.stream;

import ovh.abbl.streamnode.models.WaveFile;

import java.util.List;

public class WaveFileSlicer {
    public List<WaveFile> slice(WaveFile waveFile, int sliceDuration){
        System.out.println("Slice bytes size: " + getDataSizeForGivenDuration(waveFile, sliceDuration));

        return null;
    }

    private int getDataSizeForGivenDuration(WaveFile waveFile, int sliceDuration){
        return waveFile.getFmtHeader().getSampleRate() * waveFile.getFmtHeader().getNumberOfChannels() * (waveFile.getFmtHeader().getBitsPerSample() / 8) * sliceDuration;
    }
}
