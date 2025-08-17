package com.ages.volunteersmile.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ages.volunteersmile.model.Volunteer;

@RestController
@RequestMapping("/api/volunteers")
public class VolunteerController {

    @GetMapping
    public List<Volunteer> getAllVolunteers() {
        // Por enquanto, retorna uma lista vazia
        return new ArrayList<>();
    }
}
