package com.online_learning.service;


import com.online_learning.dao.DeckDao;
import com.online_learning.dto.deck.DeckResponse;
import com.online_learning.dto.userv2.UpdateAvatarUser;
import com.online_learning.dto.userv2.UpdatePWUser;
import com.online_learning.dto.userv2.UpdateUser;
import com.online_learning.entity.Deck;
import com.online_learning.entity.Role;
import com.online_learning.util.Helper;
import com.online_learning.dao.UserRepository;
import com.online_learning.dto.auth.UserResponse;
import com.online_learning.entity.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DeckDao deckDao;
    private final Helper helper;
    private final FirebaseStorageService firebaseStorageService;
    private final PasswordEncoder passwordEncoder;


    public  boolean updateUser(UpdateUser updateUser) {

        User user = helper.getUser();
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setPhone(updateUser.getPhone());
        user.setDateOfBirth(updateUser.getDateOfBirth());
        user.setGender(updateUser.getGender());

        userRepository.save(user);
        return true;

    }

    public UserResponse getProfileUser() {
        return new UserResponse(helper.getUser());
    }


    // lấy ra thong tin nguoi dung.
    public UserResponse getInfoOtherUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return new UserResponse(user);
    }


    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("not found user!"));
    }

    public boolean updateAvatarUser(UpdateAvatarUser updateAvatarUser) {
        User user = helper.getUser();
        user.setAvatar(updateAvatarUser.getAvatar());
        userRepository.save(user);
        return true;
    }

    public boolean updatePWUser(UpdatePWUser updatePWUser) throws Exception {
        User user = helper.getUser();

        String newPW = updatePWUser.getNewPW();
        String confirmPW = updatePWUser.getConfirmPW();
        String oldPW = updatePWUser.getOldPW();

        if (!passwordEncoder.matches(oldPW, user.getPassword())) throw new Exception("Mật khẩu cũ không chính xác!");
        if (!newPW.equals(confirmPW)) throw new Exception("Mật khẩu mới và xác nhận không khớp!");

        user.setPassword(passwordEncoder.encode(newPW));
        userRepository.save(user);
        return true;
    }

    // update role TEACHER
    public void updateUserWithRoleTeacher(String emailUser) {
        User userStored = userRepository.findByEmail(emailUser).orElseThrow();
        userStored.getRoles().add(new Role("TEACHER"));
        userRepository.save(userStored);
    }

    public List<DeckResponse> getDecks(Long id) {
        List<Deck> decks = deckDao.getDecks(id);
        return decks.stream().map(DeckResponse::new).toList();
    }
}
