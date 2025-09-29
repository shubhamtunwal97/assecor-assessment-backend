package com.assecor.assessment.repository;

import com.assecor.assessment.model.Person;
import com.opencsv.CSVReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CsvPersonRepository implements PersonRepository {

    private final List<Person> persons;
    private final AtomicLong idGenerator;

    public CsvPersonRepository() {
        this.persons = new ArrayList<>();
        this.idGenerator = new AtomicLong(0);
        loadPersonsFromCsv();
    }

    private void loadPersonsFromCsv() {
        try (CSVReader csvReader = new CSVReader(
                new InputStreamReader(
                    new ClassPathResource("sample-input.csv").getInputStream(),
                    StandardCharsets.UTF_8))) {

            String[] values;
            long lineNumber = 1;

            while ((values = csvReader.readNext()) != null) {
                if (values.length >= 4) {
                    String lastname = values[0].trim();
                    String name = values[1].trim();
                    String location = values[2].trim();
                    String colorIdStr = values[3].trim();

                    String[] locationParts = location.split(" ", 2);
                    String zipcode = locationParts.length > 0 ? locationParts[0] : "";
                    String city = locationParts.length > 1 ? locationParts[1] : "";

                    try {
                        Integer colorId = Integer.parseInt(colorIdStr);
                        Person person = new Person(lineNumber, lastname, name, zipcode, city, colorId);
                        persons.add(person);
                        idGenerator.set(lineNumber);
                        lineNumber++;
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid color ID in line " + lineNumber + ": " + colorIdStr);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(persons);
    }

    @Override
    public Optional<Person> findById(Long id) {
        return persons.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Person> findByColor(String color) {
        return persons.stream()
                .filter(person -> person.getColor() != null && person.getColor().equalsIgnoreCase(color))
                .toList();
    }

    @Override
    public Person save(Person person) {
        if (person.getId() == null) {
            person.setId(idGenerator.incrementAndGet());
        }

        Optional<Person> existingPerson = findById(person.getId());
        if (existingPerson.isPresent()) {
            persons.remove(existingPerson.get());
        }

        persons.add(person);
        return person;
    }
}