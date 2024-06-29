package com.hdnguyen.service;


import com.hdnguyen.util.Helper;
import com.hdnguyen.dao.AssignmentDao;
import com.hdnguyen.dao.SubmitDao;
import com.hdnguyen.entity.Assignment;
import com.hdnguyen.entity.Submit;
import com.hdnguyen.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class SubmitService {

    private final Helper helper;

    private final SubmitDao submitDao;
    private final AssignmentDao assignmentDao;
    private final FirebaseStorageService firebaseStorageService;

    public void createSubmit(Long idGroup, Long idAssignment, MultipartFile file) throws Exception {

        String emailUser = helper.getEmailUser();
        User user = helper.getUser();
        Assignment assignment = assignmentDao.findAssignmentByIdGroupIdAndUserEmail(idAssignment, idGroup, emailUser).orElse(null);

        if (assignment == null) throw new Exception("Không tìm thấy tài nguyên");

        Date deadline = assignment.getDeadline();
        Date submitDate = new Date();
        // check thời gian submit

        if (deadline.compareTo(submitDate) < 0) {
            throw new Exception("Quá hạn nộp bài!");
        }

        // tiếp tục lưu file.
        String url = firebaseStorageService.save("submit", file);

        Submit submit = new Submit();
        submit.setAssignment(assignment);
        submit.setUser(user);
        submit.setUrl(url);
        submit.setTime(submitDate);

        submitDao.save(submit);
    }

}
