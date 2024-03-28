package top.zsmile.common.core.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OkHttpUtil {
    private static final Logger log = LoggerFactory.getLogger(OkHttpUtil.class);
    public static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static MediaType XML = MediaType.parse("application/xml; charset=utf-8");

    private OkHttpUtil() {
    }

    /**
     * GET
     *
     * @param url     请求的url
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return String
     */
    public static String get(String url, Map<String, String> queries) {
        return get(url, (Map) null, queries);
    }

    /**
     * GET
     *
     * @param url     请求的url
     * @param header  请求头
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return String
     */
    public static String get(String url, Map<String, String> header, Map<String, String> queries) {
        String queriesStr = buildQueries(url, queries);
        Request.Builder builder = buildRequest(header);
        Request request = builder.url(queriesStr).build();
        return getBody(request);
    }

    /**
     * GET
     *
     * @param url     请求的url
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return String
     */
    public static String get(String url, String queries) {
        return get(url, (Map) null, queries);
    }

    /**
     * GET
     *
     * @param url     请求的url
     * @param header  请求头
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return String
     */
    public static String get(String url, Map<String, String> header, String queries) {
        String queriesStr = buildQueries(url, queries);
        Request.Builder builder = buildRequest(header);
        Request request = builder.url(queriesStr).build();
        return getBody(request);
    }

    /**
     * POST
     *
     * @param url    请求的url
     * @param params post form 提交的参数
     * @return String
     */
    public static String post(String url, Map<String, String> params) {
        return post(url, (Map) null, params);
    }

    /**
     * POST
     *
     * @param url    请求的url
     * @param header 请求头
     * @param params post form 提交的参数
     * @return String
     */
    public static String post(String url, Map<String, String> header, Map<String, String> params) {
        Request.Builder builder = buildRequest(header);
        Request request = builder.url(url).post(buildFormBody(params)).build();
        return getBody(request);
    }

    /**
     * POST请求发送JSON数据
     *
     * @param url  请求的url
     * @param json 请求的json串
     * @return String
     */
    public static String postJson(String url, String json) {
        return postJson(url, (Map) null, json);
    }

    /**
     * POST请求发送JSON数据
     *
     * @param url    请求的url
     * @param header 请求头
     * @param json   请求的json串
     * @return String
     */
    public static String postJson(String url, Map<String, String> header, String json) {
        return postContent(url, header, json, JSON);
    }

    /**
     * POST请求发送xml数据
     *
     * @param url 请求的url
     * @param xml 请求的xml串
     * @return String
     */
    public static String postXml(String url, String xml) {
        return postXml(url, (Map) null, xml);
    }

    /**
     * POST请求发送xml数据
     *
     * @param url    请求的url
     * @param header 请求头
     * @param xml    请求的xml串
     * @return String
     */
    public static String postXml(String url, Map<String, String> header, String xml) {
        return postContent(url, header, xml, XML);
    }

    /**
     * 发送POST请求
     *
     * @param url       请求的url
     * @param header    请求头
     * @param content   请求内容
     * @param mediaType 请求类型
     * @return String
     */
    public static String postContent(String url, Map<String, String> header, String content, MediaType mediaType) {
        RequestBody requestBody = RequestBody.create(mediaType, content);
        Request.Builder builder = new Request.Builder();
        if (header != null && header.keySet().size() > 0) {
            header.forEach(builder::addHeader);
        }

        Request request = builder.url(url).post(requestBody).build();
        return getBody(request);
    }

    /**
     * 发送POST请求
     *
     * @param url       请求的url
     * @param header    请求头
     * @param content   请求内容
     * @param mediaType 请求类型
     * @return byte[]
     */
    public static byte[] postContentByte(String url, Map<String, String> header, String content, MediaType mediaType) {
        RequestBody requestBody = RequestBody.create(mediaType, content);
        Request.Builder builder = new Request.Builder();
        if (header != null && header.keySet().size() > 0) {
            header.forEach(builder::addHeader);
        }

        Request request = builder.url(url).post(requestBody).build();
        return getBodyByte(request);
    }

    /**
     * 获取body
     *
     * @param request request
     * @return String
     */
    private static String getBody(Request request) {
        String responseBody = "";
        Response response = null;

        String var4;
        try {
            OkHttpClient okHttpClient = getClient(request);
            response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                return responseBody;
            }
            var4 = response.body().string();
        } catch (Exception var8) {
            log.error("okhttp3 post error >> ex = {}", var8.getMessage());
            return responseBody;
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return var4;
    }


    /**
     * 获取Client
     *
     * @param request request
     * @return String
     */
    private static OkHttpClient getClient(Request request) {
        int timeout = 30;
        String timeoutHeader = request.header("timeout");
        if (null != timeoutHeader && !timeoutHeader.isEmpty()) {
            timeout = Integer.parseInt(timeoutHeader);
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    /**
     * POST请求发送文件并下载
     *
     * @param url    请求的url
     * @param params 请求的参数
     * @param files  请求的文件
     * @return byte[]
     */
    public static byte[] downloadMultipartPost(String url, Map<String, String> params, Map<String, File> files) {
        return downloadMultipartPost(url, (Map) null, params, files);
    }

    /**
     * POST请求发送文件并下载
     *
     * @param url    请求的url
     * @param header 请求头
     * @param params 请求的参数
     * @param files  请求的文件
     * @return byte[]
     */
    public static byte[] downloadMultipartPost(String url, Map<String, String> header, Map<String, String> params, Map<String, File> files) {
        Request.Builder builder = buildRequest(header);
        Request request = builder.url(url).post(buildMultipartBody(params, files)).build();
        return getBodyByte(request);
    }


    /**
     * POST请求下载
     *
     * @param url    请求的url
     * @param params 请求的参数
     * @return byte[]
     */
    public static byte[] downloadPost(String url, Map<String, String> params) {
        return downloadPost(url, (Map) null, params);
    }

    /**
     * POST请求下载
     *
     * @param url    请求的url
     * @param header 请求头
     * @param params 请求的参数
     * @return byte[]
     */
    public static byte[] downloadPost(String url, Map<String, String> header, Map<String, String> params) {
        return downloadPost(url, header, params, true);
    }

    /**
     * POST请求下载
     *
     * @param url        请求的url
     * @param header     请求头
     * @param params     请求的参数
     * @param needEncode 是否需要encode
     * @return byte[]
     */
    public static byte[] downloadPost(String url, Map<String, String> header, Map<String, String> params, boolean needEncode) {
        Request.Builder builder = buildRequest(header);
        Request request = builder.url(url).post(buildFormBody(params, needEncode)).build();
        return getBodyByte(request);
    }


    /**
     * POST JSON请求下载
     *
     * @param url  请求的url
     * @param json 请求的json参数
     * @return byte[]
     */
    public static byte[] downloadPostJson(String url, String json) {
        return postContentByte(url, null, json, JSON);
    }

    /**
     * POST JSON请求下载
     *
     * @param url    请求的url
     * @param header 请求头
     * @param json   请求的json参数
     * @return byte[]
     */
    public static byte[] downloadPostJson(String url, Map<String, String> header, String json) {
        return postContentByte(url, header, json, JSON);
    }


    /**
     * 获取 body 字节流
     *
     * @param request request
     * @return byte[]
     */
    private static byte[] getBodyByte(Request request) {
        byte[] responseBytes = null;
        Response response = null;

        try {
            OkHttpClient okHttpClient = getClient(request);
            response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                return responseBytes;
            }
            responseBytes = response.body().bytes();
        } catch (Exception var8) {
            log.error("okhttp3 post error >> ex = {}", var8.getMessage());
            return responseBytes;
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBytes;
    }

    /**
     * 编译FormBody
     *
     * @param params 请求参数
     * @return RequestBody
     */
    public static RequestBody buildFormBody(Map<String, String> params) {
        return buildFormBody(params, true);
    }


    /**
     * 编译FormBody
     *
     * @param params     请求参数
     * @param needEncode 需要编码
     * @return RequestBody
     */
    public static RequestBody buildFormBody(Map<String, String> params, boolean needEncode) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (params != null && params.keySet().size() > 0) {
            if (needEncode) {
                params.forEach(formBuilder::add);
            } else {
                params.forEach(formBuilder::addEncoded);
            }
        }
        return formBuilder.build();
    }


    /**
     * 编译MultipartBody
     *
     * @param params 请求参数
     * @param files  请求文件
     * @return RequestBody
     */
    public static RequestBody buildMultipartBody(Map<String, String> params, Map<String, File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (params != null && params.keySet().size() > 0) {
            params.forEach(builder::addFormDataPart);
        }
        if (files != null && files.keySet().size() > 0) {
            files.forEach((s, file) -> {
                try {
                    builder.addFormDataPart(s, file.getName(), RequestBody.create(MediaType.parse(Files.probeContentType(file.toPath())), file));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return builder.build();
    }

    /**
     * 编译Request
     *
     * @param header 请求头
     * @return Request
     */
    public static Request.Builder buildRequest(Map<String, String> header) {
        Request.Builder builder = new Request.Builder();
        if (header != null && header.keySet().size() > 0) {
            header.forEach(builder::addHeader);
        }
        return builder;
    }

    /**
     * 编译请求参数
     *
     * @param url     URl路径
     * @param queries 参数
     * @return String 请求url
     */
    public static String buildQueries(String url, Map<String, String> queries) {
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            queries.forEach((k, v) -> {
                sb.append("&").append(k).append("=").append(v);
            });
        }
        return sb.toString();
    }

    /**
     * 编译请求参数
     *
     * @param url     URl路径
     * @param queries 参数
     * @return String 请求url
     */
    public static String buildQueries(String url, String queries) {
        StringBuffer sb = new StringBuffer(url);
        if (StringUtils.isNotBlank(queries)) {
            if (queries.charAt(0) != '?') {
                sb.append("?");
            }
            sb.append(queries);
        }
        return sb.toString();
    }
}
