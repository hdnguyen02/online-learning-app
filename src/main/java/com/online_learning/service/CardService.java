package com.online_learning.service;

import com.online_learning.dao.CommonDeckDao;
import com.online_learning.entity.CommonDeck;
import com.online_learning.util.Helper;
import com.online_learning.dao.CardDao;
import com.online_learning.dao.DeckDao;
import com.online_learning.dto.card.CardResponse;
import com.online_learning.entity.Card;
import com.online_learning.entity.Deck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final CommonDeckDao commonDeckDao;

    // viết hàm thêm vào.
    public boolean createCardWithCommonDeck(Long idCommonDeck, String term, String definition, String example,
                                            MultipartFile image, MultipartFile audio) throws IOException {

        CommonDeck commonDeck = commonDeckDao.findById(idCommonDeck).orElseThrow();

        String imageUrl = image != null ? firebaseStorageService.save("image", image) : null;
        String audioUrl = audio != null ? firebaseStorageService.save("audio", audio) : null;

//        Card card = Card.builder()
//                .term(term)
//                .definition(definition)
//                .example(example)
//                .image(imageUrl)
//                .audio(audioUrl)
//                .commonDeck(commonDeck)
//                .build();
//        try {
//            cardDao.save(card);
//            return true;
//        }
//        catch (Exception e) {
//            return false;
//        }
        return true;
    }


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
                .deck(deck)
                .build();


        return new CardResponse(cardDao.save(card));
    }





//    public CardResponse updateCard(Long id, Long idDeck, String term, String definition,
//                                   String example, MultipartFile image, MultipartFile audio,
//                                   Boolean isFavourite, Boolean isRemembered) throws Exception {
//
//        Card card = cardDao.findById(id).orElseThrow();
//        if (idDeck != null) {
//            Deck deck = deckDao.findById(idDeck).orElseThrow();
//            card.setDeck(deck);
//        }
//        if (image != null ) {
//            card.setImage(firebaseStorageService.save("image", image));
//        }
//        if (audio != null) {
//            card.setAudio(firebaseStorageService.save("audio", audio));
//        }
//        if (term != null) {
//            card.setTerm(term);
//        }
//        if (definition != null) {
//            card.setDefinition(definition);
//        }
//        if (example != null) {
//            card.setExample(example);
//        }
//
//        if (isFavourite != null) {
//            card.setIsFavourite(isFavourite);
//        }
//        return new CardResponse(cardDao.save(card));
//    }

//    public CardResponse deleteCard(Long id)  {
//        String emailUser = helper.getEmailUser();
//        Card card = cardDao.findFirstByIdAndDeckUserEmail(id, emailUser).orElseThrow();
//        cardDao.delete(card);
//        return new CardResponse(card);
//    }

//    public CardResponse getCardWithId(Long id) {
//        String emailUser = helper.getEmailUser();
//        Card card = cardDao.findById(id).orElseThrow();
//        return new CardResponse(card);
//    }

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

    public void updateFavourite(long id, boolean value) {
        Card card = cardDao.findById(id).orElseThrow();
        card.setIsFavourite(value);
        cardDao.save(card);
    }
    // xíu nữa sữa xóa update gì thì vào đây =>

    // xóa không trả về gì cả.
    public void deleteCards(long [] ids) {
        String emailUser = helper.getEmailUser();
        for (long id : ids) {
            Card card = cardDao.findById(id).orElseThrow();
            cardDao.delete(card);
        }
    }
}

// lớp học: group
// bộ thẻ: => thành viên trong lớp học chia sẻ với nhau.

