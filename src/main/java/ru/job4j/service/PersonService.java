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

    public Optional<Person> save(Person person) {
        Optional<Person> result = Optional.of(person);
        try {
            personRepository.save(person);
        } catch (Exception ex) {
            result = Optional.empty();
        }
        return result;
    }

    public boolean delete(Person person) {
        boolean isDeleted = true;
        personRepository.delete(person);
        if (personRepository.findById(person.getId()).isPresent()) {
            isDeleted = false;
        }
        return isDeleted;
    }

    public boolean update(Person person) {
        personRepository.delete(person);
        if (personRepository.findById(person.getId()).isPresent()) {
            return false;
        }
        try {
            personRepository.save(person);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
