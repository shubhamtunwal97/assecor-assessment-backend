package com.assecor.assessment.service;

import com.assecor.assessment.model.Person;
import com.assecor.assessment.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    private Person testPerson;

    @BeforeEach
    void setUp() {
        testPerson = new Person(1L, "Hans", "Müller", "67742", "Lauterecken", "blau");
    }

    @Test
    void getAllPersons_ShouldReturnAllPersons() {
        List<Person> expectedPersons = Arrays.asList(testPerson);
        when(personRepository.findAll()).thenReturn(expectedPersons);

        List<Person> actualPersons = personService.getAllPersons();

        assertEquals(expectedPersons, actualPersons);
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void getPersonById_ShouldReturnPerson() {
        when(personRepository.findById(1L)).thenReturn(Optional.of(testPerson));

        Optional<Person> result = personService.getPersonById(1L);

        assertTrue(result.isPresent());
        assertEquals(testPerson, result.get());
        verify(personRepository, times(1)).findById(1L);
    }

    @Test
    void getPersonsByColor_ShouldReturnPersonsWithColor() {
        List<Person> expectedPersons = Arrays.asList(testPerson);
        when(personRepository.findByColor("blau")).thenReturn(expectedPersons);

        List<Person> actualPersons = personService.getPersonsByColor("blau");

        assertEquals(expectedPersons, actualPersons);
        verify(personRepository, times(1)).findByColor("blau");
    }

    @Test
    void createPerson_ShouldSavePerson() {
        when(personRepository.save(any(Person.class))).thenReturn(testPerson);

        Person result = personService.createPerson(testPerson);

        assertEquals(testPerson, result);
        verify(personRepository, times(1)).save(testPerson);
    }
}