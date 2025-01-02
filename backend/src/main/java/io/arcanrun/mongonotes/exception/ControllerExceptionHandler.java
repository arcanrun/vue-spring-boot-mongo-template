package io.arcanrun.mongonotes.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolationException(
          final ConstraintViolationException exception) {
    return buildResponseEntity(exception, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException exception) {
    return buildResponseEntity(exception, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<Object> handleUnauthorizedException(final UnauthorizedException exception) {
    return buildResponseEntity(exception, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Object> handleResourceNotFoundException(
          final ResourceNotFoundException exception) {
    return buildResponseEntity(exception, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<Object> handleValidationException(final ValidationException exception) {
    return buildResponseEntity(exception, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<Object> handleBadRequestException(final BadRequestException exception) {
    return buildResponseEntity(exception, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGenericException(HttpServletResponse response, Exception e) {
    return buildResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
          MethodArgumentNotValidException ex,
          HttpHeaders headers,
          HttpStatusCode status,
          WebRequest request) {
    log.error(ex.getMessage(), ex);

    var errors =
            ex.getBindingResult().getFieldErrors().stream()
                    .map(
                            error -> {
                              var field = error.getField();
                              var message =
                                      error.getDefaultMessage() == null
                                              ? null
                                              : String.format(error.getDefaultMessage(), error.getField());

                              return String.format("%s: %s", field, message);
                            })
                    .toList();
    var exceptionMessage = String.format("%s", String.join(",", errors));
    var apiError =
            ApiErrorDto.builder()
                    .message(exceptionMessage)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .timestamp(LocalDateTime.now())
                    .build();

    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
          HttpMessageNotReadableException ex,
          HttpHeaders headers,
          HttpStatusCode status,
          WebRequest request) {
    log.error(ex.getMessage(), ex);

    var errorMessage = ex.getMessage();

    if (ex.getCause().getClass() == InvalidFormatException.class) {
      var cause = (InvalidFormatException) ex.getCause();
      var from = cause.getPath().stream().map(e -> e.getFrom().getClass()).toList();

      errorMessage = String.format("%s | %s ", cause.getMessage(), from);
    }

    var apiError =
            ApiErrorDto.builder()
                    .message(errorMessage)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .timestamp(LocalDateTime.now())
                    .build();

    return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
  }

  private ResponseEntity<Object> buildResponseEntity(Throwable e, HttpStatus badRequest) {
    log.error(e.getMessage(), e);

    var apiError =
            ApiErrorDto.builder()
                    .message(e.getMessage())
                    .httpStatus(badRequest)
                    .timestamp(LocalDateTime.now())
                    .build();

    return new ResponseEntity<>(apiError, apiError.getHttpStatus());
  }
}
