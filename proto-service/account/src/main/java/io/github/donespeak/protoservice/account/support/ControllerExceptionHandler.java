package io.github.donespeak.protoservice.account.support;

import io.github.donespeak.protoservice.common.proto.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

/**
 * @author Yang Guanrong
 * @date 2020/04/20 09:13
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 对应 Http 请求头的 accept 客户器端希望接受的类型和服务器端返回类型不一致。 这里虽然设置了拦截，但是并没有起到作用。需要通过http请求的流程来进一步确定原因。
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ApiError handleHttpMediaTypeNotAcceptableException(HttpServletRequest request,
        HttpMediaTypeNotAcceptableException ex) {
        log.debug(ex.getMessage(), ex);
        StringBuilder messageBuilder =
            new StringBuilder().append("The media type is not acceptable.").append(" Acceptable media types are ");
        ex.getSupportedMediaTypes().forEach(t -> messageBuilder.append(t + ", "));
        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
        String code = httpStatus.value() + "";
        String message = messageBuilder.substring(0, messageBuilder.length() - 2);

        return ApiError.newBuilder().setCode(code).setMessage(message).build();
    }

    /**
     * 对应请求头的 content-type 客户端发送的数据类型和服务器端希望接收到的数据不一致
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ApiError handleHttpMediaTypeNotSupportedException(HttpServletRequest request,
        HttpMediaTypeNotSupportedException ex) {
        log.debug(ex.getMessage(), ex);

        return unsupportedMediaType(ex.getContentType(), ex.getSupportedMediaTypes());
    }

    /**
     * 前端发送过来的数据无法被正常处理 比如后天希望收到的是一个json的数据，但是前端发送过来的却是xml格式的数据或者是一个错误的json格式数据
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApiError handlerHttpMessageNotReadableException(HttpServletRequest request,
        HttpMessageNotReadableException ex) {
        log.debug(ex.getMessage(), ex);
        String message = "Problems parsing JSON";
        return ApiError.newBuilder().setCode(HttpStatus.BAD_REQUEST.value() + "").setMessage(message).build();
    }

    /**
     * 将返回的结果转化到响应的数据时候导致的问题。 当使用json作为结果格式时，可能导致的原因为序列化错误。 目前知道，如果返回一个没有属性的对象作为结果时，会导致该异常。
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ApiError handlerHttpMessageNotWritableException(HttpServletRequest request,
        HttpMessageNotWritableException ex) {
        return internalServiceError(request, ex);
    }

    /**
     * 请求方法不支持
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiError exceptionHandle(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        log.debug(ex.getMessage(), ex);
        return methodNotAllow(ex.getMethod(), ex.getSupportedHttpMethods());
    }

    public static ApiError unsupportedMediaType(MediaType contentType, List<MediaType> supportedMediaTypes) {
        HttpStatus httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        StringBuilder messageBuilder =
            new StringBuilder().append(contentType).append(" media type is not supported. Supported media types are ");

        supportedMediaTypes.forEach(t -> messageBuilder.append(t + ", "));

        String message = messageBuilder.substring(0, messageBuilder.length() - 2);

        return ApiError.newBuilder().setCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value() + "").setMessage(message).build();
    }

    public static ApiError methodNotAllow(String method, Set<HttpMethod> supportedHttpMethods) {
        HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        StringBuilder messageBuilder = new StringBuilder().append(method)
            .append(" method is not supported for this request.").append(" Supported methods are ");

        supportedHttpMethods.forEach(m -> messageBuilder.append(m + ", "));

        String message = messageBuilder.substring(0, messageBuilder.length() - 2);

        return ApiError.newBuilder().setCode(httpStatus.value() + "").setMessage(message).build();
    }

    private ApiError internalServiceError(HttpServletRequest request, Exception e) {
        log.error("Internal Error: {}", e.getMessage(), e);
        return ApiError.newBuilder()
            .setCode(HttpStatus.INTERNAL_SERVER_ERROR.value() + "")
            .setMessage(e.getMessage())
            .build();
    }
}
