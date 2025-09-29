package com.assecor.assessment.model;

import java.util.Map;

public class Person {
    private Long id;
    private String name;
    private String lastname;
    private String zipcode;
    private String city;
    private String color;

    private static final Map<Integer, String> COLOR_MAPPING = Map.of(
            1, "blau",
            2, "grün",
            3, "violett",
            4, "rot",
            5, "gelb",
            6, "türkis",
            7, "weiß"
    );

    public Person() {
    }

    public Person(Long id, String lastname, String name, String zipcode, String city, Integer colorId) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.zipcode = zipcode;
        this.city = city;
        this.color = COLOR_MAPPING.get(colorId);
    }

    public Person(Long id, String name, String lastname, String zipcode, String city, String color) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.zipcode = zipcode;
        this.city = city;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static String getColorByNumber(Integer colorId) {
        return COLOR_MAPPING.get(colorId);
    }
}