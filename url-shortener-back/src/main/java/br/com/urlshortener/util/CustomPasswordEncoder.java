package br.com.urlshortener.util;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Qualifier
public class CustomPasswordEncoder extends BCryptPasswordEncoder {
  @Override
  public String encode(CharSequence rawPassword) {
    // Create an encoder with strength 16
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
    final String password = rawPassword.toString();

    return encoder.encode(password);
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    // Create an encoder with strength 16
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);

    return encoder.matches(rawPassword, encodedPassword);
  }

}
