package com.localsearch.controller;

import com.localsearch.model.BusinessDetails;
import com.localsearch.service.BusinessService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/business")
public class BusinessController {

    private final BusinessService businessService;

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Business details have been successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Failure while trying to retrieve business details")
    })
    public BusinessDetails getBusinessDetails(@NotNull @PathVariable("id") final String id) {
        return businessService.getBusinessDetails(id);
    }
}
