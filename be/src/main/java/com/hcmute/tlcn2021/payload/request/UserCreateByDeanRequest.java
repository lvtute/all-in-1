package com.hcmute.tlcn2021.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateByDeanRequest {
    @NotBlank(message = "Tài khoản người dùng không được để trống")
    private String username;

    @NotBlank(message = "Tên người dùng không được để trống")
    private String fullName;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @NotNull
    List<Long> topicIdList;
}
