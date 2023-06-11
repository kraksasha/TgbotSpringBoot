package com.example.tgbotspringboot;

import com.example.tgbotspringboot.Entity.Filter;
import com.example.tgbotspringboot.Entity.Vacancy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

@Component
public class Logic {
    private HhApi hhApi;

    @Autowired
    public Logic(HhApi hhApi) {
        this.hhApi = hhApi;
    }

    public List<Vacancy> getVacancyFilter(Filter filter){
        Field field[] = filter.getClass().getDeclaredFields();
        field[2].setAccessible(true);
        field[3].setAccessible(true);
        try {
            if (field[2].get(filter) == null && Integer.parseInt(String.valueOf(field[3].get(filter))) == 0){
                return hhApi.getVacanciesFilterNameRegion(filter.getNameVacancy(),filter.getNameRegion());
            }
            if (field[2].get(filter) != null && Integer.parseInt(String.valueOf(field[3].get(filter))) == 0){
                return hhApi.getVacanciesFilterNameRegionExperience(filter.getNameVacancy(),filter.getNameRegion(), overRideExperience(filter.getExperience()));
            }
            if (field[2].get(filter) == null && Integer.parseInt(String.valueOf(field[3].get(filter))) != 0){
                return hhApi.getVacanciesFilterNameRegionSalary(filter.getNameVacancy(),filter.getNameRegion(),String.valueOf(filter.getSalary()));
            }
            if (field[2].get(filter) != null && Integer.parseInt(String.valueOf(field[3].get(filter))) != 0){
                return hhApi.getVacanciesFilterNameRegionExperienceSalary(filter.getNameVacancy(), filter.getNameRegion(), overRideExperience(filter.getExperience()), String.valueOf(filter.getSalary()));
            }
            return null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public String overRideExperience(String nameExperience){
        String massive[] = {"noExperience","between1And3","between3And6","moreThan6"};
        String massiveNameExperience[] = nameExperience.split(" ");
        char s[] = massiveNameExperience[0].toCharArray();
        for (int i = 0; i < s.length; i++){
            if (s[i] == ','){
                s[i] = '.';
            }
        }
        massiveNameExperience[0] = String.valueOf(s);
        if (Double.parseDouble(massiveNameExperience[0]) < 1){
            return massive[0];
        }
        if (Double.parseDouble(massiveNameExperience[0]) >= 1 && Double.parseDouble(massiveNameExperience[0]) < 3){
            return massive[1];
        }
        if (Double.parseDouble(massiveNameExperience[0]) >= 3 && Double.parseDouble(massiveNameExperience[0]) <= 6){
            return massive[2];
        }
        if (Double.parseDouble(massiveNameExperience[0]) > 6){
            return massive[3];
        }
        return null;
    }
}
