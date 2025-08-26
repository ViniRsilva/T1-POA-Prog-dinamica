package com.ages.volunteersmile.application.controller;

import java.util.ArrayList;
import java.util.List;

import com.ages.volunteersmile.domain.volunteer.model.Volunteer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/volunteers")
public class VolunteerController {

    @GetMapping
    public List<Volunteer> getAllVolunteers() {
        // Por enquanto, retorna uma lista vazia
        return new ArrayList<>();
    }
}
