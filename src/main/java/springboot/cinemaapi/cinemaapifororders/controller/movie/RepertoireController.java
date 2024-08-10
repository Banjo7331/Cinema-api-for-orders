package springboot.cinemaapi.cinemaapifororders.controller.movie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.RepertoireDto;

@RestController
@RequestMapping("/api/repertoire")
public class RepertoireController {

    @GetMapping
    public ResponseEntity<RepertoireDto> getRepertoire() {

    }
}
