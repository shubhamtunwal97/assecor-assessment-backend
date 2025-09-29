package com.assecor.assessment.repository;

import com.assecor.assessment.model.Person;
import com.assecor.assessment.model.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@ConditionalOnProperty(name = "app.datasource.type", havingValue = "database")
public class DatabasePersonRepository implements PersonRepository {

    private final PersonEntityRepository personEntityRepository;

    @Autowired
    public DatabasePersonRepository(PersonEntityRepository personEntityRepository) {
        this.personEntityRepository = personEntityRepository;
    }

    @Override
    public List<Person> findAll() {
        return personEntityRepository.findAll()
                .stream()
                .map(PersonEntity::toPerson)
                .toList();
    }

    @Override
    public Optional<Person> findById(Long id) {
        return personEntityRepository.findById(id)
                .map(PersonEntity::toPerson);
    }

    @Override
    public List<Person> findByColor(String color) {
        return personEntityRepository.findByColorIgnoreCase(color)
                .stream()
                .map(PersonEntity::toPerson)
                .toList();
    }

    @Override
    public Person save(Person person) {
        PersonEntity entity = PersonEntity.fromPerson(person);
        PersonEntity savedEntity = personEntityRepository.save(entity);
        return savedEntity.toPerson();
    }
}