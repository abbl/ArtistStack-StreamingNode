package ovh.abbl.streamnode.repositories;

public interface AudioRepository {
    byte[] load(String fileName);
}
