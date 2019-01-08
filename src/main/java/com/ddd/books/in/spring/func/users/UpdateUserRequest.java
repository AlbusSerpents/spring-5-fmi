package com.ddd.books.in.spring.func.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static java.util.Optional.ofNullable;

@Data
@NoArgsConstructor
public class UpdateUserRequest {

    @Email
    @NotBlank
    private String email;

    @Valid
    @JsonProperty(access = WRITE_ONLY)
    private ChangePasswordRequest passwords;

    @JsonIgnore
    String getNewPassword() {
        return ofNullable(passwords)
                .map(ChangePasswordRequest::getNewPassword)
                .orElse(null);
    }

    @JsonIgnore
    String getOldPassword() {
        return ofNullable(passwords)
                .map(ChangePasswordRequest::getOldPassword)
                .orElse(null);
    }

    @Data
    @NoArgsConstructor
    @SuppressWarnings("WeakerAccess")
    public static class ChangePasswordRequest {
        @NotBlank
        private String oldPassword;
        @NotBlank
        private String newPassword;

    }
}
