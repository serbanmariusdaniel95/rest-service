package com.localsearch.service.impl;

import com.localsearch.exception.BusinessException;
import com.localsearch.feign.CodingSessionClient;
import com.localsearch.feign.model.CodingSessionInfo;
import com.localsearch.feign.model.Days;
import com.localsearch.feign.model.OpeningHours;
import com.localsearch.feign.model.ScheduleInfo;
import com.localsearch.model.BusinessDetails;
import com.localsearch.scheme.DaysScheme;
import com.localsearch.service.BusinessService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.localsearch.scheme.DaysScheme.*;
import static java.util.Collections.*;
import static org.apache.commons.lang3.ObjectUtils.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    private final CodingSessionClient codingSessionClient;

    @Override
    public BusinessDetails getBusinessDetails(String id) {
        try {
            CodingSessionInfo codingSessionInfo = codingSessionClient.retrieveCodingSessionInfo(id);
            return BusinessDetails.builder()
                    .name(codingSessionInfo.getDisplayedWhat())
                    .address(codingSessionInfo.getDisplayedWhere())
                    .openingHours(groupOpeningHours(codingSessionInfo.getOpeningHours()))
                    .build();
        } catch (FeignException e) {
            String message = String.format("Unable to retrieve business details from coding session client for id: %s. " +
                    "Reason: %s", id, e);
            log.error(message);
            throw new BusinessException(message);
        }
    }

    private Map<String, List<String>> groupOpeningHours(OpeningHours openingHours) {
        Map<String, List<String>> groupedOpeningHours = new LinkedHashMap<>();
        if (isEmpty(openingHours) || isEmpty(openingHours.getDays())) {
            groupedOpeningHours.put(getWeekClosed(), singletonList("closed"));
            return groupedOpeningHours;
        }

        processDays(groupedOpeningHours, openingHours.getDays());
        return groupOpeningHours(groupedOpeningHours);
    }

    private void processDays(Map<String, List<String>> groupedOpeningHours, Days days) {
        processDay(groupedOpeningHours, MONDAY, days.getMonday());
        processDay(groupedOpeningHours, TUESDAY, days.getTuesday());
        processDay(groupedOpeningHours, WEDNESDAY, days.getWednesday());
        processDay(groupedOpeningHours, THURSDAY, days.getThursday());
        processDay(groupedOpeningHours, FRIDAY, days.getFriday());
        processDay(groupedOpeningHours, SATURDAY, days.getSaturday());
        processDay(groupedOpeningHours, SUNDAY, days.getSunday());
    }

    private void processDay(Map<String, List<String>> groupedOpeningHours, DaysScheme daysScheme, List<ScheduleInfo> scheduleInfos) {
        if (isEmpty(scheduleInfos)) {
            groupedOpeningHours.put(daysScheme.getDay(), singletonList("closed"));
            return;
        }

        List<String> hours = new ArrayList<>();
        scheduleInfos.forEach(info -> hours.add(info.getStart() + " - " + info.getEnd()));
        groupedOpeningHours.put(daysScheme.getDay(), hours);
    }

    private Map<String, List<String>> groupOpeningHours(Map<String, List<String>> groupedOpeningHours) {
        boolean continueToGroup = Boolean.TRUE;
        while (BooleanUtils.isTrue(continueToGroup)) {
            boolean wasBroken = Boolean.FALSE;
            Iterator<Map.Entry<String, List<String>>> iterator = groupedOpeningHours.entrySet().iterator();
            if (iterator.hasNext()) {
                Map.Entry<String, List<String>> currentElement = iterator.next();
                while (iterator.hasNext()) {
                    Map.Entry<String, List<String>> nextElement = iterator.next();
                    if (currentElement.getValue().equals(nextElement.getValue())) {
                        groupedOpeningHours = replaceKeyInMap(groupedOpeningHours, currentElement, nextElement);
                        wasBroken = Boolean.TRUE;
                        break;
                    }
                    currentElement = nextElement;
                }
            }
            if (BooleanUtils.isFalse(wasBroken)) {
                continueToGroup = Boolean.FALSE;
            }
        }

        return groupedOpeningHours;
    }

    private Map<String, List<String>> replaceKeyInMap(Map<String, List<String>> groupedOpeningHours, Map.Entry<String, List<String>> currentElement,
                                                      Map.Entry<String, List<String>> nextElement) {
        Map<String, List<String>> newGroupedOpeningHours = new LinkedHashMap<>();
        groupedOpeningHours.forEach((key, value) -> {
            if (key.equals(currentElement.getKey())) {
                newGroupedOpeningHours.put(currentElement.getKey().contains("-") ?
                        currentElement.getKey().substring(0, currentElement.getKey().indexOf("-")) + "- " + nextElement.getKey() :
                        currentElement.getKey() + " - " + nextElement.getKey(), nextElement.getValue());
            } else {
                if (!key.equals(nextElement.getKey())) {
                    newGroupedOpeningHours.put(key, value);
                }
            }
        });
        return newGroupedOpeningHours;
    }
}
