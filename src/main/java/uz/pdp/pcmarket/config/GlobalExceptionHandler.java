package uz.pdp.pcmarket.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.pdp.pcmarket.exception.ConflictException;
import uz.pdp.pcmarket.exception.NotFoundException;
import uz.pdp.pcmarket.payload.response.ApiError;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest request) {
        return new ResponseEntity<>(new ApiError(INTERNAL_SERVER_ERROR, "Server error", e.getLocalizedMessage()), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(NotFoundException e, WebRequest request) {
        return new ResponseEntity<>(new ApiError(NOT_FOUND, "Record Not Found", e.getLocalizedMessage()), NOT_FOUND);
    }

    @ExceptionHandler(value = ConflictException.class)
    public final ResponseEntity<Object> handleConflictException(ConflictException e, WebRequest request) {
        return new ResponseEntity<>(new ApiError(CONFLICT, "Data integrity violation", e.getLocalizedMessage()), CONFLICT);
    }

    /**
     * Customize the response for MethodArgumentNotValidException.
     * <p>This method delegates to {@link #handleExceptionInternal}.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            errors.add(error.getDefaultMessage());
        }
        return new ResponseEntity<>(new ApiError(BAD_REQUEST, "Validation Failed", errors), BAD_REQUEST);
    }

    /**
     * Customize the response for HttpRequestMethodNotSupportedException.
     * <p>This method logs a warning, sets the "Allow" header, and delegates to
     * {@link #handleExceptionInternal}.
     *
     * @param ex      the exception
     * @param headers the headers to be written to the response
     * @param status  the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(httpMethod -> builder.append(httpMethod).append(" "));
        ApiError apiError = new ApiError(METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());
        return new ResponseEntity<>(apiError, METHOD_NOT_ALLOWED);
    }
}
