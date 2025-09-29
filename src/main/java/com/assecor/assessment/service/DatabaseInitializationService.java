package com.assecor.assessment.service;

import com.assecor.assessment.model.Person;
import com.assecor.assessment.model.PersonEntity;
import com.assecor.assessment.repository.PersonEntityRepository;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
@ConditionalOnProperty(name = "app.datasource.type", havingValue = "database")
public class DatabaseInitializationService {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializationService.class);

    private final PersonEntityRepository personEntityRepository;

    @Autowired
    public DatabaseInitializationService(PersonEntityRepository personEntityRepository) {
        this.personEntityRepository = personEntityRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeDatabase() {
        if (personEntityRepository.count() == 0) {
            logger.info("Database is empty, initializing with CSV data...");
            loadDataFromCsv();
        } else {
            logger.info("Database already contains {} persons", personEntityRepository.count());
        }
    }

    private void loadDataFromCsv() {
        try (CSVReader csvReader = new CSVReader(
                new InputStreamReader(
                    new ClassPathResource("sample-input.csv").getInputStream(),
                    StandardCharsets.UTF_8))) {

            String[] values;
            int recordCount = 0;

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
                        String color = Person.getColorByNumber(colorId);

                        PersonEntity entity = new PersonEntity(name, lastname, zipcode, city, color);
                        personEntityRepository.save(entity);
                        recordCount++;
                    } catch (NumberFormatException e) {
                        logger.warn("Invalid color ID in CSV: {}", colorIdStr);
                    }
                }
            }

            logger.info("Successfully loaded {} persons from CSV into database", recordCount);

        } catch (Exception e) {
            logger.error("Error loading CSV data into database", e);
        }
    }
}