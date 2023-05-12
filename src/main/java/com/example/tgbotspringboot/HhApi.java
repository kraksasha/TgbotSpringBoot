package com.example.tgbotspringboot;

import com.example.tgbotspringboot.Entity.Country;
import com.example.tgbotspringboot.Entity.ListVacancies;
import com.example.tgbotspringboot.Entity.Vacancy;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
@Component
public class HhApi {
    private HttpClient httpClient;
    private ObjectMapper objectMapper;

    public HhApi(){
        httpClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<Vacancy> getVacanciesFilterNameRegion(String nameVacancy, String nameRegion){
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("https://api.hh.ru/vacancies?" + "text=" + nameVacancy + "&area=" + getIdRegion(nameRegion) + "&responses_count_enabled=true")).build();
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            System.out.println(body);
            ListVacancies lV = objectMapper.readValue(body,ListVacancies.class);
            return lV.getItems();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vacancy> getVacanciesFilterNameRegionExperience(String nameVacancy, String nameRegion, String idExperience){
        HttpRequest httpRequestNre = HttpRequest.newBuilder().uri(URI.create("https://api.hh.ru/vacancies?" + "text=" + nameVacancy + "&area=" + getIdRegion(nameRegion) + "&experience=" + idExperience  + "&responses_count_enabled=true")).build();
        try {
            HttpResponse<String> responseNre = httpClient.send(httpRequestNre, HttpResponse.BodyHandlers.ofString());
            String bodyNre = responseNre.body();
            System.out.println(bodyNre);
            ListVacancies lV = objectMapper.readValue(bodyNre,ListVacancies.class);
            return lV.getItems();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vacancy> getVacanciesFilterNameRegionSalary(String nameVacancy, String nameRegion, String salary){
        HttpRequest httpRequestNrs = HttpRequest.newBuilder().uri(URI.create("https://api.hh.ru/vacancies?" + "text=" + nameVacancy + "&area=" + getIdRegion(nameRegion) + "&salary=" + salary  + "&responses_count_enabled=true")).build();
        try {
            HttpResponse<String> responseNrs = httpClient.send(httpRequestNrs, HttpResponse.BodyHandlers.ofString());
            String bodyNrs = responseNrs.body();
            System.out.println(bodyNrs);
            ListVacancies lV = objectMapper.readValue(bodyNrs,ListVacancies.class);
            return lV.getItems();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vacancy> getVacanciesFilterNameRegionExperienceSalary(String nameVacancy, String nameRegion, String idExperience, String salary){
        HttpRequest httpRequestNres = HttpRequest.newBuilder().uri(URI.create("https://api.hh.ru/vacancies?" + "text=" + nameVacancy + "&area=" + getIdRegion(nameRegion) + "&experience=" + idExperience + "&salary=" + salary   + "&responses_count_enabled=true")).build();
        try {
            HttpResponse<String> responseNres = httpClient.send(httpRequestNres, HttpResponse.BodyHandlers.ofString());
            String bodyNres = responseNres.body();
            System.out.println(bodyNres);
            ListVacancies lV = objectMapper.readValue(bodyNres,ListVacancies.class);
            return lV.getItems();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public String getIdRegion(String nameRegion){
        HttpRequest httpRequestArea = HttpRequest.newBuilder().uri(URI.create("https://api.hh.ru/areas/113")).build();
        String idAreas = null;
        try {
            HttpResponse<String> responseArea = httpClient.send(httpRequestArea, HttpResponse.BodyHandlers.ofString());
            String bodyArea = responseArea.body();
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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return idAreas;
    }
}
