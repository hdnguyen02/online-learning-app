package com.online_learning.service;

import com.online_learning.dao.CardDao;
import com.online_learning.entity.Card;
import com.online_learning.util.Helper;
import com.online_learning.dao.DeckDao;
import com.online_learning.dto.deck.DetailDeckResponse;
import com.online_learning.dto.deck.DeckResponse;
import com.online_learning.entity.Deck;
import com.online_learning.entity.User;
import com.online_learning.dto.deck.DeckRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeckService {

    private final DeckDao deckDao;
    private final Helper helper;
    private final CardDao cardDao;

    public List<DeckResponse> getGlobalDecks() {
        return deckDao.getGlobal().stream().map(DeckResponse::new).toList();
    }

    public List<DeckResponse> searchGlobalDecks(String searchTerm) {
        return deckDao.searchGlobal(searchTerm).stream()
                .map(DeckResponse::new).toList();
    }

    @Transactional
    public boolean cloneDeck(Long id) {

        User user = helper.getUser();
        Deck deck = deckDao.findById(id).orElseThrow();

        Deck deckClone = Deck.builder()
                .name(deck.getName() + " - Clone")
                .description(deck.getDescription())
                .configLanguage(deck.getConfigLanguage())
                .user(user)
                .isPublic(false)
                .quantityClones(0)
                .build();

        Deck deckCloneSaved = deckDao.save(deckClone);

        List<Card> cardsClone = deck.getCards().stream().map(card -> Card.builder()
                .deck(deckCloneSaved)
                .audio(card.getAudio())
                .image(card.getImage())
                .term(card.getTerm())
                .definition(card.getDefinition())
                .example(card.getExample())
                .build()).toList();
        cardDao.saveAll(cardsClone); // lưu lại.
        deck.setQuantityClones(deckCloneSaved.getQuantityClones() + 1);
        deckDao.save(deck);
        return true;

    }

    public DeckResponse createDeck(DeckRequest deckRequest) {
        User user = helper.getUser();
        Deck deck = Deck.builder()
                .name(deckRequest.getName())
                .description(deckRequest.getDescription())
                .isPublic(deckRequest.getIsPublic())

                .user(user)
                .quantityClones(0)
                .build();
        return new DeckResponse(deckDao.save(deck));
    }

    public void createDeckV2() {
        // send deck + cards

    }

    public List<DeckResponse> getDesks() {
        String emailUser = helper.getEmailUser();
        List<Deck> decks = deckDao.findByUserEmail(emailUser);
        List<DeckResponse> decksDto = new ArrayList<>();
        decks.forEach(deck -> {
            decksDto.add(new DeckResponse(deck));
        });
        return decksDto;
    }

    public DeckResponse deleteDeck(Long id) {
        String emailUser = helper.getEmailUser();
        Deck deck = deckDao.findFirstByIdAndUserEmail(id, emailUser).orElseThrow();
        deckDao.delete(deck);
        return new DeckResponse(deck);
    }

    public DeckResponse updateDeck(Long id, DeckRequest deckRequest) {
        String emailUser = helper.getEmailUser();
        Deck deck = deckDao.findFirstByIdAndUserEmail(id, emailUser).orElseThrow();
        if (deckRequest.getName() != null) {
            deck.setName(deckRequest.getName());
        }
        if (deckRequest.getDescription() != null) {
            deck.setDescription(deckRequest.getDescription());
        }
        if (deckRequest.getIsPublic() != null) {
            deck.setIsPublic(deckRequest.getIsPublic());
        }
        return new DeckResponse(deckDao.save(deck));
    }

    public DetailDeckResponse getDeckWithId(Long id) {
        Deck deck = deckDao.findById(id).orElseThrow();
        return new DetailDeckResponse(deck);
    }

    public List<DeckResponse> searchDecks(String searchTerm) {
        String emailUser = helper.getEmailUser();
        List<Deck> decks = deckDao.search(emailUser, searchTerm);
        List<DeckResponse> decksDto = new ArrayList<>();
        decks.forEach(deck -> decksDto.add(new DeckResponse(deck)));
        return decksDto;
    }

    public void initDeck() {

        if (!deckDao.findAll().isEmpty())
            return;

        Deck deckB1 = Deck.builder()
                .name("Tu Vung B1")
                .description("Bo The Tu Vung B1")
                .isPublic(true)

                .user(new User("n20dccn047@student.ptithcm.edu.vn"))
                .quantityClones(0)
                .build();

        Deck deckB2 = Deck.builder()
                .name("Tu vung B2")
                .description("Bo The Tu Vung B2")
                .isPublic(true)

                .user(new User("n20dccn047@student.ptithcm.edu.vn"))
                .quantityClones(0)
                .build();
        Deck deckB1Stored = deckDao.save(deckB1);

        List<Card> cards = new ArrayList<>();
        Card card01 = Card.builder()
                .term("Hello")
                .definition("Xin chao")
                .example("Hello Marry")
                .deck(deckB1Stored)
                .build();

        Card card02 = Card.builder()
                .term("Hi")
                .definition("Xin chao")
                .example("Hi Marry")
                .deck(deckB1Stored)
                .build();
        Card card03 = Card.builder()
                .term("Jar")
                .definition("Xin chao")
                .example("My jar")
                .deck(deckB1Stored)
                .build();
        Card card04 = Card.builder()
                .term("Table")
                .definition("Cai ban")
                .example("My table")
                .deck(deckB1Stored)
                .build();

        Card card05 = Card.builder()
                .term("Computer")
                .definition("May tinh")
                .example("My computer")
                .deck(deckB1Stored)
                .build();

        Card card06 = Card.builder()
                .term("Pen")
                .definition("cay but")
                .example("My pen")
                .deck(deckB1Stored)
                .build();

        Card card07 = Card.builder()
                .term("Book")
                .definition("Quyen sach")
                .example("My book")
                .deck(deckB1Stored)
                .build();

        cards.add(card01);
        cards.add(card02);
        cards.add(card03);
        cards.add(card04);
        cards.add(card05);
        cards.add(card06);
        cards.add(card07);

        cardDao.saveAll(cards);
        deckDao.save(deckB2);
    }

}
