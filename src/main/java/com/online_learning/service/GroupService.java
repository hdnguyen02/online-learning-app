package com.online_learning.service;

import com.online_learning.dao.*;
import com.online_learning.entity.*;
import com.online_learning.util.Helper;
import com.online_learning.dto.group.GroupResponse;
import com.online_learning.dto.group.GroupRequest;
import com.online_learning.dto.group.UserGroupRequest;
import com.online_learning.sendmail.EmailDetail;
import com.online_learning.sendmail.EmailServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupService {

    private final GroupDao groupRepository;
    private final UserGroupDao userGroupRepository;
    private final UserRepository userRepository;
    private final CommonDeckDao commonDeckDao;
    private final CardDao cardDao;
    private final DeckDao deckDao;
    private final Helper helper;
    private final EmailServiceImpl emailServiceImpl;

    @Transactional
    public void cloneDeck(long id) {
        User user = helper.getUser();
        CommonDeck commonDeck = commonDeckDao.findById(id).orElseThrow();
        List<CommonCard> commonCards = commonDeck.getCards();

        Deck deckClone = new Deck();
        deckClone.setName(commonDeck.getName() + " - Clone");
        deckClone.setDescription(commonDeck.getDescription());
        deckClone.setConfigLanguage(commonDeck.getConfigLanguage());
        deckClone.setIsPublic(false);
        deckClone.setQuantityClones(0);
        deckClone.setUser(user);

        Deck deckCloneSaved = deckDao.save(deckClone);
        List<Card> commonCardsClone = new ArrayList<>();
        commonCards.forEach(commonCard -> {
            commonCardsClone.add(
                    Card.builder()
                            .term(commonCard.getTerm())
                            .definition(commonCard.getDefinition())
                            .audio(commonCard.getAudio())
                            .image(commonCard.getImage())
                            .example(commonCard.getExample())
                            .deck(deckCloneSaved)
                            .build());
        });
        cardDao.saveAll(commonCardsClone);
    }

    public List<GroupResponse> getGlobalGroups() {
        List<Group> groups = groupRepository.getGlobal();
        return groups.stream().map(GroupResponse::mapToGroupDto).toList();
    }

    public void outGroup(long id) throws Exception {
        Group group = groupRepository.findById(id).orElseThrow();
        User user = helper.getUser();

        List<UserGroup> userGroups = userGroupRepository.findByUserAndGroup(user, group);
        if (userGroups.isEmpty())
            throw new Exception("User not in group with id: " + id);
        userGroupRepository.delete(userGroups.get(0));
    }

    public GroupResponse joinGroup(Long idGroup) throws Exception {

        Group group = groupRepository.findById(idGroup).orElseThrow();
        String email = helper.getEmailUser();
        if (email.equals(group.getOwner().getEmail())) {
            throw new Exception("You've joined the class!");
        }

        boolean notAttended = group.getUserGroups().stream()
                .noneMatch(userGroup -> userGroup.getUser().getEmail().equals(email));
        if (!notAttended)
            throw new Exception("You've joined the class!");
        UserGroup userGroup = UserGroup.builder()
                .group(group)
                .user(helper.getUser())
                .approachType("SUBMIT")
                .isActive(true) // tham gia trực tiếp vào lớp học
                .tokenActive(null)
                .build();

        group.getUserGroups().add(userGroupRepository.save(userGroup));
        return GroupResponse.mapToGroupDto(group);

    }

    public List<GroupResponse> searchGlobalGroups(String searchTerm) {
        List<Group> groups = groupRepository.searchGlobal(searchTerm);
        return groups.stream().map(GroupResponse::mapToGroupDto).toList();
    }

    public Boolean createGroup(GroupRequest groupRequest) {
        Group group = new Group();

        String emailOwner = helper.getEmailUser();
        User owner = helper.getUser();

        group.setName(groupRequest.getName());
        group.setDescription(groupRequest.getDescription());
        group.setOwner(owner);

        group.setCreatedBy(emailOwner);
        group.setIsPublic(groupRequest.getIsPublic());

        groupRepository.save(group);

        return true;
    }

    public boolean updateGroup(Long id, GroupRequest groupRequest) {

        User user = helper.getUser();
        Group group = groupRepository.findById(id).orElseThrow();

        group.setName(groupRequest.getName());
        group.setDescription(groupRequest.getDescription());
        group.setIsPublic(groupRequest.getIsPublic());
        group.setOwner(user);

        groupRepository.save(group);

        return true;
    }

    public GroupResponse getGroupById(Long id) {
        Group group = groupRepository.findById(id).orElseThrow();
        return GroupResponse.mapToGroupDtoDetail(group);
    }

    public List<GroupResponse> getGroupsByOwner() {
        User owner = helper.getUser(); // thông tin của người dùng
        List<Group> groups = groupRepository.findByOwner(owner);
        List<GroupResponse> groupResponses = new ArrayList<>();

        groups.forEach(group -> {
            groupResponses.add(GroupResponse.mapToGroupDto(group));
        });

        return groupResponses;
    }

    public boolean deleteGroupById(Long id) {
        Group group = groupRepository.findById(id).orElseThrow();
        groupRepository.delete(group);
        return true;
    }

    public boolean inviteUserGroup(Long id, String email) throws Exception {

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty())
            throw new UsernameNotFoundException("User whose email does not exist!");

        Optional<Group> groupOptional = groupRepository.findById(id);
        if (groupOptional.isEmpty())
            throw new Exception("Group does not exist!");
        User user = userOptional.get();
        Group group = groupOptional.get();

        List<UserGroup> userGroups = userGroupRepository.findByUserAndGroup(user, group);
        String token = UUID.randomUUID().toString();
        if (!userGroups.isEmpty()) {
            UserGroup userGroupDB = userGroups.get(0);
            if (!userGroupDB.isActive()) {
                userGroupDB.setTokenActive(token);
                Thread threadSaveData = new Thread(() -> {
                    userGroupRepository.save(userGroupDB);
                });

                Thread threadSendMail = new Thread(() -> {
                    sendMailAddGroup(id, email, token);
                });

                threadSendMail.start();
                threadSaveData.start();
            }

        } else {
            UserGroup userGroup = new UserGroup();
            userGroup.setTokenActive(token);

            userGroup.setUser(user);
            userGroup.setGroup(group);
            userGroup.setActive(false);

            Thread threadSendMail = new Thread(() -> {
                sendMailAddGroup(id, email, token);
            });

            Thread threadSaveData = new Thread(() -> {
                userGroupRepository.save(userGroup);
            });

            threadSendMail.start();
            threadSaveData.start();
        }

        return true;
    }

    private void sendMailAddGroup(Long groupId, String emailTo, String token) {
        EmailDetail details = new EmailDetail();
        details.setSubject("Invitation letter to join the class");
        details.setRecipient(emailTo);

        String appHost = System.getenv("APP_HOST");
        if (appHost == null || appHost.isEmpty()) {
            appHost = "localhost:8080";
        }

        String link = "http://" + appHost + "/groups/" + groupId + "/active?token=" + token;
        details.setMsgBody("<p>Hi,</p><p>Please click <a href=\'" + link
                + "\'>this link</a> to join class.</p><p>Best regards,</p>");
        emailServiceImpl.sendMailWithAttachment(details);
    }

    public boolean activeUserGroup(Long id, String token) {
        // Lấy Group từ database
        Optional<Group> groupOptional = groupRepository.findById(id);
        if (groupOptional.isEmpty()) {
            throw new EntityNotFoundException("Group does not exist!");
        }
        Group group = groupOptional.get();

        // Tìm UserGroup với Group đã được quản lý
        Optional<UserGroup> userGroupOptional = userGroupRepository.findByGroupAndTokenActive(group, token);
        if (userGroupOptional.isEmpty()) {
            return false;
        }

        // Kích hoạt UserGroup
        UserGroup userGroup = userGroupOptional.get();
        userGroup.setActive(true);
        userGroupRepository.save(userGroup);
        return true;
    }

    public boolean deleteUserGroupById(UserGroupRequest userGroupRequest) {
        Optional<User> userOptional = userRepository.findByEmail(userGroupRequest.getEmail());
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User whose email does not exist!");
        }
        List<UserGroup> userGroups = userGroupRepository.findByUserAndGroup(new User(userGroupRequest.getEmail()),
                new Group(userGroupRequest.getIdGroup()));
        if (userGroups.isEmpty()) {
            throw new UsernameNotFoundException("User whose email is not in the group!");
        }

        userGroupRepository.delete(userGroups.get(0));
        return true;
    }

    public List<GroupResponse> getGroupsByAttendance() {

        User user = helper.getUser();

        List<GroupResponse> groupResponses = new ArrayList<>();

        List<UserGroup> userGroups = userGroupRepository.findByUserAndIsActive(user, true);
        userGroups.forEach(ele -> {
            groupResponses.add(GroupResponse.mapToGroupDto(ele.getGroup()));
        });

        return groupResponses;
    }

    public void initGroup() {
        if (!groupRepository.findAll().isEmpty())
            return;

        Group group = new Group();
        group.setName("Tieng anh cap toc");
        group.setDescription("Lop học tieng anh cap toc trong vong 2 thang");
        group.setOwner(new User("hdnguyen7702@gmail.com"));
        group.setCreatedBy("hdnguyen7702@gmail.com");
        group.setIsPublic(true);

        Group groupStored = groupRepository.save(group);

        // thêm vào 2 thành viên vào lớp học
        UserGroup userGroup01 = UserGroup.builder()
                .group(groupStored)
                .user(new User("n20dccn047@student.ptithcm.edu.vn"))
                .approachType("SUBMIT")
                .isActive(true)
                .tokenActive(null)
                .build();

        UserGroup userGroup02 = UserGroup.builder()
                .group(groupStored)
                .user(new User("hoducnguyen@gmail.com"))
                .approachType("SUBMIT")
                .isActive(true)
                .tokenActive(null)
                .build();

        userGroupRepository.save(userGroup01);
        userGroupRepository.save(userGroup02);

    }

}