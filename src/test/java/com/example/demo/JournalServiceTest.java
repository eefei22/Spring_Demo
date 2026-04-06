//package com.example.demo;
//
//import com.example.demo.entity.JournalEntry;
//import com.example.demo.repository.JournalRepository;
//import com.example.demo.service.JournalService;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class JournalServiceTest {
//
//    private final JournalRepository journalRepository = mock(JournalRepository.class);
//    private final JournalService journalService = new JournalService(journalRepository);
//
//    @Test
//    void shouldCreateEntry() {
//        JournalEntry entry = new JournalEntry();
//        entry.setTitle("Test");
//        entry.setContent("Content");
//
//        when(journalRepository.save(any())).thenReturn(entry);
//
//        JournalEntry result = journalService.createEntry("Test", "Content");
//        assertEquals("Test", result.getTitle());
//        verify(journalRepository, times(1)).save(any());
//    }
//
//    @Test
//    void shouldReturnAllEntries() {
//        when(journalRepository.findAll()).thenReturn(List.of(new JournalEntry()));
//
//        List<JournalEntry> result = journalService.getAllEntries();
//        assertEquals(1, result.size());
//    }
//}