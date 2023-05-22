package com.example.tgbotspringboot;

import com.example.tgbotspringboot.Entity.Vacancy;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class Bot {
    private HhApi hhApi;
    private List<Vacancy> list;

    @Autowired
    public Bot(HhApi hhApi){
        this.hhApi = hhApi;
        TelegramBot bot = new TelegramBot("6184077336:AAGMjTpSF-zYlTs-kc7-0N56Y1ZNC1j4VH0");
        bot.setUpdatesListener(element -> {
            System.out.println(element);
            element.forEach(it->{
                String massive[] = it.message().text().split("&");
                if (massive[0].equals("/start") || massive.length < 2){
                    bot.execute(new SendMessage(it.message().chat().id(), "Приветствую тебя кожанный холоп" +
                            "\nВведи название вакансии(одним словом), город поиска, опыт работы, заработная плата через & в виде:" + "\nВакансия&Город&1.5 года&40000" + "\nЕсли город поиска не уникальный введи в формате:" +
                            "\nВакансия&Город (Область)&1.5 года&50000"));
                } else {
                    if (massive.length == 2){
                        list = hhApi.getVacanciesFilterNameRegion(massive[0],massive[1]);
                    }
                    if (massive.length == 3){
                        if (experienceOrSalary(massive[2]) < 40){
                            list = hhApi.getVacanciesFilterNameRegionExperience(massive[0],massive[1],overRide(massive[2]));
                        } else {
                            list = hhApi.getVacanciesFilterNameRegionSalary(massive[0],massive[1],massive[2]);
                        }
                    }
                    if (massive.length == 4){
                        list = hhApi.getVacanciesFilterNameRegionExperienceSalary(massive[0],massive[1],overRide(massive[2]),massive[3]);
                    }
                    if (list.size() != 0){
                        list.forEach(vacancy -> {
                            bot.execute(new SendMessage(it.message().chat().id(), "Вакансия: " + vacancy.getName() + "\nКоличество откликов: " + vacancy.getCounters().getResponses() + "\nСсылка: http://hh.ru/vacancy/" + vacancy.getId()));
                            System.out.println(vacancy.getId() + " " + vacancy.getName());
                        });
                    } else {
                        bot.execute(new SendMessage(it.message().chat().id(), "Данных вакансий в городе " + massive[1] +" не найдено"));
                    }
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    public String overRide(String nameExperience){
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
        if (Double.parseDouble(massiveNameExperience[0]) >= 3 && Double.parseDouble(massiveNameExperience[0]) < 6){
            return massive[2];
        }
        if (Double.parseDouble(massiveNameExperience[0]) >= 6){
            return massive[3];
        }
        return massive[1];
    }

    public double experienceOrSalary(String secondElementMassive){
        String secondElementMassiveSplit[] = secondElementMassive.split(" ");
        char s[] = secondElementMassiveSplit[0].toCharArray();
        for (int i = 0; i < s.length; i++){
            if (s[i] == ','){
                s[i] = '.';
            }
        }
        secondElementMassiveSplit[0] = String.valueOf(s);
        return Double.parseDouble(secondElementMassiveSplit[0]);
    }
}
