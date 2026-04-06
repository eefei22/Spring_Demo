package com.example.demo.controller;

import com.example.demo.dto.JournalRequest;
import com.example.demo.entity.JournalEntry;
import com.example.demo.service.JournalService;
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
    public JournalEntry createJournal(@PathVariable Long userId,
                                      @RequestBody JournalRequest request) {
        return journalService.createEntry(userId, request);
    }

    @GetMapping
    public List<JournalEntry> getAllJournals(@PathVariable Long userId) {
        return journalService.getAllEntriesByUser(userId);
    }

    @GetMapping("/{journalId}")
    public JournalEntry getJournalById(@PathVariable Long userId,
                                       @PathVariable Long journalId) {
        return journalService.getEntryById(userId, journalId);
    }

    @PutMapping("/{journalId}")
    public JournalEntry updateJournal(@PathVariable Long userId,
                                      @PathVariable Long journalId,
                                      @RequestBody JournalRequest request) {
        return journalService.updateEntry(userId, journalId, request);
    }

    @DeleteMapping("/{journalId}")
    public String deleteJournal(@PathVariable Long userId,
                                @PathVariable Long journalId) {
        journalService.deleteEntry(userId, journalId);
        return "Journal deleted successfully";
    }
}