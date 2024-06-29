package com.hdnguyen.service;

import com.hdnguyen.util.Helper;
import com.hdnguyen.dao.GroupDao;
import com.hdnguyen.dao.UserDao;
import com.hdnguyen.dao.UserGroupDao;
import com.hdnguyen.dto.group.GroupDto;
import com.hdnguyen.entity.Group;
import com.hdnguyen.entity.User;
import com.hdnguyen.entity.UserGroup;
import com.hdnguyen.dto.group.GroupRequest;
import com.hdnguyen.dto.group.UserGroupRequest;
import com.hdnguyen.sendmail.EmailDetail;
import com.hdnguyen.sendmail.EmailServiceImpl;
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

    public List<GroupDto> getGlobalGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups.stream().map(GroupDto::mapToGroupDto).toList();
    }

    public GroupDto joinGroup(Long idGroup) throws Exception {
        Group group = groupRepository.findById(idGroup).orElseThrow();
        String email = helper.getEmailUser();
        if (email.equals(group.getOwner().getEmail())) {
            throw new Exception("Bạn đã tham lớp học!");
        }

        boolean notAttended = group.getUserGroups().stream().noneMatch(userGroup -> userGroup.getUser().getEmail().equals(email));
        if (!notAttended) throw new Exception("Bạn đã tham gia lớp học!");
        UserGroup userGroup = UserGroup.builder()
                .group(group)
                .user(helper.getUser())
                .approachType("SUBMIT")
                .isActive(true) // tham gia trực tiếp vào lớp học
                .tokenActive(null)
                .build();

        group.getUserGroups().add(userGroupRepository.save(userGroup));
        return GroupDto.mapToGroupDto(group);

    }


    public List<GroupDto> searchGlobalGroups(String searchTerm) {
        List<Group> groups = groupRepository.searchGlobal(searchTerm);
        return groups.stream().map(GroupDto::mapToGroupDto).toList();
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

        groupRepository.save(group);

        return true;
    }

    public boolean updateGroup(Long id, GroupRequest groupRequest) {
        String email = helper.getEmailUser();
        Group group = groupRepository.findById(id).orElseThrow();

        group.setName(groupRequest.getName());
        group.setDescription(groupRequest.getDescription());
        group.setOwner(new User(email));

        Date modified = new Date();
        group.setModified(modified);
        group.setModifiedBy(email);

        groupRepository.save(group);

        return true;
    }


    public GroupDto getGroupById(Long id) {
        Group group = groupRepository.findById(id).orElseThrow();
        return GroupDto.mapToGroupDtoDetail(group);
    }


    public List<GroupDto> getGroupsByOwner() {
        User owner = helper.getUser(); // thông tin của người dùng
        List<Group> groups = groupRepository.findByOwner(owner);
        List<GroupDto> groupDtos = new ArrayList<>();

        groups.forEach(group -> {
            groupDtos.add(GroupDto.mapToGroupDto(group));
        });

        return groupDtos;
    }

    public boolean deleteGroupById(Long id) {
        Group group = groupRepository.findById(id).orElseThrow();
        groupRepository.delete(group);
        return true;
    }


    public boolean inviteUserGroup(Long id, String emailUser) {

        Optional<User> userOptional = userRepository.findById(emailUser);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Người dùng có mail không tồn tại!");
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
        details.setSubject("Thư mời tham gia lớp học");
        details.setRecipient(emailTo);
        String link = "http://localhost:8080/groups/"+groupId+"/active?token=" + token;
        details.setMsgBody("<p>Xin chào bạn,</p>" +
                "<p>Bạn vui lòng nhấn vào <a href=\'"+link+"\'>link này</a> để tham gia lớp học.</p>" +
                "    <p>Trân trọng,</p>");
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
            throw new UsernameNotFoundException("Người dùng có mail không tồn tại!");
        }
        List<UserGroup> userGroups = userGroupRepository.findByUserAndGroup(new User(userGroupRequest.getEmail()),
                new Group(userGroupRequest.getIdGroup())
        );
        if (userGroups.isEmpty()){
            throw new UsernameNotFoundException("Người dùng có mail không có trong nhóm!");
        }

        userGroupRepository.delete(userGroups.get(0));
        return true;
    }

    public List<GroupDto> getGroupsByAttendance() {

        User user = helper.getUser();

        List<GroupDto> groupDtos = new ArrayList<>();

        List<UserGroup> userGroups = userGroupRepository.findByUserAndIsActive(user, true);
        userGroups.forEach(ele -> {
            groupDtos.add(GroupDto.mapToGroupDto(ele.getGroup()));
        });

        return groupDtos;
    }
    //    public List<UserDto> getUserOfGroup(Long id){
    //        List<UserDto> userDtos = new ArrayList<>();
    //        Group group = groupRepository.findById(id).orElseThrow();
    //
    //        List<UserGroup> userActiveGroup = group.getUserGroups().stream().filter(UserGroup::isActive).toList();
    //        userActiveGroup.forEach(ele-> {
    //            userDtos.add(new UserDto(ele.getUser()));
    //        });
    //        return userDtos;
    //    }


}