package ovh.abbl.streamnode.files.definitions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.ByteOrder;

@Data
@AllArgsConstructor
public class ByteHeader {
    private int headerLength;
    private int offset;
    private ByteOrder byteOrder;
}
