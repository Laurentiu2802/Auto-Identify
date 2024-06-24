package org.example.business.userException;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidUserIdException  extends ResponseStatusException {
    public InvalidUserIdException(String errorCode){super(HttpStatus.BAD_REQUEST, errorCode);}
}
