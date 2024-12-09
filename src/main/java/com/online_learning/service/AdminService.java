package com.online_learning.service;


import com.online_learning.dao.*;
import com.online_learning.dto.auth.UserResponse;
import com.online_learning.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final CardDao cardDao;
    private final DeckDao deckDao;
    private final GroupDao groupDao;
    private final CommonDeckDao commonDeckDao;

    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserResponse::new).toList();
    }

    public void editUser(boolean isEnabled, String emailUser) {
        User userStored = userRepository.findByEmail(emailUser).orElseThrow();
        userStored.setIsEnabled(isEnabled);
        userRepository.save(userStored);
    }

    public Map<String, Integer> getCommonStatistics() {

        Map<String, Integer> commonStatistics = new HashMap<>();
        commonStatistics.put("quantityCards", cardDao.findAll().size());
        commonStatistics.put("quantityDecks", deckDao.findAll().size());
        commonStatistics.put("quantityGroups", groupDao.findAll().size());
        commonStatistics.put("quantityUsers", userRepository.findAll().size());
        commonStatistics.put("quantityCommonDecks", commonDeckDao.findAll().size());

        return commonStatistics;
    }


}
