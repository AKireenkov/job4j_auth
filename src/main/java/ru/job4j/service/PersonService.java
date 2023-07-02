package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.model.Person;
import ru.job4j.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    public boolean save(Person person) {
        boolean isSaved = true;
        personRepository.save(person);
        if (personRepository.findById(person.getId()).isEmpty()) {
            isSaved = false;
        }
        return isSaved;
    }

    public boolean delete(Person person) {
        boolean isDeleted = true;
        personRepository.delete(person);
        if (personRepository.findById(person.getId()).isPresent()) {
            isDeleted = false;
        }
        return isDeleted;
    }
}
