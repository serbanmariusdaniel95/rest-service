package com.localsearch.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.localsearch.config.WireMockConfig;
import com.localsearch.util.BusinessTestsUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = WireMockConfig.class)
class BusinessControllerTest {

    private final BusinessTestsUtil businessTestsUtil = new BusinessTestsUtil();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WireMockServer wireMockServer;

    @Test
    void should_get_business_details_with_all_days_closed_in_opening_hours() throws Exception {
        setupGet("1", businessTestsUtil.getInputInfoFrom("allClosed.json"));
        MvcResult mvcResult = mockMvc.perform(get("/business/1")).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        String expectedResult = businessTestsUtil.getExpectedInfoFrom("allClosed.json");
        assertEquals(expectedResult, result);
    }

    @Test
    void should_get_business_details_with_grouped_days_in_opening_hours() throws Exception {
        setupGet("2", businessTestsUtil.getInputInfoFrom("groupedDays.json"));
        MvcResult mvcResult = mockMvc.perform(get("/business/2")).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        String expectedResult = businessTestsUtil.getExpectedInfoFrom("groupedDays.json");
        assertEquals(expectedResult, result);
    }

    @Test
    void should_get_business_details_with_different_hours_for_every_day_in_opening_hours() throws Exception {
        setupGet("3", businessTestsUtil.getInputInfoFrom("differentDays.json"));
        MvcResult mvcResult = mockMvc.perform(get("/business/3")).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        String expectedResult = businessTestsUtil.getExpectedInfoFrom("differentDays.json");
        assertEquals(expectedResult, result);
    }

    private void setupGet(String parameter, String response) {
        wireMockServer.stubFor(WireMock.get(urlEqualTo("/coding-session-rest-api/" + parameter))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(response)));
    }
}