package by.playgendary.bertosh.controllers;

import by.playgendary.bertosh.entities.Room;
import by.playgendary.bertosh.payloads.RoomRequest;
import by.playgendary.bertosh.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    @Autowired
    private RoomService service;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody RoomRequest roomRequest) {
        Room room = service.save(roomRequest);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Room room) {
        room = service.update(id, room);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Room room = service.findById(id);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity findAll() {
        List<Room> roomList = service.findAll();
        return new ResponseEntity<>(roomList, HttpStatus.OK);
    }

    @GetMapping("/number/{number}")
    public ResponseEntity findByNumber(@PathVariable Integer number) {
        Room room = service.findByNumber(number);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }
}
