package springboot.cinemaapi.cinemaapifororders;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CinemaApiForOrdersApplication {
    @Value("${openai.api.key}")
    private String apiKey;

//    @Value("${openai.chatgpt.api.key}")
//    private String chatgptApiKey;
    public static void main(String[] args) {
        SpringApplication.run(CinemaApiForOrdersApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        return modelMapper;
    }

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + apiKey);

            return execution.execute(request, body);
        }));

        return restTemplate;
    }
}
