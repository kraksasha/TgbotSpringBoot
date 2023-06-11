package com.example.tgbotspringboot.Entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Filter {
    private String nameVacancy;
    private String nameRegion;
    private String experience;
    private int salary;

    public Filter(String nameVacancy, String nameRegion, int salary){
        this.nameVacancy = nameVacancy;
        this.nameRegion = nameRegion;
        this.salary = salary;
    }

    public Filter(String nameVacancy, String nameRegion, String experience) {
        this.nameVacancy = nameVacancy;
        this.nameRegion = nameRegion;
        this.experience = experience;
    }

    public Filter(String nameVacancy, String nameRegion) {
        this.nameVacancy = nameVacancy;
        this.nameRegion = nameRegion;
    }
}
