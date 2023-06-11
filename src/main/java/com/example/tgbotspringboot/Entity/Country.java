package com.example.tgbotspringboot.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Country {

    private String name;
    private List<Region> areas;
}
