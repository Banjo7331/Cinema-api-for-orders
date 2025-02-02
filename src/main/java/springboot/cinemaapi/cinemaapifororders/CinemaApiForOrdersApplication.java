package springboot.cinemaapi.cinemaapifororders;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//@EnableAdminServer
@SpringBootApplication
public class CinemaApiForOrdersApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaApiForOrdersApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
