package com.ages.volunteersmile.adapter.controller.feedback;

import com.ages.volunteersmile.application.service.VisitService;
import com.ages.volunteersmile.application.dto.UserVisitFeedbackDTO;
import com.ages.volunteersmile.application.dto.FeedbackDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeebackController {
    private final VisitService visitService;

    public FeebackController(VisitService visitService) {
        this.visitService = visitService;
    }

    @PatchMapping()
    public ResponseEntity<FeedbackDTO> addVolunteerFeedback(@Valid @RequestBody UserVisitFeedbackDTO dto) {
        FeedbackDTO response = visitService.addVolunteerFeedback(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/last")
    public ResponseEntity<FeedbackDTO> getLastVolunteerFeedback(@RequestParam UUID roomId) {
        FeedbackDTO feedback = visitService.getLastVolunteerFeedback(roomId);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacksByRoom(@PathVariable UUID roomId) {
        List<FeedbackDTO> feedbacks = visitService.getAllFeedbacksByRoom(roomId);
        return ResponseEntity.ok(feedbacks);
    }
}
