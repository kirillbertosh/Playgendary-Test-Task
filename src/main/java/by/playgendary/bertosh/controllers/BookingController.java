package by.playgendary.bertosh.controllers;

import by.playgendary.bertosh.entities.Booking;
import by.playgendary.bertosh.payloads.BookingRequest;
import by.playgendary.bertosh.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/bookings")
public class BookingController {

    @Autowired
    private BookingService service;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody BookingRequest bookingRequest) {
        Booking booking = service.save(bookingRequest);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Booking booking) {
        booking = service.update(id, booking);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Booking booking = service.findById(id);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity findAll() {
        List<Booking> bookingList = service.findAll();
        return new ResponseEntity<>(bookingList, HttpStatus.OK);
    }

    @GetMapping("user/{id}")
    public ResponseEntity findUserBookings(@PathVariable Long userId) {
        List<Booking> userBookings = service.findUserBookings(userId);
        return new ResponseEntity<>(userBookings, HttpStatus.OK);
    }
}
