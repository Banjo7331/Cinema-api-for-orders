package springboot.cinemaapi.cinemaapifororders.controller.movie;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.payload.dto.movie.MovieDto;
import springboot.cinemaapi.cinemaapifororders.service.movie.MovieService;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> findAllMovies() {
        List<MovieDto> movies = movieService.getAllMovies();

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> findMovieById(@PathVariable Long id) {

        return ResponseEntity.ok(movieService.getMovieById(id));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<MovieDto> addMovie(@RequestBody MovieDto movie) {
        return new ResponseEntity<>(movieService.addMovie(movie),HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@RequestBody MovieDto movie, @PathVariable Long id) {
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
