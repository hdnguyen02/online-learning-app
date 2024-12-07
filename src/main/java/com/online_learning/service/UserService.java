package com.online_learning.service;


import com.online_learning.dao.DeckDao;
import com.online_learning.dto.deck.DeckResponse;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DeckDao deckDao;
    private final Helper helper;
    private final FirebaseStorageService firebaseStorageService;
    private final PasswordEncoder passwordEncoder;

    // update thông tin theo.  name, gender, age, phone, dataOfBirth, avatar
    public UserResponse updateUser(String firstName, String lastName, String gender, String phone, String dataOfBirth, MultipartFile avatar) throws IOException {
        // kiểm tra thông tin nào cập nhập thì cập nhập. thông tin còn lại hông quan tâm
//        User user = helper.getUser();
//        if (firstName!= null) {
//            user.setFirstName(firstName);
//        }
//        if (lastName != null) {
//            user.setLastName(lastName);
//        }
//        if (gender != null) {
//            user.setGender(gender);
//        }
//
//        if (phone != null) {
//            user.setPhone(phone);
//        }
//        if (dataOfBirth != null) {
//            user.setDateOfBirth(dataOfBirth);
//        }
//
//
//        if (avatar != null) {
//            String url =  firebaseStorageService.save("avatar", avatar);
//            user.setAvatar(url);
//        }
//
//        return new UserResponse(userDao.save(user));
        return null;
    }


    public UserResponse getInfoUser() {
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

    public void changePW(String newPW) {
        User user = helper.getUser();
        user.setPassword(passwordEncoder.encode(newPW));
        userRepository.save(user);
    }

    // update role TEACHER
    public void updateUserWithRoleTeacher(String emailUser) {
        User userStored = userRepository.findByEmail(emailUser).orElseThrow();
        userStored.getRoles().add(new Role("TEACHER"));
        userRepository.save(userStored);
    }

    public List<DeckResponse> getDecks(Long id) {
        List<Deck> decks = deckDao.getDecks(id);

        System.out.println("Có chạy vào!");
        System.out.println(decks.size());

        return decks.stream().map(DeckResponse::new).toList();
    }
}
