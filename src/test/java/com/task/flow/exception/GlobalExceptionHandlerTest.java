package com.task.flow.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void typeMismatchReturnsBadRequestApiError() {
        MethodArgumentTypeMismatchException exception = new MethodArgumentTypeMismatchException(
                "not-an-id", Integer.class, "id", null, null
        );

        var response = exceptionHandler.handleTypeMismatch(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().status());
        assertEquals("Bad Request", response.getBody().error());
        assertEquals("Invalid value for parameter 'id'", response.getBody().message());
        assertEquals(Map.of(), response.getBody().validationErrors());
    }

    @Test
    void illegalStateReturnsInternalServerErrorApiError() {
        var response = exceptionHandler.handleIllegalState(new IllegalStateException("insert failed"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, response.getBody().status());
        assertEquals("Internal Server Error", response.getBody().error());
        assertEquals("Bulk task creation failed", response.getBody().message());
        assertEquals(Map.of(), response.getBody().validationErrors());
    }
}
