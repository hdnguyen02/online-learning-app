package com.online_learning.service;

import com.online_learning.dao.CardDao;
import com.online_learning.dao.DeckDao;
import com.online_learning.dao.LanguageDao;
import com.online_learning.dao.UserRepository;
import com.online_learning.dto.statistic.StatisticDeckAndCard;
import com.online_learning.dto.statistic.StatisticLanguage;
import com.online_learning.dto.statistic.StatisticUserAndDeck;
import com.online_learning.entity.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final DeckDao deckRepository;
    private final CardDao cardDao;
    private final UserRepository userRepository;
    private final LanguageDao languageDao;

    public List<StatisticUserAndDeck> getStatisticsDecksAndUsers(LocalDate start, LocalDate end) {
        List<StatisticUserAndDeck> stats = new ArrayList<>();

        // Xác định thời gian bắt đầu và kết thúc
        LocalDateTime startDate = start.withDayOfMonth(1).atStartOfDay(); // Ngày đầu tháng của start
        LocalDateTime endDate = end.withDayOfMonth(end.lengthOfMonth()).atTime(23, 59, 59); // Ngày cuối tháng của end

        // Lặp qua từng tháng trong khoảng thời gian
        while (!startDate.isAfter(endDate)) {
            // Ngày đầu và cuối của tháng hiện tại
            LocalDateTime startOfMonth = startDate.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
                    .withNano(0);
            LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);

            // Lấy số lượng người dùng tạo trong tháng
            long numberUsers = userRepository.countByCreatedDateBetween(startOfMonth, endOfMonth);

            // Lấy số lượng Deck được tạo trong tháng
            long numberDecks = deckRepository.countByCreatedDateBetween(startOfMonth, endOfMonth);

            // Thêm dữ liệu thống kê vào danh sách
            stats.add(new StatisticUserAndDeck(startOfMonth.toString(), (int) numberUsers, (int) numberDecks));

            // Chuyển sang tháng tiếp theo
            startDate = startDate.plusMonths(1);
        }

        return stats;
    }

    // Thống kê theo số ngôn ngữ được sử dụng => biểu đồ tròn.
    public List<StatisticLanguage> getDeckCountByLanguage() {
        List<Language> languages = languageDao.findAll();
        // Map<String, Integer> statistics = new HashMap<>();
        List<StatisticLanguage> statisticLanguages = new ArrayList<>();

        for (Language language : languages) {
            int count = deckRepository.countByConfigLanguage(language.getCode());
            statisticLanguages
                    .add(new StatisticLanguage(language.getNameInternational(), language.getNameVietnamese(), count));
        }
        return statisticLanguages;
    }

    public List<StatisticDeckAndCard> getStatisticsDecksAndCards(LocalDate start, LocalDate end) {
        List<StatisticDeckAndCard> stats = new ArrayList<>();

        // Xác định thời gian bắt đầu và kết thúc
        LocalDateTime startDate = start.withDayOfMonth(1).atStartOfDay(); // Ngày đầu tháng của start
        LocalDateTime endDate = end.withDayOfMonth(end.lengthOfMonth()).atTime(23, 59, 59); // Ngày cuối tháng của end

        // Lặp qua từng tháng trong khoảng thời gian
        while (!startDate.isAfter(endDate)) {
            // Ngày đầu và cuối của tháng hiện tại
            LocalDateTime startOfMonth = startDate.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)
                    .withNano(0);
            LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);

            // Lấy số lượng Deck được tạo trong tháng
            long numberDecks = deckRepository.countByCreatedDateBetween(startOfMonth, endOfMonth);

            // Lấy số lượng Cards được tạo trong tháng
            long numberCards = cardDao.countByCreatedDateBetween(startOfMonth, endOfMonth);

            // Thêm dữ liệu thống kê vào danh sách
            stats.add(new StatisticDeckAndCard(startOfMonth.toString(), (int) numberDecks, (int) numberCards));
            startDate = startDate.plusMonths(1);
        }

        return stats;
    }
}
