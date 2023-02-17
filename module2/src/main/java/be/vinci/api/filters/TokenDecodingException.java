package be.vinci.api.filters;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class TokenDecodingException extends WebApplicationException {
    public TokenDecodingException() {
        super(Response.status(Response.Status.FORBIDDEN)
                .build());
    }

    public TokenDecodingException(String message) {
        super(Response.status(Response.Status.FORBIDDEN)
                .entity(message)
                .type("text/plain")
                .build());
    }

    public TokenDecodingException(Throwable cause) {
        super(Response.status(Response.Status.FORBIDDEN)
                .entity(cause.getMessage())
                .type("text/plain")
                .build());
    }
}
