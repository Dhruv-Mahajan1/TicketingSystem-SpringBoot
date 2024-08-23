package com.example.ticketingsystem.controller;


import com.example.ticketingsystem.dtos.response.CategoryResponseDTO;
import com.example.ticketingsystem.dtos.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"/api/v1/category"})
public class CategoryMasterController {

    @GetMapping
    public Response<List<CategoryResponseDTO>> getAllCategories() {
        // some logic
        List<CategoryResponseDTO> categoryResponseDTO= new ArrayList<>();
        return new Response<>(HttpStatus.OK.toString(),"List of categories ",categoryResponseDTO);
    }
}
