package com.example.tgbotspringboot;

import com.example.tgbotspringboot.Entity.Filter;
import com.example.tgbotspringboot.Entity.Vacancy;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class Bot {
    private List<Vacancy> list;
    private Logic logic;
    private Filter filter;
    private ArrayList<String> listArray;

    @Autowired
    public Bot(Logic logic){
        this.logic = logic;
        listArray = new ArrayList<>();
        TelegramBot bot = new TelegramBot("6184077336:AAGMjTpSF-zYlTs-kc7-0N56Y1ZNC1j4VH0");
        bot.setUpdatesListener(element -> {
            System.out.println(element);
            element.forEach(it->{
                if (it.message().text().equals("/start")){
                    bot.execute(new SendMessage(it.message().chat().id(), "Приветствую тебя друг" +
                            "\nВведи название вакансии, город поиска, опыт работы, заработная плата. Каждый пункт запроса отправляем в чат. Что бы начать поиск отправь в чат слово -Найти-:" + "\nЕсли город поиска не уникальный введи в формате:" +
                            "\nГород (Область)" + "\nВ опыте пишем количество лет (1.5 года или 6 лет)"));
                } else {
                    if (!it.message().text().equals("Найти")){
                        listArray.add(it.message().text());
                        System.out.println(it.message().text());
                    } else {
                        filter = getFilterResponse(listArray);
                        if (filter == null){
                            list = new ArrayList<>();
                        } else {
                            list = logic.getVacancyFilter(filter);
                            listArray = new ArrayList<>();
                        }
                        if (list.size() != 0){
                            list.forEach(vacancy -> {
                                bot.execute(new SendMessage(it.message().chat().id(), "Вакансия: " + vacancy.getName() + "\nКоличество откликов: " + vacancy.getCounters().getResponses() + "\nСсылка: http://hh.ru/vacancy/" + vacancy.getId()));
                                System.out.println(vacancy.getId() + " " + vacancy.getName());
                            });
                            bot.execute(new SendMessage(it.message().chat().id(), "Количество найденых вакансий: " + list.size()));
                        } else {
                            bot.execute(new SendMessage(it.message().chat().id(), "По вашему запросу вакансий не найдено"));
                        }
                    }
                }
            });
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    public int overRideSalary(String salary){
        String res = "";
        char s[] = salary.split(" ")[0].toCharArray();
        for (int i = 0; i < s.length; i++){
            if (s[i] == '.' || s[i] == ','){
                break;
            }
            res = res + (s[i]);
        }
        return Integer.parseInt(res);
    }

    public Filter getFilterResponse(ArrayList<String> listArray){
        if (listArray.size() == 2){
            return new Filter(listArray.get(0),listArray.get(1));
        } else if (listArray.size() == 3){
            if (listArray.get(2).contains("год") || listArray.get(2).contains("лет")){
                return new Filter(listArray.get(0),listArray.get(1),listArray.get(2));
            } else {
                return new Filter(listArray.get(0),listArray.get(1),overRideSalary(listArray.get(2)));
            }
        } else if (listArray.size() == 4){
            return new Filter(listArray.get(0),listArray.get(1),listArray.get(2),overRideSalary(listArray.get(3)));
        }
        return null;
    }
}
