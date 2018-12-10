package ovh.abbl.streamnode.stream;

import org.springframework.stereotype.Component;
import ovh.abbl.streamnode.configs.StreamNodeConfig;
import ovh.abbl.streamnode.models.AudioSlice;
import java.util.ArrayList;

@Component
public class FileSlicer {

    public ArrayList<AudioSlice> slice(String encodedString){
        ArrayList<AudioSlice> slices = new ArrayList<>();
        int sliceNumber = encodedString.length() / StreamNodeConfig.SLICE_SIZE;

        System.out.println("SN:" + sliceNumber);

        for(int i = 0; i < sliceNumber; i++){
            int beginIndex = i * StreamNodeConfig.SLICE_SIZE;
            int endIndex;

            if(i != sliceNumber - 1){
                endIndex = beginIndex + StreamNodeConfig.SLICE_SIZE;
            }else{
                endIndex = beginIndex + (encodedString.length() - beginIndex);

                //System.out.println("Last start index:" + beginIndex + " End index:" + endIndex + "I:" + i);
            }

            slices.add(new AudioSlice(i, encodedString.substring(beginIndex, endIndex)));
        }
        return slices;
    }
}
