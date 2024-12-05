package springboot.cinemaapi.cinemaapifororders.infrastructure.rest.room;

import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springboot.cinemaapi.cinemaapifororders.application.dto.room.RoomRequest;
import springboot.cinemaapi.cinemaapifororders.application.dto.room.RoomResponse;
import springboot.cinemaapi.cinemaapifororders.application.ports.input.room.RoomService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYER')")
    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        return ResponseEntity.ok(roomService.findAllRooms());
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYER')")
    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable String id) {
        return ResponseEntity.ok(roomService.findRoomById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(@Valid @RequestBody RoomRequest roomDto) {
        return new ResponseEntity<>(roomService.addRoom(roomDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('EMPLOYER')")
    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable String id, @Valid  @RequestBody RoomRequest roomDto) {
        return ResponseEntity.ok(roomService.updateRoom(id, roomDto));
    }
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable String id) {
        roomService.deleteRoom(id);

        return ResponseEntity.ok("Room was successfully deleted");
    }

}
