package com.github.pedrobacchini.exception;

import com.github.pedrobacchini.config.LocaleMessageSource;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

    private final LocaleMessageSource localeMessageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        String friendlyMessage = localeMessageSource.getMessage("json-invalid-formatting");
        String debugMessage = ExceptionUtils.getRootCauseMessage(ex);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, friendlyMessage, debugMessage);
        return super.handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request) {
        String friendlyMessage = localeMessageSource.getMessage("unsupported-method", ex.getMethod());
        String debugMessage = ExceptionUtils.getRootCauseMessage(ex);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, friendlyMessage, debugMessage);
        return super.handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        String friendlyMessage = localeMessageSource.getMessage("validation-error");
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, friendlyMessage);
        apiError.addBindingResult(ex.getBindingResult());
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        StringBuilder builder = new StringBuilder();
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        String expectedType = builder.substring(0, builder.length() - 2);
        String friendlyMessage = localeMessageSource.getMessage("unsupported-media-type", ex.getContentType(), expectedType);
        String debugMessage = ExceptionUtils.getRootCauseMessage(ex);
        ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, friendlyMessage, debugMessage);
        return super.handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgumentException(RuntimeException ex, WebRequest request) {
        String friendlyMessage = localeMessageSource.getMessage("illegal-argument");
        String debugMessage = ExceptionUtils.getRootCauseMessage(ex);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, friendlyMessage, debugMessage);
        return super.handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String friendlyMessage = localeMessageSource.getMessage("operation-not-allowed");
        String debugMessage = ExceptionUtils.getRootCauseMessage(ex);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, friendlyMessage, debugMessage);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<Object> handleApiException(ApiException ex, WebRequest request) {
        String friendlyMessage = ex instanceof NotFoundException ? localeMessageSource.getMessage("resource-not-found")
                : ex instanceof IntegrityViolationException ? ex.getMessage()
                : localeMessageSource.getMessage("no-friendly-message");

        String debugMessage = ExceptionUtils.getRootCauseMessage(ex);
        ApiError apiError = new ApiError(ex.getStatus(), friendlyMessage, debugMessage);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), ex.getStatus(), request);
    }
}
