package com.hdnguyen.service;

import com.hdnguyen.dao.CardDao;
import com.hdnguyen.entity.Card;
import com.hdnguyen.util.Helper;
import com.hdnguyen.dao.DeckDao;
import com.hdnguyen.dto.deck.DetailDeckResponse;
import com.hdnguyen.dto.deck.DeckResponse;
import com.hdnguyen.entity.Deck;
import com.hdnguyen.entity.User;
import com.hdnguyen.dto.deck.DeckRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeckService {

    private final DeckDao deckDao;
    private final Helper helper;
    private final CardDao cardDao;


    // lấy ra toàn bộ bộ thẻ cho người dùng + hàm search

    public List<DeckResponse> getGlobalDecks() {
        return deckDao.getGlobal().stream().map(DeckResponse::new).toList();
    }

    // tiếp theo là tìm kiếm bộ thẻ.
    public List<DeckResponse> searchGlobalDecks(String searchTerm) {
        return deckDao.searchGlobal(searchTerm).stream()
                .map(DeckResponse::new).toList();
    }

    @Transactional
    public boolean cloneDeck(Long idDeck){
        try {

            User user = helper.getUser();
            Deck deck = deckDao.findById(idDeck).orElseThrow();

            Deck deckClone = Deck.builder()
                    .name(deck.getName())
                    .description(deck.getDescription())
                    .user(user)
                    .isPublic(false)
                    .createAt(new Date())
                    .quantityClone(0) // bộ thẻ này chưa được ai clone
                    .build();

            Deck deckCloneSaved = deckDao.save(deckClone);

            List<Card> cardsClone = deck.getCards().stream().map(card -> Card.builder()
                            .deck(deckCloneSaved)
                            .audio(card.getAudio())
                            .image(card.getImage())
                            .term(card.getTerm())
                            .definition(card.getDefinition())
                            .example(card.getExample())
                            .createAt(new Date())
                            .build()).toList();
            cardDao.saveAll(cardsClone); // lưu lại.
            deck.setQuantityClone(deckCloneSaved.getQuantityClone() + 1);
            deckDao.save(deck);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }


    public DeckResponse createDeck(DeckRequest deckRequest)  {
        User user = helper.getUser();
        Deck deck = Deck.builder()
                .name(deckRequest.getName())
                .description(deckRequest.getDescription())
                .isPublic(deckRequest.getIsPublic()) // cho phép clone
                .createAt(new Date())
                .user(user)
                .quantityClone(0)
                .build();
        return new DeckResponse(deckDao.save(deck));
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

    public DeckResponse deleteDeck(Long id)  {
        String emailUser = helper.getEmailUser();
        Deck deck = deckDao.findFirstByIdAndUserEmail(id, emailUser).orElseThrow();
        deckDao.delete(deck);
        return new DeckResponse(deck);
    }

    public DeckResponse updateDeck(Long id, DeckRequest deckRequest)  {
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
        String emailUser = helper.getEmailUser();
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


    // khởi tạo bộ thẻ cho tài khoản n20dccn047@student.ptithcm.edu.vn
    public void initDeck() {

        // nếu không có bộ thẻ nào => khởi tạo
        if (!deckDao.findAll().isEmpty())  return;



        Deck deckB1 = Deck.builder()
                .name("Từ vựng B1")
                .description("Bộ thẻ về tự vựng B1")
                .isPublic(true) // cho phép clone
                .createAt(new Date())
                .user(new User("n20dccn047@student.ptithcm.edu.vn"))
                .quantityClone(0)
                .build();
        Deck deckB2 = Deck.builder()
                .name("Từ vựng B2")
                .description("Bộ thẻ về tự vựng B2")
                .isPublic(true) // cho phép clone
                .createAt(new Date())
                .user(new User("n20dccn047@student.ptithcm.edu.vn"))
                .quantityClone(0)
                .build();
        deckDao.save(deckB1);
        deckDao.save(deckB2);

    }

}
