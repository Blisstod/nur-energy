package kz.nur.energy.exceptions;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorizedException extends ResponseStatusException {

    private String message;

    public UnauthorizedException(final String message) {
        super(HttpStatus.UNAUTHORIZED, message);
        this.message = message;
    }
    @Override
    public String getMessage() {
        return NestedExceptionUtils.buildMessage(this.message, getCause());
    }
}
