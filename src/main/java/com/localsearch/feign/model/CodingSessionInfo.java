package com.localsearch.feign.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CodingSessionInfo {

    @JsonProperty("displayed_what")
    private String displayedWhat;

    @JsonProperty("displayed_where")
    private String displayedWhere;

    @JsonProperty("opening_hours")
    private OpeningHours openingHours;
}
