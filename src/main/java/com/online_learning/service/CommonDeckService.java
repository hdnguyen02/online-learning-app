package com.online_learning.service;

import com.online_learning.dao.CardDao;
import com.online_learning.dao.CommonDeckDao;
import com.online_learning.dao.GroupDao;
import com.online_learning.dto.deck.CommonDeckRequest;
import com.online_learning.dto.deck.CommonDeckResponse;
import com.online_learning.dto.common_deckv2.QueryCommonDeck;
import com.online_learning.entity.CommonDeck;
import com.online_learning.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommonDeckService {

    private final CommonDeckDao commonDeckDao;
    private final CardDao cardDao;
    private final GroupDao groupDao;

    public boolean createCommonDeck(CommonDeckRequest commonDeckRequest) {
        Group group = groupDao.findById(commonDeckRequest.getIdGroup()).orElseThrow();

        CommonDeck commonDeck = CommonDeck.builder()
                .name(commonDeckRequest.getName())
                .description(commonDeckRequest.getDescription())
                .configLanguage(commonDeckRequest.getConfigLanguage())
                .group(group)
                .build();
        try {
            commonDeckDao.save(commonDeck);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public List<CommonDeckResponse> getCommonDecksByIdGroup(long idGroup) {
        return commonDeckDao.getCommonDeckByIdGroup(idGroup).stream()
                .map(CommonDeckResponse::new).toList();
    }

    public QueryCommonDeck getCommonDeck(Long idCommonDeck) {
        return new QueryCommonDeck(commonDeckDao.findById(idCommonDeck).orElseThrow());
    }


    public boolean editCommonDeck(Long idCommonDeck, CommonDeckRequest commonDeckRequest) {

        CommonDeck commonDeck = commonDeckDao.findById(idCommonDeck).orElseThrow();
        commonDeck.setName(commonDeckRequest.getName());
        commonDeck.setDescription(commonDeckRequest.getDescription());
        try {
            commonDeckDao.save(commonDeck);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean deleteCommonDeck(Long id) {
        try {
            commonDeckDao.deleteById(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }


    public void initCommonDeck() {
        if (!commonDeckDao.findAll().isEmpty())  return;

        CommonDeck commonDeck = CommonDeck.builder()
                .name("Tu vung A1")
                .description("Bo the tu vung A1")

                .group(new Group(1L))
                .build();

        CommonDeck commonDeckStored = commonDeckDao.save(commonDeck);

//        Card card01 = Card.builder()
//                .term("Hello")
//                .definition("Xin chao")
//                .example("Hello Marry")
//                .commonDeck(commonDeckStored)
//                .build();
//
//        Card card02 = Card.builder()
//                .term("Hi")
//                .definition("Xin chao")
//                .example("Hi Marry")
//                .commonDeck(commonDeckStored)
//                .build();
//        Card card03 = Card.builder()
//                .term("Jar")
//                .definition("Xin chao")
//                .example("My jar")
//                .commonDeck(commonDeckStored)
//                .build();
//        Card card04 = Card.builder()
//                .term("Table")
//                .definition("Cai ban")
//                .example("My table")
//                .commonDeck(commonDeckStored)
//                .build();
//
//        Card card05 = Card.builder()
//                .term("Computer")
//                .definition("May tinh")
//                .example("My computer")
//                .commonDeck(commonDeckStored)
//                .build();
//
//        Card card06 = Card.builder()
//                .term("Pen")
//                .definition("cay but")
//                .example("My pen")
//                .commonDeck(commonDeckStored)
//                .build();
//
//        Card card07 = Card.builder()
//                .term("Book")
//                .definition("Quyen sach")
//                .example("My book")
//                .commonDeck(commonDeckStored)
//                .build();
//
//        cardDao.save(card01);
//        cardDao.save(card02);
//        cardDao.save(card03);
//        cardDao.save(card04);
//        cardDao.save(card05);
//        cardDao.save(card06);
//        cardDao.save(card07);

    }





}
