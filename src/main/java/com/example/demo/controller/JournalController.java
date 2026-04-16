package com.example.demo.controller;

import com.example.demo.dto.JournalRequest;
import com.example.demo.dto.JournalResponse;
import com.example.demo.service.JournalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/journals")
public class JournalController {

    private final JournalService journalService;

    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    @PostMapping
    public ResponseEntity<JournalResponse> createJournal(@PathVariable Long userId,
                                                        @RequestBody JournalRequest request) {
        JournalResponse created = journalService.createEntry(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<JournalResponse>> getAllJournals(@PathVariable Long userId) {
        return ResponseEntity.ok(journalService.getAllEntriesByUser(userId));
    }

    @GetMapping("/{journalId}")
    public ResponseEntity<JournalResponse> getJournalById(@PathVariable Long userId,
                                                         @PathVariable Long journalId) {
        return ResponseEntity.ok(journalService.getEntryById(userId, journalId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<JournalResponse>> searchJournals(@PathVariable Long userId,
                                                                @RequestParam String keyword) {
        return ResponseEntity.ok(journalService.searchEntries(userId, keyword));
    }

    @PutMapping("/{journalId}")
    public ResponseEntity<JournalResponse> updateJournal(@PathVariable Long userId,
                                                        @PathVariable Long journalId,
                                                        @RequestBody JournalRequest request) {
        return ResponseEntity.ok(journalService.updateEntry(userId, journalId, request));
    }

    @DeleteMapping("/{journalId}")
    public ResponseEntity<Void> deleteJournal(@PathVariable Long userId,
                                             @PathVariable Long journalId) {
        journalService.deleteEntry(userId, journalId);
        return ResponseEntity.noContent().build();
    }
}
