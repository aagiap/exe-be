package com.exebe.dto.auth;

import lombok.Data;

import java.time.Instant;

@Data
public class LoginResponseDTO {
  private String token;
  private Instant expiration;

  public LoginResponseDTO(String token, Instant expiration) {
    this.token = token;
    this.expiration = expiration;
  }
}
