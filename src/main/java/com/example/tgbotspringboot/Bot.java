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

    @Autowired
    public Bot(HhApi hhApi){
        this.hhApi = hhApi;
        TelegramBot bot = new TelegramBot("6184077336:AAGMjTpSF-zYlTs-kc7-0N56Y1ZNC1j4VH0");
        bot.setUpdatesListener(element -> {
            System.out.println(element);
            element.forEach(it->{
                String massive[] = it.message().text().split(" ");
                if (massive[0].equals("/start") || massive.length < 2){
                    bot.execute(new SendMessage(it.message().chat().id(), "Приветствую тебя кожанный холоп" +
                            "\nВведи название вакансии(одним словом) и город поиска в виде:" + "\nВакансия Город" + "\nЕсли город поиска не уникальный введи в формате:" +
                            "\nВакансия Город (Область)"));
                } else {
                    if (massive.length > 2){
                        massive[1] = overRide(massive);
                    }
                    List<Vacancy> list = hhApi.getVacanciesFilterNameRegion(massive[0],massive[1]);
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


    public String overRide(String s[]){
        String reg = s[1];
        for (int i = 2; i < s.length; i++){
            reg = reg + " " + s[i];
        }
        return reg;
    }
}
