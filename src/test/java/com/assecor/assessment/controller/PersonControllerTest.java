package com.assecor.assessment.controller;

import com.assecor.assessment.model.Person;
import com.assecor.assessment.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    private Person testPerson1;
    private Person testPerson2;
    private Person testPerson3;

    @BeforeEach
    void setUp() {
        testPerson1 = new Person(1L, "Hans", "Müller", "67742", "Lauterecken", "blau");
        testPerson2 = new Person(2L, "Peter", "Petersen", "18439", "Stralsund", "grün");
        testPerson3 = new Person(3L, "Johnny", "Johnson", "88888", "made up", "violett");
    }

    @Test
    void getAllPersons_ShouldReturnListOfPersons() throws Exception {
        List<Person> persons = Arrays.asList(testPerson1, testPerson2, testPerson3);
        when(personService.getAllPersons()).thenReturn(persons);

        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Hans"))
                .andExpect(jsonPath("$[0].lastname").value("Müller"))
                .andExpect(jsonPath("$[0].zipcode").value("67742"))
                .andExpect(jsonPath("$[0].city").value("Lauterecken"))
                .andExpect(jsonPath("$[0].color").value("blau"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Peter"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("Johnny"));
    }

    @Test
    void getPersonById_WhenPersonExists_ShouldReturnPerson() throws Exception {
        when(personService.getPersonById(1L)).thenReturn(Optional.of(testPerson1));

        mockMvc.perform(get("/persons/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Hans"))
                .andExpect(jsonPath("$.lastname").value("Müller"))
                .andExpect(jsonPath("$.zipcode").value("67742"))
                .andExpect(jsonPath("$.city").value("Lauterecken"))
                .andExpect(jsonPath("$.color").value("blau"));
    }

    @Test
    void getPersonById_WhenPersonDoesNotExist_ShouldReturnNotFound() throws Exception {
        when(personService.getPersonById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/persons/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPersonsByColor_ShouldReturnPersonsWithSpecificColor() throws Exception {
        List<Person> bluePersons = Arrays.asList(testPerson1);
        when(personService.getPersonsByColor("blau")).thenReturn(bluePersons);

        mockMvc.perform(get("/persons/color/blau"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Hans"))
                .andExpect(jsonPath("$[0].color").value("blau"));
    }

    @Test
    void getPersonsByColor_WhenNoPersonsWithColor_ShouldReturnEmptyList() throws Exception {
        when(personService.getPersonsByColor("nonexistent")).thenReturn(Arrays.asList());

        mockMvc.perform(get("/persons/color/nonexistent"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void createPerson_ShouldCreateAndReturnNewPerson() throws Exception {
        Person newPerson = new Person(null, "John", "Doe", "12345", "Teststadt", "rot");
        Person savedPerson = new Person(4L, "John", "Doe", "12345", "Teststadt", "rot");

        when(personService.createPerson(any(Person.class))).thenReturn(savedPerson);

        mockMvc.perform(post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPerson)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.lastname").value("Doe"))
                .andExpect(jsonPath("$.zipcode").value("12345"))
                .andExpect(jsonPath("$.city").value("Teststadt"))
                .andExpect(jsonPath("$.color").value("rot"));
    }
}