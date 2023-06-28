package com.example.tgbotspringboot;

import com.example.tgbotspringboot.Entity.Filter;
import com.example.tgbotspringboot.Entity.Vacancy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Logic {
    private HhApi hhApi;

    @Autowired
    public Logic(HhApi hhApi) {
        this.hhApi = hhApi;
    }

    public List<Vacancy> getVacancyFilter(Filter filter){
        if (filter.getExperience() == null && filter.getSalary() == 0){
            return hhApi.getVacanciesFilterNameRegion(filter.getNameVacancy(),filter.getNameRegion());
        }
        if (filter.getExperience() != null && filter.getSalary() == 0){
            return hhApi.getVacanciesFilterNameRegionExperience(filter.getNameVacancy(),filter.getNameRegion(), overRideExperience(filter.getExperience()));
        }
        if (filter.getExperience() == null && filter.getSalary() != 0){
            return hhApi.getVacanciesFilterNameRegionSalary(filter.getNameVacancy(),filter.getNameRegion(),String.valueOf(filter.getSalary()));
        }
        if (filter.getExperience() != null && filter.getSalary() != 0){
            return hhApi.getVacanciesFilterNameRegionExperienceSalary(filter.getNameVacancy(), filter.getNameRegion(), overRideExperience(filter.getExperience()), String.valueOf(filter.getSalary()));
        }
        return null;
    }

    public String overRideExperience(String nameExperience){
        double exper;
        String massive[] = {"noExperience","between1And3","between3And6","moreThan6"};
        String massiveNameExperience[] = nameExperience.split(" ");
        char s[] = massiveNameExperience[0].toCharArray();
        for (int i = 0; i < s.length; i++){
            if (s[i] == ','){
                s[i] = '.';
            }
        }
        massiveNameExperience[0] = String.valueOf(s);
        exper = Double.parseDouble(massiveNameExperience[0]);
        if (exper < 1){
            return massive[0];
        }
        if (exper >= 1 && exper < 3){
            return massive[1];
        }
        if (exper >= 3 && exper <= 6){
            return massive[2];
        }
        if (exper > 6){
            return massive[3];
        }
        return null;
    }
}
