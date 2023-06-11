package com.example.tgbotspringboot.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vacancy {

    private String id;
    private String name;
    private Counters counters;
}
