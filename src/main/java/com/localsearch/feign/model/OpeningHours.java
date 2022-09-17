package com.localsearch.feign.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OpeningHours {

    @JsonProperty("days")
    private Days days;
}
