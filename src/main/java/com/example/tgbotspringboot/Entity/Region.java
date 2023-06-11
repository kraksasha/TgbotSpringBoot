package com.example.tgbotspringboot.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Region {

    private String name;
    private List<Town> areas;
}
