package ovh.abbl.streamnode.files.encoder;

import ovh.abbl.streamnode.models.impl.WaveFile;

public abstract class SoundFileEncoder {
    public byte[] encode(WaveFile waveFile) {
        return new byte[0];
    }
}
