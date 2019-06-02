package ovh.abbl.streamnode.repositories;

import java.io.File;

public interface AudioRepository {
    byte[] load(String fileName);
    File getFile(String fileName);
}
