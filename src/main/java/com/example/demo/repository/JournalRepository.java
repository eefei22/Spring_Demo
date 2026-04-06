package com.example.demo.repository;

import com.example.demo.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JournalRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findByUserId(Long userID);
    Optional<JournalEntry> findByIdAndUserId(Long journalId, Long userId);

}

/*
JpaRepository built-in methods:
    - save()
    - findAll()
    - findById()
    - delete(...)
 */