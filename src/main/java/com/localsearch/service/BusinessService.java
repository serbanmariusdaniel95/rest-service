package com.localsearch.service;

import com.localsearch.model.BusinessDetails;

public interface BusinessService {

    /**
     * This method aims to retrieve and process the business details from coding session client based on a given id
     *
     * @param id non-null value.
     * @return an object of {@link BusinessDetails} with the processed details retrieved from coding session client
     * @throws com.localsearch.exception.BusinessException if something wrong occurs during the feign request.
     */
    BusinessDetails getBusinessDetails(String id);
}
