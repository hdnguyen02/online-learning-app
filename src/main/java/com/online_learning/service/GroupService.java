package com.online_learning.service;

import com.online_learning.util.Helper;
import com.online_learning.dao.GroupDao;
import com.online_learning.dao.UserDao;
import com.online_learning.dao.UserGroupDao;
import com.online_learning.dto.group.GroupResponse;
import com.online_learning.entity.Group;
import com.online_learning.entity.User;
import com.online_learning.entity.UserGroup;
import com.online_learning.dto.group.GroupRequest;
import com.online_learning.dto.group.UserGroupRequest;
import com.online_learning.sendmail.EmailDetail;
import com.online_learning.sendmail.EmailServiceImpl;
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
    private final UserDao userRepository;
    private final Helper helper;
    private final EmailServiceImpl emailServiceImpl;

    public List<GroupResponse> getGlobalGroups() {
        List<Group> groups = groupRepository.getGlobal();
        return groups.stream().map(GroupResponse::mapToGroupDto).toList();
    }

    public GroupResponse joinGroup(Long idGroup) throws Exception {

        Group group = groupRepository.findById(idGroup).orElseThrow();
        String email = helper.getEmailUser();
        if (email.equals(group.getOwner().getEmail())) {
            throw new Exception("You've joined the class!");
        }

        boolean notAttended = group.getUserGroups().stream().noneMatch(userGroup -> userGroup.getUser().getEmail().equals(email));
        if (!notAttended) throw new Exception("You've joined the class!");
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
        Date created = new Date();
    
        String emailOwner = helper.getEmailUser();
        User owner = helper.getUser();

        group.setName(groupRequest.getName());
        group.setDescription(groupRequest.getDescription());
        group.setOwner(owner);
        group.setCreated(created);
        group.setCreatedBy(emailOwner);
        group.setIsPublic(groupRequest.getIsPublic());

        groupRepository.save(group);

        return true;
    }

    public boolean updateGroup(Long id, GroupRequest groupRequest) {
        String email = helper.getEmailUser();
        Group group = groupRepository.findById(id).orElseThrow();

        group.setName(groupRequest.getName());
        group.setDescription(groupRequest.getDescription());
        group.setIsPublic(groupRequest.getIsPublic());
        group.setOwner(new User(email));

        Date modified = new Date();
        group.setModified(modified);
        group.setModifiedBy(email);
        group.setIsPublic(groupRequest.getIsPublic());

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


    public boolean inviteUserGroup(Long id, String emailUser) {

        Optional<User> userOptional = userRepository.findById(emailUser);
        if (userOptional.isEmpty()) {
                throw new UsernameNotFoundException("User whose email does not exist!");
        }

        List<UserGroup> userGroups = userGroupRepository.findByUserAndGroup(new User(emailUser),
                new Group(id)
        );
        String token = UUID.randomUUID().toString();
        if (!userGroups.isEmpty()) {
            UserGroup userGroupDB = userGroups.get(0);
            if (!userGroupDB.isActive()) {
                userGroupDB.setTokenActive(token);
                Thread threadSaveData = new Thread(()-> {
                    userGroupRepository.save(userGroupDB);
                });

                Thread threadSendMail = new Thread(()->{
                    sendMailAddGroup(id, emailUser, token);
                });

                threadSendMail.start();
                threadSaveData.start();
            }

        } else {
            UserGroup userGroup =  new UserGroup();
            userGroup.setTokenActive(token);

            userGroup.setUser(new User(emailUser));
            userGroup.setGroup(new Group(id));
            userGroup.setActive(false);

            Thread threadSendMail = new Thread(()->{
                sendMailAddGroup(id, emailUser, token);
            });

            Thread threadSaveData = new Thread(()-> {
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
        String link = "http://localhost:8080/groups/"+groupId+"/active?token=" + token;
        details.setMsgBody("<p>Xin chào bạn,</p>" +
                "<p>Please click <a href=\'"+link+"\'>this link</a> to join class.</p>" +
                "    <p>Best regards,</p>");
        emailServiceImpl.sendMailWithAttachment(details);
    }

    public boolean activeUserGroup(Long id, String token) {
        Optional<UserGroup> userGroupOptional = userGroupRepository.findByGroupAndTokenActive(new Group(id), token);

        if (userGroupOptional.isEmpty()) {
            return false;
        }

        UserGroup userGroup = userGroupOptional.get();
        userGroup.setActive(true);
        userGroupRepository.save(userGroup);
        return true;
    }

    public boolean deleteUserGroupById(UserGroupRequest userGroupRequest) {
        Optional<User> userOptional = userRepository.findById(userGroupRequest.getEmail());
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User whose email does not exist!");
        }
        List<UserGroup> userGroups = userGroupRepository.findByUserAndGroup(new User(userGroupRequest.getEmail()),
                new Group(userGroupRequest.getIdGroup())
        );
        if (userGroups.isEmpty()){
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
        if (!groupRepository.findAll().isEmpty())  return;

        Group group = new Group();
        group.setName("Tieng anh cap toc");
        group.setDescription("Lop học tieng anh cap toc trong vong 2 thang");
        group.setOwner(new User("hdnguyen7702@gmail.com"));
        group.setCreatedBy("hdnguyen7702@gmail.com");
        group.setIsPublic(true);

        Group groupStored =  groupRepository.save(group);

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