package com.hdnguyen.service;

import com.hdnguyen.util.Helper;
import com.hdnguyen.dao.CardDao;
import com.hdnguyen.dao.DeckDao;
import com.hdnguyen.dto.card.CardResponse;
import com.hdnguyen.entity.Card;
import com.hdnguyen.entity.Deck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardDao cardDao;
    private final DeckDao deckDao;
    private final Helper helper;
    private final FirebaseStorageService firebaseStorageService;


    public CardResponse createCard(Long idDeck, String term, String definition, String example,
                                   MultipartFile image, MultipartFile audio) throws Exception {

        String emailUser = helper.getEmailUser();
        Optional<Deck> oDeck = deckDao.findFirstByIdAndUserEmail(idDeck, emailUser);

        if(oDeck.isEmpty()) {
            throw new Exception("Not found");
        }
        Deck deck =  oDeck.get();



        String imageUrl = image != null ? firebaseStorageService.save("image", image) : null;
        String audioUrl = audio != null ? firebaseStorageService.save("audio", audio) : null;

        Card card = Card.builder()
                .term(term)
                .definition(definition)
                .example(example)
                .image(imageUrl)
                .audio(audioUrl)
                .createAt(new Date())
                .isFavourite(false)
                .isRemembered(false)
                .deck(deck)
                .build();


        return new CardResponse(cardDao.save(card));
    }



    public CardResponse updateCard(Long id, Long idDeck, String term, String definition,
                                   String example, MultipartFile image, MultipartFile audio,
                                   Boolean isFavourite, Boolean isRemembered) throws Exception {
        String emailUser = helper.getEmailUser();
        Card card = cardDao.findFirstByIdAndDeckUserEmail(id, emailUser).orElseThrow();
        if (idDeck != null) {
            Deck deck = deckDao.findById(idDeck).orElseThrow();
            card.setDeck(deck);
        }
        if (image != null ) {
            card.setImage(firebaseStorageService.save("image", image));
        }
        if (audio != null) {
            card.setAudio(firebaseStorageService.save("audio", audio));
        }
        if (term != null) {
            card.setTerm(term);
        }
        if (definition != null) {
            card.setDefinition(definition);
        }
        if (example != null) {
            card.setExample(example);
        }
        if (isRemembered != null) {
            card.setIsRemembered(isRemembered);
        }
        if (isFavourite != null) {
            card.setIsFavourite(isFavourite);
        }
        return new CardResponse(cardDao.save(card));
    }

    public CardResponse deleteCard(Long id)  {
        String emailUser = helper.getEmailUser();
        Card card = cardDao.findFirstByIdAndDeckUserEmail(id, emailUser).orElseThrow();
        cardDao.delete(card);
        return new CardResponse(card);
    }

    public CardResponse getCardWithId(Long id) {
        String emailUser = helper.getEmailUser();
        Card card = cardDao.findFirstByIdAndDeckUserEmail(id, emailUser).orElseThrow();
        return new CardResponse(card);
    }

    public List<CardResponse> getCards() {
        String emailUser = helper.getEmailUser();
        List<Card> cards = cardDao.findByDeckUserEmail(emailUser);
        List<CardResponse> cardsDto = new ArrayList<>();
        cards.forEach(card -> cardsDto.add(new CardResponse(card)));
        return cardsDto;
    }

    public List<CardResponse> searchCards(String searchTerm) {
        String emailUser = helper.getEmailUser();
        List<Card> cards = cardDao.search(emailUser, searchTerm);
        List<CardResponse> cardsDto = new ArrayList<>();
        cards.forEach(card -> cardsDto.add(new CardResponse(card)));
        return cardsDto;
    }

    public List<CardResponse> filterCards(Long idDeck, Boolean isFavourite, Boolean isRemembered) {
        String emailUser = helper.getEmailUser();
        List<Card> cards = cardDao.filter(emailUser, idDeck, isFavourite, isRemembered);
        List<CardResponse> cardsDto = new ArrayList<>();
        cards.forEach(card -> cardsDto.add(new CardResponse(card)));
        return cardsDto;
    }

    // xóa không trả về gì cả.
    public void deleteCards(long [] ids) {
        String emailUser = helper.getEmailUser();
        for (long id : ids) {
            Card card = cardDao.findFirstByIdAndDeckUserEmail(id, emailUser).orElseThrow();
            cardDao.delete(card);
        }
    }
}

// lớp học: group
// bộ thẻ: => thành viên trong lớp học chia sẻ với nhau.

