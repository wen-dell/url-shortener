package br.com.urlshortener.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/*
 * Essa classe vai ter os atributos que irei usar no retorno da exceção.
*/

@Getter
@Setter
@JsonInclude(Include.NON_NULL) //Só inclui campos que não são nulos
public class Problem {
	
	private Integer status;
	private LocalDateTime dateTime;
	private String message;
	private List<Field> fields;
	
}
