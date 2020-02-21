package com.huiblog.huiblog.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateUserReq {
    @ApiModelProperty(
            example="Sam Smith",
            notes="Name cannot be empty",
            required=true
    )
    @NotEmpty(message = "Ten khong duoc rong")
    private String name;

    @ApiModelProperty(
            example="Ahihi@gmail.com",
            notes="Valid email",
            required=true
    )
    @Email
    private String email;

    @Size(min = 4, max = 30, message = "Password phai nam trong khoang 4 - 30")
    private String password;

//    @Pattern(regexp = "", message = "So dien thoai khong hop le")
//    private String phone;

//    @JsonFormat(pattern = "yyyy/MM/dd", shape = JsonFormat.Shape.STRING)
//    private Date dob;
}
