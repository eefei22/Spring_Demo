package com.example.demo;

import com.example.demo.controller.JournalController;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.GlobalExceptionHandler;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.JournalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class JournalControllerExceptionTest {

    private MockMvc mockMvc;

    @Mock
    private JournalService journalService;

    @BeforeEach
    void setUp() {
        JournalController controller = new JournalController(journalService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("GET journal by id - returns 404 when journal is not found")
    void getJournalById_shouldReturn404_whenJournalNotFound() throws Exception {
        given(journalService.getEntryById(1L, 10L))
                .willThrow(new ResourceNotFoundException("Journal not found for this user"));

        mockMvc.perform(get("/users/1/journals/10"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Journal not found for this user"))
                .andExpect(jsonPath("$.path").value("/users/1/journals/10"));
    }

    @Test
    @DisplayName("PUT update journal - returns 400 when title is blank")
    void updateJournal_shouldReturn400_whenTitleIsBlank() throws Exception {
        String requestBody = """
                {
                  "title": "",
                  "content": "Updated content"
                }
                """;

        given(journalService.updateEntry(eq(1L), eq(10L), any()))
                .willThrow(new BadRequestException("Title must not be blank"));

        mockMvc.perform(put("/users/1/journals/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Title must not be blank"))
                .andExpect(jsonPath("$.path").value("/users/1/journals/10"));
    }

    @Test
    @DisplayName("PUT update journal - returns 404 when journal is not found")
    void updateJournal_shouldReturn404_whenJournalNotFound() throws Exception {
        String requestBody = """
                {
                  "title": "Updated title",
                  "content": "Updated content"
                }
                """;

        given(journalService.updateEntry(eq(1L), eq(10L), any()))
                .willThrow(new ResourceNotFoundException("Journal not found for this user"));

        mockMvc.perform(put("/users/1/journals/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Journal not found for this user"))
                .andExpect(jsonPath("$.path").value("/users/1/journals/10"));
    }

    @Test
    @DisplayName("DELETE journal - returns 404 when journal is not found")
    void deleteJournal_shouldReturn404_whenJournalNotFound() throws Exception {
        org.mockito.BDDMockito.willThrow(new ResourceNotFoundException("Journal not found for this user"))
                .given(journalService).deleteEntry(1L, 10L);

        mockMvc.perform(delete("/users/1/journals/10"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Journal not found for this user"))
                .andExpect(jsonPath("$.path").value("/users/1/journals/10"));
    }

    @Test
    @DisplayName("GET journal by id - returns 500 for unexpected exception")
    void getJournalById_shouldReturn500_whenUnexpectedErrorOccurs() throws Exception {
        given(journalService.getEntryById(1L, 11L))
                .willThrow(new RuntimeException("Database crashed"));

        mockMvc.perform(get("/users/1/journals/11"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"))
                .andExpect(jsonPath("$.path").value("/users/1/journals/11"));
    }
}
