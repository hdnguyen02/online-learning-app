package com.hdnguyen.dto.group;


import com.hdnguyen.dto.auth.UserResponse;
import com.hdnguyen.entity.Submit;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SubmitDto {

    private Long id;
    private String url;
    private Date time;
    private UserResponse user;


    // nhận thông tin của submit
    public SubmitDto(Submit submit) {
        this.id = submit.getId();
        this.url = submit.getUrl();
        this.time = submit.getTime();
        user = new UserResponse(submit.getUser());
    }

}
