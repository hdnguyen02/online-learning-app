package com.online_learning.dao;

import com.online_learning.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardDao extends JpaRepository<Card, Long> {
    Optional<Card> findFirstByIdAndDeckUserEmail(Long id, String userEmail);
    List<Card> findByDeckUserEmail(String email);
    @Query("SELECT c FROM Card c WHERE c.deck.user.email = ?1 AND CONCAT(c.term, ' ', c.definition, ' ', c.example) LIKE %?2%")
    List<Card> search(String email, String searchTerm);
    long countByCreatedDateBetween(LocalDateTime start, LocalDateTime end);

}
