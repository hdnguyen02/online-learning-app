package com.online_learning.service;

import com.online_learning.util.Helper;
import com.online_learning.dao.CardDao;
import com.online_learning.dto.card.CardResponse;
import com.online_learning.entity.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardDao cardDao;
    private final Helper helper;

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

    public void deleteCards(long[] ids) {
        for (long id : ids) {
            Card card = cardDao.findById(id).orElseThrow();
            cardDao.delete(card);
        }
    }
}
