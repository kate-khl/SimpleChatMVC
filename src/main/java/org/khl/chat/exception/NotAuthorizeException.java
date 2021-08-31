package org.khl.chat.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class NotAuthorizeException extends RuntimeException {

	public NotAuthorizeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotAuthorizeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
