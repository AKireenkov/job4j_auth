package ru.job4j.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.model.Person;
import ru.job4j.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {
    private final PersonService persons;
    private BCryptPasswordEncoder encoder;


    @GetMapping("/all")
    public List<Person> findAll() {
        return this.persons.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = this.persons.findById(id);
        return new ResponseEntity<Person>(
                person.orElse(new Person()),
                person.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        person.setPassword(encoder.encode(person.getPassword()));
        return this.persons.save(person).isPresent()
                ? new ResponseEntity<>(person, HttpStatus.CREATED) : new ResponseEntity<>(person, HttpStatus.CONFLICT);
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person,
                                       Model model) {
        if (!this.persons.update(person)) {
            model.addAttribute("errorMessage", "User not updated !");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id,
                                       Model model) {
        Person person = new Person();
        person.setId(id);
        if (!this.persons.delete(person)) {
            model.addAttribute("errorMessage", "User not deleted !");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
