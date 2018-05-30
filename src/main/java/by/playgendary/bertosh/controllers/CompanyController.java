package by.playgendary.bertosh.controllers;

import by.playgendary.bertosh.entities.Company;
import by.playgendary.bertosh.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/apt/v1/companies")
public class CompanyController {

    @Autowired
    private CompanyService service;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Company company) {
        company = service.save(company);
        return new ResponseEntity<>(company, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Company company) {
        company = service.update(id, company);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        Company company = service.findById(id);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }
}
