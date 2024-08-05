package springboot.cinemaapi.cinemaapifororders;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CinemaApiForOrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaApiForOrdersApplication.class, args);
    }

    @Bean
    public ModelMapper modelMaper(){
        return new ModelMapper();
    }
}
