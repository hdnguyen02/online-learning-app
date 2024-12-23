package com.online_learning.service;

import com.online_learning.dao.CommonCardDao;
import com.online_learning.dao.CommonDeckDao;
import com.online_learning.dao.LanguageDao;
import com.online_learning.dto.common_deckv2.UpdateCommonCard;
import com.online_learning.dto.common_deckv2.UpdateCommonDeck;
import com.online_learning.dto.deckv2.Answer;
import com.online_learning.dto.deckv2.JoinCardElement;
import com.online_learning.dto.deckv2.Question;
import com.online_learning.entity.Card;
import com.online_learning.entity.CommonCard;
import com.online_learning.entity.CommonDeck;
import com.online_learning.entity.Deck;
import com.online_learning.entity.Language;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonDeckServiceV2 {

        private final CommonDeckDao commonDeckDao;
        private final CommonCardDao commonCardDao;
        private final LanguageDao languageDao;

        public List<Question> generateQuiz(long id, int numberOfQuestions, String optionType) throws Exception {
                int maxQuestion = 15;
                numberOfQuestions = Math.min(numberOfQuestions, maxQuestion);

                CommonDeck commonDeck = commonDeckDao.findById(id).orElse(null);
                if (commonDeck == null) {
                        throw new Exception("Not found common deck with id: " + id);
                }
                List<CommonCard> cards = commonDeck.getCards();

                // Lọc thẻ dựa trên isOnlyFavorite

                // Shuffle cards để random hóa
                Collections.shuffle(cards);

                // Danh sách câu hỏi đầu ra
                List<Question> questions = new ArrayList<>();

                // Lặp qua số lượng câu hỏi yêu cầu
                for (int i = 0; i < numberOfQuestions && i < cards.size(); i++) {
                        CommonCard card = cards.get(i);

                        // Tạo câu hỏi tùy theo optionType
                        String audio = card.getAudio();
                        String image = card.getImage();
                        String questionContent;
                        Answer correctAnswer = new Answer();
                        String contentCorrectAnswer;

                        String type;

                        if ("TERM".equalsIgnoreCase(optionType)) {
                                questionContent = card.getTerm();
                                contentCorrectAnswer = card.getDefinition();
                                type = "Thuật ngữ";
                        } else if ("DEFINITION".equalsIgnoreCase(optionType)) {
                                questionContent = card.getDefinition();
                                contentCorrectAnswer = card.getTerm();
                                type = "Định nghĩa";
                        } else {
                                // Random chọn TERM hoặc DEFINITION khi optionType là "BOTH"
                                if (new Random().nextBoolean()) {
                                        questionContent = card.getTerm();
                                        contentCorrectAnswer = card.getDefinition();
                                        type = "Thuật ngữ";
                                } else {
                                        questionContent = card.getDefinition();
                                        contentCorrectAnswer = card.getTerm();
                                        type = "Định nghĩa";
                                }
                        }

                        correctAnswer.setContentAnswer(contentCorrectAnswer);
                        correctAnswer.setId(UUID.randomUUID().toString());

                        // Tạo danh sách đáp án
                        List<Answer> answers = new ArrayList<>();
                        answers.add(correctAnswer);

                        // Thêm 3 đáp án sai
                        List<CommonCard> otherCards = cards.stream().filter(c -> !c.equals(card))
                                        .collect(Collectors.toList());
                        Collections.shuffle(otherCards);

                        for (int j = 0; j < 3 && j < otherCards.size(); j++) {
                                CommonCard wrongCard = otherCards.get(j);
                                String wrongAnswer = "TERM".equalsIgnoreCase(optionType) || new Random().nextBoolean()
                                                ? wrongCard.getDefinition()
                                                : wrongCard.getTerm();
                                answers.add(new Answer(UUID.randomUUID().toString(), wrongAnswer));
                        }

                        Collections.shuffle(answers);

                        // Tạo câu hỏi
                        Question question = Question.builder()
                                        .id(UUID.randomUUID().toString())
                                        .questionContent(questionContent)
                                        .correctAnswer(correctAnswer)
                                        .answers(answers)
                                        .type(type)
                                        .audio(audio)
                                        .image(image)
                                        .build();
                        questions.add(question);
                }
                return questions;
        }

        public List<JoinCardElement> getJoinCommonCards(long id, int quantity) throws Exception {
                CommonDeck commonDeck = commonDeckDao.findById(id).orElse(null);
                if (commonDeck == null) {
                        throw new Exception("Not found common deck with id: " + id);
                }

                List<JoinCardElement> joinCardElements = new ArrayList<>();

                List<CommonCard> cards = commonDeck.getCards();

                if (quantity > cards.size()) {
                        quantity = cards.size(); // Nếu quantity lớn hơn số thẻ, chỉ lấy tối đa số thẻ hiện có
                }

                // Lấy ngẫu nhiên `quantity` thẻ
                Collections.shuffle(cards);
                List<CommonCard> selectedCards = cards.subList(0, quantity);

                selectedCards.forEach(card -> {
                        String randomUUID = UUID.randomUUID().toString();
                        JoinCardElement joinCardElementTerm = new JoinCardElement();
                        joinCardElementTerm.setContent(card.getTerm());
                        joinCardElementTerm.setKey(randomUUID);
                        joinCardElementTerm.setId(UUID.randomUUID().toString());

                        JoinCardElement joinCardElementDefinition = new JoinCardElement();
                        joinCardElementDefinition.setContent(card.getDefinition());
                        joinCardElementDefinition.setKey(randomUUID);
                        joinCardElementDefinition.setId(UUID.randomUUID().toString());

                        joinCardElements.add(joinCardElementTerm);
                        joinCardElements.add(joinCardElementDefinition);
                });

                Collections.shuffle(joinCardElements);
                return joinCardElements;
        }

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
