package com.assecor.assessment.repository;

import com.assecor.assessment.model.Person;
import com.assecor.assessment.model.PersonEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("database-test")
class DatabasePersonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonEntityRepository personEntityRepository;

    private DatabasePersonRepository databasePersonRepository;

    @BeforeEach
    void setUp() {
        databasePersonRepository = new DatabasePersonRepository(personEntityRepository);

        PersonEntity entity1 = new PersonEntity("Hans", "Müller", "67742", "Lauterecken", "blau");
        PersonEntity entity2 = new PersonEntity("Peter", "Petersen", "18439", "Stralsund", "grün");
        PersonEntity entity3 = new PersonEntity("Anna", "Schmidt", "12345", "Berlin", "blau");

        entityManager.persistAndFlush(entity1);
        entityManager.persistAndFlush(entity2);
        entityManager.persistAndFlush(entity3);
    }

    @Test
    void testFindAll() {
        List<Person> persons = databasePersonRepository.findAll();

        assertThat(persons).hasSize(3);
        assertThat(persons.get(0).getName()).isEqualTo("Hans");
        assertThat(persons.get(1).getName()).isEqualTo("Peter");
        assertThat(persons.get(2).getName()).isEqualTo("Anna");
    }

    @Test
    void testFindById() {
        List<PersonEntity> entities = personEntityRepository.findAll();
        Long firstId = entities.get(0).getId();

        Optional<Person> person = databasePersonRepository.findById(firstId);

        assertThat(person).isPresent();
        assertThat(person.get().getName()).isEqualTo("Hans");
        assertThat(person.get().getLastname()).isEqualTo("Müller");
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Person> person = databasePersonRepository.findById(999L);

        assertThat(person).isEmpty();
    }

    @Test
    void testFindByColor() {
        List<Person> bluePersons = databasePersonRepository.findByColor("blau");

        assertThat(bluePersons).hasSize(2);
        assertThat(bluePersons).extracting(Person::getName).containsExactlyInAnyOrder("Hans", "Anna");
    }

    @Test
    void testFindByColorCaseInsensitive() {
        List<Person> bluePersons = databasePersonRepository.findByColor("BLAU");

        assertThat(bluePersons).hasSize(2);
        assertThat(bluePersons).extracting(Person::getName).containsExactlyInAnyOrder("Hans", "Anna");
    }

    @Test
    void testSaveNewPerson() {
        Person newPerson = new Person(null, "Max", "Mustermann", "54321", "Hamburg", "rot");

        Person savedPerson = databasePersonRepository.save(newPerson);

        assertThat(savedPerson.getId()).isNotNull();
        assertThat(savedPerson.getName()).isEqualTo("Max");
        assertThat(savedPerson.getColor()).isEqualTo("rot");

        List<Person> allPersons = databasePersonRepository.findAll();
        assertThat(allPersons).hasSize(4);
    }

    @Test
    void testUpdateExistingPerson() {
        List<PersonEntity> entities = personEntityRepository.findAll();
        Long firstId = entities.get(0).getId();

        Person existingPerson = new Person(firstId, "Hans Updated", "Müller", "67742", "Lauterecken", "rot");

        Person updatedPerson = databasePersonRepository.save(existingPerson);

        assertThat(updatedPerson.getId()).isEqualTo(firstId);
        assertThat(updatedPerson.getName()).isEqualTo("Hans Updated");
        assertThat(updatedPerson.getColor()).isEqualTo("rot");

        List<Person> allPersons = databasePersonRepository.findAll();
        assertThat(allPersons).hasSize(3);
    }
}