package br.com.urlshortener.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

/*
 * Essa classe possui os campos que vai compor a mensagem no retorno da exceção.
*/

@Getter
@Setter
@JsonInclude(Include.NON_NULL) //Só inclui campos que não são nulos
public class Field {
	
	private String name;
	private String message;
	
	public Field(String name, String message) {
		super();
		this.name = name;
		this.message = message;
	}
	
}
