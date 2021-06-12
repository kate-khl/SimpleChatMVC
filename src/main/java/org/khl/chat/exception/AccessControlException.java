package org.khl.chat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class AccessControlException extends RuntimeException{

	public AccessControlException(){
		super();
	}
	public AccessControlException(String message){
		super(message);
	}
}
