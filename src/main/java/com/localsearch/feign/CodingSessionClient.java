package com.localsearch.feign;

import com.localsearch.feign.model.CodingSessionInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "CodingSessionClient", url = "${coding-session.url}")
public interface CodingSessionClient {

    @GetMapping(value = "/coding-session-rest-api/{place_id}")
    CodingSessionInfo retrieveCodingSessionInfo(@PathVariable("place_id") String id);
}
