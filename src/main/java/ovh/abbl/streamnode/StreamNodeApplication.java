package ovh.abbl.streamnode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import ovh.abbl.streamnode.files.encoder.impl.WaveFileEncoder;

@SpringBootApplication()
@ComponentScan("ovh.abbl.streamnode")
public class StreamNodeApplication {
	public static void main(String[] args) {
		SpringApplication.run(StreamNodeApplication.class, args);
	}
}
