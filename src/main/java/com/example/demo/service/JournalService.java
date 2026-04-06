package com.example.demo.service;

import com.example.demo.dto.JournalRequest;
import com.example.demo.entity.JournalEntry;
import com.example.demo.entity.User;
import com.example.demo.repository.JournalRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalService {

    private final JournalRepository journalRepository;
    private final UserRepository userRepository;

    public JournalService(JournalRepository journalRepository, UserRepository userRepository) {
        this.journalRepository = journalRepository;
        this.userRepository = userRepository;
    }

    public JournalEntry createEntry(Long userId, JournalRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found"));

        JournalEntry entry = new JournalEntry();
        entry.setTitle(request.getTitle());
        entry.setContent(request.getContent());
        entry.setCreatedAt(LocalDateTime.now());
        entry.setUser(user);

        return journalRepository.save(entry);
    }

    public List<JournalEntry> getAllEntriesByUser(Long userId) {
        return journalRepository.findByUserId(userId);
    }

    public JournalEntry getEntryById(Long userId, Long journalId) {
        return journalRepository.findByIdAndUserId(journalId, userId).orElseThrow(() ->
                new RuntimeException("Journal not found for this user"));
    }

    public JournalEntry updateEntry(Long userId, Long journalId, JournalRequest request) {
        JournalEntry entry = journalRepository.findByIdAndUserId(journalId, userId).orElseThrow(() ->
                new RuntimeException("Journal not found for this user"));

        entry.setTitle(request.getTitle());
        entry.setContent(request.getContent());

        return journalRepository.save(entry);
    }

    public void deleteEntry(Long userId, Long journalId) {
        JournalEntry entry = journalRepository.findByIdAndUserId(journalId, userId).orElseThrow(() ->
                new RuntimeException("Journal not found for this user"));

        journalRepository.delete(entry);
    }
}