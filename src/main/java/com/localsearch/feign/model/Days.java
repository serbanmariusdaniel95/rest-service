package com.localsearch.feign.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Days {

    @JsonProperty("monday")
    private List<ScheduleInfo> monday;

    @JsonProperty("tuesday")
    private List<ScheduleInfo> tuesday;

    @JsonProperty("wednesday")
    private List<ScheduleInfo> wednesday;

    @JsonProperty("thursday")
    private List<ScheduleInfo> thursday;

    @JsonProperty("friday")
    private List<ScheduleInfo> friday;

    @JsonProperty("saturday")
    private List<ScheduleInfo> saturday;

    @JsonProperty("sunday")
    private List<ScheduleInfo> sunday;
}
