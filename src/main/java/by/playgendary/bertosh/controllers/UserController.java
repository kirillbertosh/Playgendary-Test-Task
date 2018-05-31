package by.playgendary.bertosh.controllers;

import by.playgendary.bertosh.entities.User;
import by.playgendary.bertosh.payloads.UserRequest;
import by.playgendary.bertosh.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody UserRequest userRequest) {
        User user = service.save(userRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        User user = service.update(id, userRequest);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        User user = service.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity findAll() {
        List<User> userList = service.findAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity findByEmail(@PathVariable String email) {
        User user = service.findByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }
}
