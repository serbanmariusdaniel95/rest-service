package com.localsearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDetails {

    private String name;
    private String address;
    private Map<String, List<String>> openingHours;
}
