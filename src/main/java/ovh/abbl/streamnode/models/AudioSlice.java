package ovh.abbl.streamnode.models;

import lombok.Data;

@Data
public class AudioSlice {
    private int id;
    private int totalSizeInBytes;
    private int totalNumberOfSlices;
    private String encodedBytes; //BASE64

    public AudioSlice(int id, String encodedBytes){
        this.id = id;
        this.encodedBytes = encodedBytes;
    }
}
