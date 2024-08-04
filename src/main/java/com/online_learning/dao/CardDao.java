package com.online_learning.dao;

import com.online_learning.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardDao extends JpaRepository<Card, Long> {
    Optional<Card> findFirstByIdAndDeckUserEmail(Long id, String userEmail);
    List<Card> findByDeckUserEmail(String email);
    @Query("SELECT c FROM Card c WHERE c.deck.user.email = ?1 AND CONCAT(c.term, ' ', c.definition, ' ', c.example) LIKE %?2%")
    List<Card> search(String email, String searchTerm);
    @Query("SELECT c FROM Card c WHERE c.deck.user.email = ?1 AND (?2 IS NULL OR c.deck.id = ?2) AND (?3 IS NULL OR c.isFavourite = ?3) AND (?4 IS NULL OR c.isRemembered = ?4)")
    List<Card> filter(String email, Long idDeck, Boolean isFavourite, Boolean isRemembered);
}
