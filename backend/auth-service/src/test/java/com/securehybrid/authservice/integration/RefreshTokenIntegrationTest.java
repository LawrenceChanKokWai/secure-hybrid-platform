package com.securehybrid.authservice.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.securehybrid.authservice.TestUtil;
import com.securehybrid.authservice.dto.request.RefreshTokenRequest;
import com.securehybrid.authservice.dto.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class RefreshTokenIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void refreshToken_shouldRotateRefreshToken() throws Exception {
        String email = TestUtil.uniqueEmail();

        RegisterRequest registerRequest =
                TestUtil.createRegisterRequest(email);

        String registerResponse = mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registerRequest))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.refreshToken", notNullValue()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode registerJson =
                objectMapper.readTree(registerResponse);

        String oldRefreshToken =
                registerJson.get("refreshToken").asText();

        RefreshTokenRequest refreshRequest =
                RefreshTokenRequest.builder()
                        .refreshToken(oldRefreshToken)
                        .build();

        String refreshResponse = mockMvc.perform(
                        post("/auth/refresh")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(refreshRequest))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", notNullValue()))
                .andExpect(jsonPath("$.refreshToken", notNullValue()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode refreshJson =
                objectMapper.readTree(refreshResponse);

        String newRefreshToken =
                refreshJson.get("refreshToken").asText();

        assert !oldRefreshToken.equals(newRefreshToken);

        mockMvc.perform(
                        post("/auth/refresh")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(refreshRequest))
                )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message")
                        .value("Invalid refresh token"));
    }

}
