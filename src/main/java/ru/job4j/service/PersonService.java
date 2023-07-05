package ru.job4j.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.model.Person;
import ru.job4j.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PersonService {
    private final PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    public Optional<Person> save(Person person) {
        Optional<Person> result = Optional.empty();
        try {
            personRepository.save(person);
            result = Optional.of(person);
        } catch (Exception ex) {
            log.error("User not saved !");
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
        boolean isUpdated = false;
        if (personRepository.findById(person.getId()).isPresent()) {
            throw new IllegalArgumentException("User not deleted !");
        }
        try {
            personRepository.save(person);
            isUpdated = true;
        } catch (Exception ex) {
            log.error("User not updated !");
        }
        return isUpdated;
    }
}
