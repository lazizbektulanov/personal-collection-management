package uz.itransition.personalcollectionmanagement.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterDto {

    @NotEmpty(message = "First name can not be empty")
    private String fullName;

    @NotEmpty(message = "Email can not be empty")
    private String email;

    @NotEmpty(message = "Please enter your password")
    private String password;

    @NotEmpty(message = "Please confirm your password")
    private String confirmPassword;
}
