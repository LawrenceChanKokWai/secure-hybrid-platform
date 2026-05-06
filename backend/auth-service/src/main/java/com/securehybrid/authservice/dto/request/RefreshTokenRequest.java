package com.securehybrid.authservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RefreshTokenRequest {

    @NotBlank
    private String refreshToken;

}
