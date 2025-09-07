package com.ages.volunteersmile.adapter.controller;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ages.volunteersmile.adapter.controller.volunteer.VolunteerController;
import com.ages.volunteersmile.application.dto.CreateVolunteerDTO;
import com.ages.volunteersmile.application.dto.UpdatePasswordDTO;
import com.ages.volunteersmile.application.dto.UpdateVolunteerDTO;
import com.ages.volunteersmile.application.dto.VolunteerDTO;
import com.ages.volunteersmile.application.service.VolunteerApplicationService;

@ExtendWith(MockitoExtension.class)
public class VolunteerControllerTest {

    @Mock
    private VolunteerApplicationService volunteerService;

    @InjectMocks
    private VolunteerController volunteerController;

    @Test
    public void createVolunteer_shouldReturnCreated() {
        CreateVolunteerDTO createDto = new CreateVolunteerDTO();
        createDto.setName("New Volunteer");
        createDto.setEmail("new@example.com");
        createDto.setPassword("password123");

        VolunteerDTO responseDto = new VolunteerDTO();
        responseDto.setId(UUID.randomUUID());
        responseDto.setName(createDto.getName());
        responseDto.setEmail(createDto.getEmail());

        when(volunteerService.createVolunteer(createDto)).thenReturn(responseDto);

        ResponseEntity<VolunteerDTO> response = volunteerController.createVolunteer(createDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        verify(volunteerService, times(1)).createVolunteer(createDto);
    }

    @Test
    public void getVolunteerById_shouldReturnOk() {
        UUID volunteerId = UUID.randomUUID();
        VolunteerDTO responseDto = new VolunteerDTO();
        responseDto.setId(volunteerId);
        responseDto.setName("Existing Volunteer");
        responseDto.setEmail("existing@example.com");

        when(volunteerService.findById(volunteerId)).thenReturn(responseDto);

        ResponseEntity<VolunteerDTO> response = volunteerController.getVolunteerById(volunteerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        verify(volunteerService, times(1)).findById(volunteerId);
    }

    @Test
    public void getVolunteerByEmail_shouldReturnOk() {
        String email = "existing@example.com";
        VolunteerDTO responseDto = new VolunteerDTO();
        responseDto.setId(UUID.randomUUID());
        responseDto.setName("Existing Volunteer");
        responseDto.setEmail(email);

        when(volunteerService.findByEmail(email)).thenReturn(responseDto);

        ResponseEntity<VolunteerDTO> response = volunteerController.getVolunteerByEmail(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        verify(volunteerService, times(1)).findByEmail(email);
    }

    @Test
    public void getAllVolunteers_shouldReturnOk() {
        VolunteerDTO volunteerDto = new VolunteerDTO();
        volunteerDto.setId(UUID.randomUUID());
        volunteerDto.setName("Volunteer 1");
        volunteerDto.setEmail("volunteer1@example.com");
        List<VolunteerDTO> volunteerList = List.of(volunteerDto);

        when(volunteerService.findAll()).thenReturn(volunteerList);

        ResponseEntity<List<VolunteerDTO>> response = volunteerController.getAllVolunteers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(volunteerList, response.getBody());
        verify(volunteerService, times(1)).findAll();
    }

    @Test
    public void updateVolunteer_shouldReturnOk() {
        UUID volunteerId = UUID.randomUUID();
        UpdateVolunteerDTO updateDto = new UpdateVolunteerDTO();
        updateDto.setName("Updated Name");

        VolunteerDTO responseDto = new VolunteerDTO();
        responseDto.setId(volunteerId);
        responseDto.setName(updateDto.getName());
        responseDto.setEmail("original@example.com");

        when(volunteerService.updateVolunteer(volunteerId, updateDto)).thenReturn(responseDto);

        ResponseEntity<VolunteerDTO> response = volunteerController.updateVolunteer(volunteerId, updateDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
        verify(volunteerService, times(1)).updateVolunteer(volunteerId, updateDto);
    }

    @Test
    public void updatePassword_shouldReturnNoContent() {
        UUID volunteerId = UUID.randomUUID();
        UpdatePasswordDTO passwordDto = new UpdatePasswordDTO();
        passwordDto.setOldPassword("oldPass");
        passwordDto.setNewPassword("newPass");

        doNothing().when(volunteerService).updatePassword(volunteerId, passwordDto);

        ResponseEntity<Void> response = volunteerController.updatePassword(volunteerId, passwordDto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(volunteerService, times(1)).updatePassword(volunteerId, passwordDto);
    }

    @Test
    public void deleteVolunteer_shouldReturnNoContent() {
        UUID volunteerId = UUID.randomUUID();
        doNothing().when(volunteerService).deleteVolunteerById(volunteerId);

        ResponseEntity<Void> response = volunteerController.deleteVolunteer(volunteerId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(volunteerService, times(1)).deleteVolunteerById(volunteerId);
    }
}