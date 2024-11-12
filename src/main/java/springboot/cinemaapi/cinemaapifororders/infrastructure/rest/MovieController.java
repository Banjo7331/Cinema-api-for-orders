package springboot.cinemaapi.cinemaapifororders.infrastructure.rest;


import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.application.dto.MovieDto;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.MovieService;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<Page<MovieDto>> getAllMovies(@RequestParam(defaultValue = "0") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer size) {
        Page<MovieDto> movies = movieService.findAllMovies(page,size);

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {

        return ResponseEntity.ok(movieService.findMovieById(id));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<MovieDto> createMovie(@Valid @RequestBody MovieDto movie) {
        return new ResponseEntity<>(movieService.addMovie(movie),HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@Valid @RequestBody MovieDto movie, @PathVariable Long id) {
        return ResponseEntity.ok(movieService.updateMovie(id,movie));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);

        return ResponseEntity.ok("Deleted movie successfully");
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @DeleteMapping
    public ResponseEntity<String> deleteAllMovies() {
        movieService.deleteAllMovies();

        return ResponseEntity.ok("All movies Deleted from database successfully");
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYER')")
    @DeleteMapping("/expired")
    public ResponseEntity<String> deleteExpiredMovies() {
        movieService.deleteExpiredMovies();

        return ResponseEntity.ok("All expired movies Deleted from database successfully");
    }
}
