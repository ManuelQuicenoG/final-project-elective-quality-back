package com.shelter.animalback.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnimalDto {
    private long id;
    private String name;
    private String breed;
    private String gender;
    private boolean vaccinated;
    private String[] vaccines;

    public AnimalDto(long id, String name, String breed, String gender, boolean vaccinated, String[] vaccines) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.vaccinated = vaccinated;
        this.vaccines = vaccines;
    }
}
