package uz.alcedo.sproject.security;

import org.springframework.security.core.AuthenticationException;

public class UserNotActivatedException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public UserNotActivatedException(String msg) {
		super(msg);
	}
	
	public UserNotActivatedException(String message, Throwable t) {
        super(message, t);
    }
}
