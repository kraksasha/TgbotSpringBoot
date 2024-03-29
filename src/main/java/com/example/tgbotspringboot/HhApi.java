package com.example.tgbotspringboot;

import com.example.tgbotspringboot.Entity.Country;
import com.example.tgbotspringboot.Entity.ListVacancies;
import com.example.tgbotspringboot.Entity.Vacancy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.io.IOException;
import java.util.List;
@Component
public class HhApi {
    private ObjectMapper objectMapper;
    private WebClient webClient;
    @Autowired
    public HhApi(WebClient webClient, ObjectMapper objectMapper){
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    public List<Vacancy> getVacanciesFilterNameRegion(String nameVacancy, String nameRegion){
        WebClient.ResponseSpec responseSpec = webClient.get()
                .uri("/vacancies?" + "text=" + nameVacancy + "&area=" + getIdRegion(nameRegion) + "&responses_count_enabled=true")
                .retrieve();
        String body = responseSpec.bodyToMono(String.class).block();
        System.out.println(body);
        try {
            ListVacancies lV = objectMapper.readValue(body,ListVacancies.class);
            return lV.getItems();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vacancy> getVacanciesFilterNameRegionExperience(String nameVacancy, String nameRegion, String idExperience){
        WebClient.ResponseSpec responseSpecNre = webClient.get()
                .uri("/vacancies?" + "text=" + nameVacancy + "&area=" + getIdRegion(nameRegion) + "&experience=" + idExperience  + "&responses_count_enabled=true")
                .retrieve();
        String bodyNre = responseSpecNre.bodyToMono(String.class).block();
        System.out.println(bodyNre);
        try {
            ListVacancies lV = objectMapper.readValue(bodyNre,ListVacancies.class);
            return lV.getItems();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vacancy> getVacanciesFilterNameRegionSalary(String nameVacancy, String nameRegion, String salary){
        WebClient.ResponseSpec responseSpecNrs = webClient.get()
                .uri("/vacancies?" + "text=" + nameVacancy + "&area=" + getIdRegion(nameRegion) + "&salary=" + salary  + "&responses_count_enabled=true")
                .retrieve();
        String bodyNrs = responseSpecNrs.bodyToMono(String.class).block();
        System.out.println(bodyNrs);
        try {
            ListVacancies lV = objectMapper.readValue(bodyNrs,ListVacancies.class);
            return lV.getItems();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vacancy> getVacanciesFilterNameRegionExperienceSalary(String nameVacancy, String nameRegion, String idExperience, String salary){
        WebClient.ResponseSpec responseSpecNres = webClient.get()
                .uri("/vacancies?" + "text=" + nameVacancy + "&area=" + getIdRegion(nameRegion) + "&experience=" + idExperience + "&salary=" + salary   + "&responses_count_enabled=true")
                .retrieve();
        String bodyNres = responseSpecNres.bodyToMono(String.class).block();
        System.out.println(bodyNres);
        try {
            ListVacancies lV = objectMapper.readValue(bodyNres,ListVacancies.class);
            return lV.getItems();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getIdRegion(String nameRegion){
        String idAreas = null;
        WebClient.ResponseSpec responseSpecArea = webClient.get()
                .uri("/areas/113")
                .retrieve();
        String bodyArea = responseSpecArea.bodyToMono(String.class).block();
        System.out.println(bodyArea);
        try {
            Country cT = objectMapper.readValue(bodyArea, Country.class);
            System.out.println(nameRegion);
            for (int i = 0; i < cT.getAreas().size(); i++){
                for (int j = 0; j < cT.getAreas().get(i).getAreas().size(); j++){
                    if (nameRegion.equals("Москва")){
                        idAreas = "1";
                        break;
                    } else {
                        if (cT.getAreas().get(i).getAreas().get(j).getName().equals(nameRegion)){
                            idAreas = cT.getAreas().get(i).getAreas().get(j).getId();
                            break;
                        }
                    }
                }
                if (idAreas != null){
                    break;
                }
            }
            System.out.println(idAreas);
            if (idAreas == null){
                idAreas = "2";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return idAreas;
    }
}
