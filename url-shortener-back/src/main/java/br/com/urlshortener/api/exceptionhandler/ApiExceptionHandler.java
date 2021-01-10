package br.com.urlshortener.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.urlshortener.domain.exception.BusinessException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	//Vamos usar esse método para tratar as exceções quando lançadas (ex: nos serviços)
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBusiness(BusinessException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problem problem = new Problem();
		problem.setStatus(status.value());
		problem.setMessage(ex.getMessage());
		problem.setDateTime(LocalDateTime.now());
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	//Vamos sobrecrever esse método para poder dar um retorno mais padronizado na exceção.
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Field> fields = new ArrayList<Field>();
		
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			
			//Aqui pegamos o nome do campo com o erro
			String name = ((FieldError) error).getField();
			
			//Aqui pegamos a mensagem (padrão - inglês) que explica o erro
			//String message = error.getDefaultMessage();
			
			//Aqui pegamos a mensagem que explica o erro e padronizamos no arquivo -> messages.properties
			String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			fields.add(new Field(name, message));
		}
				
		Problem problem = new Problem();
		problem.setStatus(status.value());
		problem.setMessage("Um ou mais campos estão inválidos. Tente novamente, por favor!");
		problem.setDateTime(LocalDateTime.now());
		problem.setFields(fields);

		//Método mais genérico que vamos passar o objeto problem
		return super.handleExceptionInternal(ex, problem, headers, status, request);
	}

}
