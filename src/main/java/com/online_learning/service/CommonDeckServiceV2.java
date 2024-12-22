package com.online_learning.service;

import com.online_learning.dao.CommonCardDao;
import com.online_learning.dao.CommonDeckDao;
import com.online_learning.dao.LanguageDao;
import com.online_learning.dto.common_deckv2.UpdateCommonCard;
import com.online_learning.dto.common_deckv2.UpdateCommonDeck;
import com.online_learning.entity.CommonCard;
import com.online_learning.entity.CommonDeck;
import com.online_learning.entity.Language;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonDeckServiceV2 {

        private final CommonDeckDao commonDeckDao;
        private final CommonCardDao commonCardDao;
        private final LanguageDao languageDao;

        @Transactional
        public boolean updateCommonDeckV2(UpdateCommonDeck updateCommonDeck) throws Exception {

                CommonDeck commonDeck = commonDeckDao.findById(updateCommonDeck.getId()).orElse(null);
                if (commonDeck == null) {
                        throw new Exception("Not found common deck with id: " + updateCommonDeck.getId());
                }

                Language language = languageDao.findByCode(updateCommonDeck.getConfigLanguage()).orElse(null);
                if (language == null) {
                        throw new Exception("Not found language");
                }

                commonDeck.setName(updateCommonDeck.getName());
                commonDeck.setDescription(updateCommonDeck.getDescription());

                commonDeck.setLanguage(language);

                commonDeckDao.save(commonDeck);

                List<Long> incomingCardIds = updateCommonDeck.getCards().stream()
                                .map(UpdateCommonCard::getId)
                                .filter(Objects::nonNull) // Loại bỏ các ID null (thẻ mới)
                                .collect(Collectors.toList());

                // Lấy danh sách các ID hiện tại trong Deck
                List<Long> currentCardIds = commonDeck.getCards().stream()
                                .map(CommonCard::getId)
                                .collect(Collectors.toList());

                // Tìm các ID không nằm trong danh sách được gửi xuống (các thẻ bị xóa)
                List<Long> cardIdsToDelete = currentCardIds.stream()
                                .filter(id -> !incomingCardIds.contains(id))
                                .collect(Collectors.toList());

                commonCardDao.deleteAllById(cardIdsToDelete);

                List<UpdateCommonCard> cardsToUpdate = updateCommonDeck.getCards().stream()
                                .filter(card -> card.getId() != null) // Các thẻ cần cập nhật
                                .collect(Collectors.toList());

                List<UpdateCommonCard> cardsToAdd = updateCommonDeck.getCards().stream()
                                .filter(card -> card.getId() == null) // Các thẻ cần thêm mới
                                .collect(Collectors.toList());

                for (UpdateCommonCard cardRequest : cardsToUpdate) {
                        CommonCard existingCard = commonCardDao.findById(cardRequest.getId())
                                        .orElseThrow(() -> new RuntimeException("Card not found"));
                        existingCard.setTerm(cardRequest.getTerm());
                        existingCard.setDefinition(cardRequest.getDefinition());
                        existingCard.setExample(cardRequest.getExample());
                        existingCard.setImage(cardRequest.getImage());
                        existingCard.setAudio(cardRequest.getAudio());
                        commonCardDao.save(existingCard);
                }

                for (UpdateCommonCard cardRequest : cardsToAdd) {
                        CommonCard newCard = new CommonCard();
                        newCard.setTerm(cardRequest.getTerm());
                        newCard.setDefinition(cardRequest.getDefinition());
                        newCard.setExample(cardRequest.getExample());
                        newCard.setImage(cardRequest.getImage());
                        newCard.setAudio(cardRequest.getAudio());
                        newCard.setCommonDeck(commonDeck);
                        commonCardDao.save(newCard);
                }
                return true;
        }
}
