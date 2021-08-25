package uz.alcedo.sproject.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResponseFileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResponseFileNotFoundException(String message) {
        super(message);
    }

    public ResponseFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
