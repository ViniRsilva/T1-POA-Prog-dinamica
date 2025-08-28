package com.ages.volunteersmile.adapter.controller.volunteer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class VolunteerController {

    @PostMapping("/volunteers")
    public ResponseEntity<Void> createVolunteer(@RequestBody VolunteerDTO volunteerDTO) {
        // Logic to create a volunteer
        return ResponseEntity.ok().build();

    }
}
