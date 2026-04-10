package com.example.demo.service;

import com.example.demo.dto.JournalRequest;
import com.example.demo.dto.JournalResponse;
import com.example.demo.entity.JournalEntry;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
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

    public JournalResponse createEntry(Long userId, JournalRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found"));

        JournalEntry entry = new JournalEntry();
        entry.setTitle(request.getTitle());
        entry.setContent(request.getContent());
        entry.setCreatedAt(LocalDateTime.now());
        entry.setUser(user);

        JournalEntry savedEntry = journalRepository.save(entry);
        return mapToResponse(savedEntry);
    }

    public List<JournalResponse> getAllEntriesByUser(Long userId) {
        return journalRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public JournalResponse getEntryById(Long userId, Long journalId) {
        if (userId == null) {
            throw new BadRequestException("User ID must not be null");
        }
        if (journalId == null) {
            throw new BadRequestException("Journal ID must not be null");
        }
        JournalEntry entry = journalRepository.findByIdAndUserId(journalId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Journal not found for this user"));
                //orElseThrow(() -> new RuntimeException("Journal not found for this user"));

        return mapToResponse(entry);
    }

    public List<JournalResponse> searchEntries(Long userId, String keyword) {
        return journalRepository.searchByTitle(userId, keyword)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public JournalResponse updateEntry(Long userId, Long journalId, JournalRequest request) {
        if (userId == null) {
            throw new BadRequestException("User ID must not be null");
        }
        if (journalId == null) {
            throw new BadRequestException("Journal ID must not be null");
        }
        if (request == null) {
            throw new BadRequestException("Request body must not be null");
        }
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new BadRequestException("Title must not be blank!!!!!!!");
        }
        if (request.getContent() == null || request.getContent().isBlank()) {
            throw new BadRequestException("Content must not be blank");
        }
        JournalEntry entry = journalRepository.findByIdAndUserId(journalId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Journal not found for this user"));

        entry.setTitle(request.getTitle());
        entry.setContent(request.getContent());

        JournalEntry updatedEntry = journalRepository.save(entry);
        return mapToResponse(updatedEntry);
    }

    public void deleteEntry(Long userId, Long journalId) {
        if (userId == null) {
            throw new BadRequestException("User ID must not be null");
        }
        if (journalId == null) {
            throw new BadRequestException("Journal ID must not be null");
        }
        JournalEntry entry = journalRepository.findByIdAndUserId(journalId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Journal not found for this user"));

        journalRepository.delete(entry);
    }

    private JournalResponse mapToResponse(JournalEntry entry){
        return new JournalResponse(
                entry.getId(), entry.getTitle(),
                entry.getContent(), entry.getCreatedAt(),
                entry.getUser().getId(),
                entry.getUser().getUsername()
        );
    }
}