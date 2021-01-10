package br.com.urlshortener.domain.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "public", name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private Long id;

	@Column(name = "login")
	private String login;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<UserURL> urlsFromUser;

}
