package docman;

import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class DocmanServerApplication {
    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(DocmanServerApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}



