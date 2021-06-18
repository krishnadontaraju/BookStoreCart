package com.bookstore.cart.exception;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.bookstore.cart.response.Response;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

private static final String message = "Exception while processing request";
	
	@org.springframework.web.bind.annotation.ExceptionHandler(CartException.class)
    public ResponseEntity<Response> handleInsuranceCategoryException (CartException exception){

        log.error("Contact Exception ," ,exception);

        Response response = new Response("Exception while processing ", exception.getMessage());
        return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
    }
	

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> handleHttpNotReadableException(HttpMessageNotReadableException exception){

        log.error("Wrong HTTP regquested" , exception);
        Response response = new Response(message , "HTTP method requested was not correct");
        return new ResponseEntity<Response>(response , HttpStatus.BAD_REQUEST);

    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleArgumentNotValidExceptionEntity (MethodArgumentNotValidException exception){
        List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
        List<String> errorMessage = errorList.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        log.error(String.valueOf(errorMessage));

        Response response = new Response("Exception while processing ", errorMessage);
        return new ResponseEntity<Response>(response,HttpStatus.BAD_REQUEST);
    }

     @org.springframework.web.bind.annotation.ExceptionHandler(SQLGrammarException.class)
    public ResponseEntity<Response> handleInCorrectQueryException(SQLGrammarException exception){

    	 Response response = new Response(exception.getMessage() ,"The Sql Query you gave has a problem please check and do again" );

        log.error("Sql Query exception " , exception);
        return new ResponseEntity<Response>(response , HttpStatus.NOT_ACCEPTABLE);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DateTimeParseException.class)
    public  ResponseEntity<Response> handleIncorrectDateTimeException(DateTimeParseException exception){

    	Response response = new Response(exception.getMessage() , "Date should be of the format dd MMM yyyy");
        log.error("Date format exception " ,exception);
        return new ResponseEntity<Response>(response , HttpStatus.BAD_REQUEST);

    }
	
}
