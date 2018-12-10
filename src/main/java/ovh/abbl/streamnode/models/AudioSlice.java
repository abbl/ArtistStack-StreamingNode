package ovh.abbl.streamnode.models;

import lombok.Data;

@Data
public class AudioSlice {
    private int id;
    private int totalSizeInBytes;
    private int totalNumberOfSlices;

    private String encodedBytes;


    public AudioSlice(){}
    public AudioSlice(int id, String encodedBytes){
        this.id = id;
        this.encodedBytes = encodedBytes;
    }
}
