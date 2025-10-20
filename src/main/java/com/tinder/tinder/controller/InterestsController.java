package com.tinder.tinder.controller;

import com.tinder.tinder.dto.request.InterestCreate;
import com.tinder.tinder.dto.response.ApiResponse;
import com.tinder.tinder.service.InterestService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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


    @PutMapping("/update")
    public ApiResponse<String> updateInterset(@RequestParam Long id, @RequestParam String name) {
        ApiResponse response = new ApiResponse();
        interestService.updateInterest(id, name);
        response.setMessage("Successfully update interest");
        response.setCode(HttpStatus.ACCEPTED.value());
        return response;
    }
}
