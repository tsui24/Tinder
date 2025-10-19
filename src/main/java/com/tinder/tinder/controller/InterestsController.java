package com.tinder.tinder.controller;

import com.tinder.tinder.dto.request.InterestCreate;
import com.tinder.tinder.dto.response.ApiResponse;
import com.tinder.tinder.service.InterestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/interest")
public class InterestsController {

    private final InterestService interestService;

    public InterestsController(InterestService interestService) {
        this.interestService = interestService;
    }

    @PostMapping
    public ApiResponse<String> insertInterset(@RequestBody InterestCreate request) {
        ApiResponse response = new ApiResponse();
        interestService.addInterest(request);
        response.setMessage("Successfully added interest");
        response.setCode(200);
        return response;
    }
}
