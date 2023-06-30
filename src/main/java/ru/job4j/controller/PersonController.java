package ru.job4j.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/")
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

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        var savedPerson = this.persons.save(person);
        return persons.findById(person.getId()).isEmpty()
                ? new ResponseEntity<>(savedPerson, HttpStatus.CREATED) : new ResponseEntity<>(savedPerson, HttpStatus.CONFLICT);
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person,
                                       Model model) {
        this.persons.save(person);
        if (persons.findById(person.getId()).isEmpty()) {
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
        this.persons.delete(person);
        if (persons.findById(person.getId()).isPresent()) {
            model.addAttribute("errorMessage", "User not deleted !");
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
