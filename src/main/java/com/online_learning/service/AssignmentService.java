package com.online_learning.service;


import com.online_learning.util.Helper;
import com.online_learning.dao.AssignmentDao;
import com.online_learning.dao.GroupDao;
import com.online_learning.dto.group.AssignmentStudentDto;
import com.online_learning.dto.group.AssignmentTeacherDto;
import com.online_learning.entity.Assignment;
import com.online_learning.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final Helper helper;
    private final AssignmentDao assignmentDao;
    private final GroupDao groupDao;
    private final FirebaseStorageService firebaseStorageService;

    public boolean createAssignment(@RequestParam String name,
                                    @RequestParam String description,
                                    @RequestParam Long idGroup,
                                    Date deadline,
                                    @RequestParam MultipartFile file) throws Exception {


        Group group = groupDao.findById(idGroup).orElse(null);
        if (group == null) throw new Exception("Không tìm thấy tài nguyên");
        String url = firebaseStorageService.save("assignment", file);


        Assignment assignment = Assignment.builder()
                .name(name)
                .description(description)
                .deadline(deadline)
                .url(url)
                .group(group)
                .build();

        try {
            assignmentDao.save(assignment);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }


    public List<AssignmentStudentDto> getStudentAssignments(long idGroup) throws Exception {
        Group group =groupDao.findById(idGroup).orElse(null);
        if (group == null) throw new Exception("Không tồn tại group");
        List<Assignment> assignments = group.getAssignments();
        List<AssignmentStudentDto> assignmentDtos = new ArrayList<>();
        assignments.forEach(assignment -> {
            assignmentDtos.add(new AssignmentStudentDto(assignment));
        });

        return assignmentDtos;
    }


    public AssignmentTeacherDto getTeacherAssignment(Long id) throws Exception {

        Assignment assignment = assignmentDao.findById(id).orElse(null);
        if (assignment == null) throw new Exception("Không tìm thấy tài nguyên");

        return new AssignmentTeacherDto(assignment);
    }

    public AssignmentStudentDto getStudentAssignment(Long id) throws Exception {
        Assignment assignment = assignmentDao.findById(id).orElse(null);
        if (assignment == null) throw new Exception("Không tìm thấy tài nguyên");
        return new AssignmentStudentDto(assignment);
    }







}


