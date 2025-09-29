package com.assecor.assessment.service;

import com.assecor.assessment.model.Person;
import com.assecor.assessment.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    public List<Person> getPersonsByColor(String color) {
        return personRepository.findByColor(color);
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }
}