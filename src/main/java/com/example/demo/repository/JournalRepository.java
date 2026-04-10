package com.example.demo.repository;

import com.example.demo.entity.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JournalRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findByUserId(Long userID);
    Optional<JournalEntry> findByIdAndUserId(Long journalId, Long userId);

    @Query("SELECT j FROM JournalEntry j WHERE j.user.id = :userId AND j.title LIKE %:keyword%")
    List<JournalEntry> searchByTitle(@Param("userId") Long userId,
                                     @Param("keyword") String keyword);
}

/*
JpaRepository built-in methods:
    - save()
    - findAll()
    - findById()
    - delete(...)
 */