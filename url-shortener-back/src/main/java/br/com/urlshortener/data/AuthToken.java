package br.com.urlshortener.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthToken {
	
  private String token;
  private String name;
  private Long userId;
  
}
