package ovh.abbl.streamnode.stream;

import ovh.abbl.streamnode.models.WaveFile;

import java.util.List;

public class WaveFileSlicer {
    public List<WaveFile> slice(WaveFile waveFile, int sliceDuration){
        System.out.println("Slice bytes size: " + getDataSizeForGivenDuration(waveFile, sliceDuration));

        return null;
    }

    private int getDataSizeForGivenDuration(WaveFile waveFile, int sliceDuration){
        return 0;//waveFile.getSampleRate() * waveFile.getNumberOfChannels() * (waveFile.getBitsPerSample() / 8) * sliceDuration;
    }
}
