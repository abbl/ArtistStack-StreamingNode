package ovh.abbl.streamnode.models;

import ovh.abbl.streamnode.files.encoder.SoundFileEncoder;

public interface SoundFile {
    SoundFileEncoder getEncoder();
}
