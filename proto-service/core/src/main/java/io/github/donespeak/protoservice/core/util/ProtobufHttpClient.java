package io.github.donespeak.protoservice.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import io.github.donespeak.protoservice.core.exception.NotImplementedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.protobuf.Message;

/**
 * @author Yang Guanrong
 */
@Slf4j
public class ProtobufHttpClient {

    public static Message get(String url, Map<String, String> headers, Map<String, String> params,
        Message.Builder builder) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder(url);
        setParameters(uriBuilder, params);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        setHeaders(httpGet, headers);

        return execute(httpclient, httpGet, builder);
    }

    private static void setHeaders(HttpRequestBase request, Map<String, String> headers) {
        request.setHeader("User-Agent",
            "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        request.setHeader("Content-Type", "application/x-protobuf");
        if (headers == null) {
            return;
        }
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            request.setHeader(entry.getKey(), entry.getValue());
        }
    }

    private static void setParameters(URIBuilder uriBuilder, Map<String, String> parameters) {
        if (parameters == null) {
            return;
        }
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            uriBuilder.setParameter(entry.getKey(), entry.getValue());
        }
    }

    public static Message post(Message message, String url, Map<String, String> headers, Message.Builder builder)
        throws IOException {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        HttpEntity httpEntity = new ByteArrayEntity(message.toByteArray());
        httpPost.setEntity(httpEntity);
        setHeaders(httpPost, headers);

        return execute(httpclient, httpPost, builder);
    }

    public static Message put(Message message, String url, Map<String, String> headers, Message.Builder builder)
        throws IOException {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http POST请求
        HttpPut httpPut = new HttpPut(url);
        setHeaders(httpPut, headers);

        return execute(httpclient, httpPut, builder);
    }

    public static Message patch(Message message, String url, Map<String, String> header, Message.Builder builder) {
        throw new NotImplementedException();
    }

    public static Message delete(String url, Map<String, String> header, Message.Builder builder) {
        throw new NotImplementedException();
    }

    private static Message execute(CloseableHttpClient httpclient, HttpUriRequest request, Message.Builder builder)
        throws IOException {

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(request);
            InputStream content = response.getEntity().getContent();
            switch (response.getStatusLine().getStatusCode()) {
                case 200: {
                    return execute200(content, builder);
                }
                case 400: {
                    return execute400(content);
                }
                case 401: {
                    return execute401(content);
                }
                case 403: {
                    return execute403(content);
                }
                case 404: {
                    return execute404(content);
                }
                case 415: {
                    return executeError(content);
                }
                case 419: {
                    return executeError(content);
                }
                case 422: {
                    return executeError(content);
                }
                case 500: {
                    return execute500(content);
                }
                default: {
                    System.err.println(content);
                    return null;
                }
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
    }

    private static Message execute200(InputStream protobufStream, Message.Builder builder) throws IOException {
        return builder.mergeFrom(protobufStream).build();
    }

    private static Message execute400(InputStream protobufStream) throws IOException {
        return executeError(protobufStream);
    }

    private static Message execute401(InputStream protobufStream) throws IOException {
        return executeError(protobufStream);
    }

    private static Message execute403(InputStream protobufStream) throws IOException {
        return executeError(protobufStream);
    }

    private static Message execute404(InputStream protobufStream) throws IOException {
        return executeError(protobufStream);
    }

    private static Message execute500(InputStream protobufStream) throws IOException {
        return executeError(protobufStream);
    }

    private static Message executeError(InputStream protobufStream) throws IOException {
        log.error("error");
        return null;
        // Message message = com.google.protobuf.Internal.getDefaultInstance(errorResponseClass);
        // return message.getParserForType().parseFrom(protobufStream);
    }
}